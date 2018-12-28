package com.weatherLambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
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

        response.setResponse(getCurrentWeather());

        return response;
    }

    private static String getCurrentWeather () {
        String weatherHtml = getHtmlFromUrl(System.getenv("WEATHER_API_URL"));

        // TODO: parse HTML to get description, temp, humidity, and wind

        return weatherHtml;
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
}
