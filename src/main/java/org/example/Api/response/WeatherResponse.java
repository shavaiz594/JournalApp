package org.example.Api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import java.util.ArrayList;
@Getter
@Setter
public class WeatherResponse {
    private ArrayList<Weather> weather;
    private String base;
    private Main main;
    private Clouds clouds;
    @Getter
    @Setter
    public static class Clouds {
        private int all;
    }

    @Getter
    @Setter
    public static class Main {
        private double temp;
        private double feels_like;
    }
    @Getter
    @Setter
    public static class Weather {
        private String description;
        private String icon;
    }
}