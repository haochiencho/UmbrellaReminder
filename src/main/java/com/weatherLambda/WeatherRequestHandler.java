package com.weatherLambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by briancho on 12/23/18.
 */
public class WeatherRequestHandler implements RequestHandler<RequestClass, ResponseClass> {
    public ResponseClass handleRequest(RequestClass request, Context context) {
        ResponseClass response = new ResponseClass();

        JsonNode weatherJson = getCurrentWeather();

        String weatherMessage = formulateMessage(weatherJson);

        SNSClient snsClient = new SNSClient();
        snsClient.publish(weatherMessage);

        response.setResponse(weatherMessage);

        return response;
    }

    private static String formulateMessage (JsonNode weather) {
        String briefDescription = getValueFromJsonNode(weather, "main");
        String description = getValueFromJsonNode(weather, "description");
        String temp = convertKelvinFahrenheit(getValueFromJsonNode(weather, "temp"));
        String max_temp = convertKelvinFahrenheit(getValueFromJsonNode(weather, "temp_max"));
        String min_temp = convertKelvinFahrenheit(getValueFromJsonNode(weather, "temp_min"));
        String humidity = getValueFromJsonNode(weather, "humidity");
        String wind_speed = getValueFromJsonNode(weather, "speed");

        return "Daily Forecast: " + briefDescription + ", current temp " + temp + ", high " + max_temp + ", low "
                + min_temp + ", humidity " + humidity + ", wind speed " + wind_speed + ", overall: " + description;

    }

    private static String convertKelvinFahrenheit (String kelvin) {
        return Double.toString(Math.round(((Float.parseFloat(kelvin) - 273.15) * 1.8 + 32.0)));
    }

    private static JsonNode getCurrentWeather () {

        String weatherAPIKey = System.getenv("WEATHER_API_KEY");
        String cityId = System.getenv("SEATTLE_CITY_ID");

        String weatherJsonString = getWeatherJson(cityId, weatherAPIKey);

        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(weatherJsonString);
            JsonNode jsonNode = mapper.readTree(weatherJsonString);
            System.out.println("norm: " + jsonNode.asText());
            System.out.println("special: " + jsonNode.get("weather").asText());

            return jsonNode;
        } catch (IOException ioException) {
            // TODO: log exceptions
            return null;
        }
    }

    private static String getHtmlFromUrl (String urlString) {
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();

            String encoding = connection.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            return IOUtils.toString(inputStream, encoding);
        } catch (MalformedURLException badUrlException) {
            return "Malformed URL: " + badUrlException.toString();
        } catch (IOException ioException) {
            return "IOException: " + ioException.toString();
        }
    }

    private static String getWeatherJson (String cityId, String weatherAPIKey) {

        String weatherApiUrl = "http://api.openweathermap.org/data/2.5/weather?id=" + cityId + "&appid=" + weatherAPIKey;
        String weatherJson = getHtmlFromUrl(weatherApiUrl);

        return weatherJson;
    }

    private static String getValueFromJsonNode (JsonNode jsonNode, String field) {
        return jsonNode.findValues(field).get(0).asText();
    }
}
