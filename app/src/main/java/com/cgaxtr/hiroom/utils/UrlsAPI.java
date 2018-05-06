package com.cgaxtr.hiroom.utils;

public class UrlsAPI {

    private static String BASE_PATH = "http://192.168.1.40:8080/";
    public static String LOGIN_PATH = BASE_PATH + "user/login";
    public static String REGISTER_PATH = BASE_PATH + "user/register";
    public static String GET_USER = BASE_PATH + "user/{id}";
    public static String GET_SELF_ADVERTISEMENTS = BASE_PATH + "advertisement/user/{id}";
}
