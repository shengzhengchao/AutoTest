package com.course.httpclient.demo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MyHttpClient {

    @Test
    public void test1() throws IOException {
        //用来存放我们的结果
        String result;
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        //这个是用来执行get方法的
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        System.out.println(result);
    }
}
