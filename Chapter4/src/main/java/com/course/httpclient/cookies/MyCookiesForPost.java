package com.course.httpclient.cookies;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPost {
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
    public void testPostCookies() throws IOException {
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
    @Test(dependsOnMethods = {"testPostCookies"})
    public void testPostWithCookies() throws IOException {
        String uri = bundle.getString("test.post.with.cookies");
        //拼接最终的测试地址
        String testUrl = this.url + uri;

        //声明一个Client对象, 设置cookies信息
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        //声明一个方法, 这个方法就是post方法
        HttpPost post = new HttpPost(testUrl);
        //添加参数
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("name", "HuHansan");
        jsonParam.put("age", "18");

//        String jsonString = "{\n" +
//                "  \"name\": \"HuHansan\",\n" +
//                "  \"age\": \"18\"\n" +
//                "}";
//        StringEntity entity = new StringEntity(jsonString, "utf-8");
//        InputStream content = entity.getContent();
//        System.out.println("请求体内容: " + content.toString());
        //设置请求头信息
        post.setHeader("content-type", "application/json");
        //将参数信息添加到post方法中
        StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
        String s = EntityUtils.toString(entity);
        System.out.println("请求entity内容为: " + s);
        post.setEntity(entity);
        //声明一个对象接收响应结果
        String result;
        //获取响应结果
        CloseableHttpResponse response = client.execute(post);
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println("post请求的结果实体为: " + result + ";");
        //将返回的响应结果字符串转化为json对象
        JSONObject jsonObject = new JSONObject(result);
        //获取"HuHansan"结果值
        String hu = jsonObject.getString("HuHansan");
        //处理结果, 判断返回结果是否符合预期
        int status = jsonObject.getInt("status");
        Assert.assertEquals("success", hu);
        Assert.assertEquals(1, status);

    }
}
