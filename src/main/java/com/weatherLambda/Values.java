package com.weatherLambda;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by briancho on 1/6/19.
 */
public class Values {
    @JsonProperty("temp")
    public String temp;

    @JsonProperty("temp_max")
    public String maxTemp;

    @JsonProperty("temp_min")
    public String minTemp;

    @JsonProperty("humidity")
    public String humidity;
}
