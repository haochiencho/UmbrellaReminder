package com.weatherLambda;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by briancho on 1/6/19.
 */
public class Weather {
    @JsonProperty("coord")
    public String coord;

    @JsonProperty("weather")
    public Description description;

    @JsonProperty("main")
    public Values values;
}
