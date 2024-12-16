package com.course.config;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;

public class TestConfig {
    public static String loginUrl;
    public static String updateUserInfoUrl;
    public static String getUserInfoUrl;
    public static String getUserListUrl;
    public static String addUserUrl;

    public static HttpClient httpClient;
    public static CookieStore cookieStore;
    public static HttpClientContext context = HttpClientContext.create();
}
