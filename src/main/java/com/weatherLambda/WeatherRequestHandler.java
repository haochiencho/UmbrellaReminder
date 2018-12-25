package com.weatherLambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Created by briancho on 12/23/18.
 */
public class WeatherRequestHandler implements RequestHandler<RequestClass, ResponseClass> {
    public ResponseClass handleRequest(RequestClass request, Context context) {
        context.getLogger().log("Input: " + request.getRequest());

        ResponseClass response = new ResponseClass();
        response.setResponse("Hello World - " + request.getRequest());

        return response;
    }
}
