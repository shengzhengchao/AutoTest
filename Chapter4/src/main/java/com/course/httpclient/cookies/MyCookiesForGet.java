package com.course.httpclient.cookies;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

//运行moco环境命令: java -jar ./Chapter3/moco-runner-0.11.0-standalone.jar http -p 8888 -c ./Chapter3/startupWithCookies.json

public class MyCookiesForGet {
    private String url;
    private ResourceBundle bundle;
    private HttpClientContext context;
    private CookieStore cookieStore;

    @BeforeTest
    public void beforeTest(){

        context = HttpClientContext.create();
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");

    }

    @Test
    public void testGetCookies() throws IOException {
        String result;
        String uri = bundle.getString("test.uri");
        String testUrl = this.url + uri;
        HttpGet httpGet = new HttpGet(testUrl);
        //创建httpclient对象
        HttpClient client = HttpClients.createDefault();
        HttpResponse response = client.execute(httpGet, context);
        HttpEntity entity = response.getEntity();
        result = EntityUtils.toString(entity, "utf-8");
        System.out.println(result);


        //获取cookies信息
        cookieStore = context.getCookieStore();
        List<Cookie> cookieList = cookieStore.getCookies();
        for (Cookie cookie : cookieList) {
            String name = cookie.getName();
            String value = cookie.getValue();
            String domain = cookie.getDomain();
            String path = cookie.getPath();
            System.out.println("cookieName: " + name
            + ";\ncookieValue: " + value
                    + ";\ncookieDomain: " + domain
                    + ";\ncookiePath: " + path);
        }
    }

    //此方法依赖于方法testGetCookies()的执行
    @Test(dependsOnMethods = {"testGetCookies"})
    public void testGetWithCookies() throws IOException {
        String uri = bundle.getString("test.get.with.cookies");
        String testUrl = this.url + uri;

        HttpGet get = new HttpGet(testUrl);
        //设置cookies信息
        HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpResponse response = client.execute(get);

        //获取响应的状态码
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode = " + statusCode);

        if(statusCode == 200) {
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);
        }else {
            System.out.println("响应失败!!!");
        }
    }
}
