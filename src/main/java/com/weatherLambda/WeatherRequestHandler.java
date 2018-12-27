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

        try {
            URL url = new URL("http://www.example.com/");
            URLConnection connection = url.openConnection();
            InputStream in = connection.getInputStream();

            String encoding = connection.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;

            String body = IOUtils.toString(in, encoding);
            response.setResponse(body);
        } catch (MalformedURLException badUrlException) {
            response.setResponse("Malformed URL: " + badUrlException.toString());
        } catch (IOException ioException) {
            response.setResponse("IOException: " + ioException.toString());
        }

        return response;
    }
}
