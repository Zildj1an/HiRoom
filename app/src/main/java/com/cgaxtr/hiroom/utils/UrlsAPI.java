package com.cgaxtr.hiroom.utils;

public class UrlsAPI {

    private static final String BASE_PATH = "http://192.168.1.39:8080/";
    public static final String LOGIN_PATH = BASE_PATH + "user/login";
    public static final String REGISTER_PATH = BASE_PATH + "user/register";
    public static final String GET_USER = BASE_PATH + "user/{id}";
    public static final String GET_SELF_ADVERTISEMENTS = BASE_PATH + "advertisement/user/{id}";
    public static final String UPDATE_USER = BASE_PATH + "user/{id}";
    public static final String ADVERTISEMENT = BASE_PATH + "advertisement";
}
