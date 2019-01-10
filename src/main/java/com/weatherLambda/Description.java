package com.weatherLambda;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by briancho on 1/6/19.
 */
public class Description {
    @JsonProperty("main")
    public String mainDescription;

    @JsonProperty("description")
    public String description;
}
