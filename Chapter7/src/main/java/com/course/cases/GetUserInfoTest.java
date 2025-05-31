package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.utils.DataBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {

    @Test(dependsOnGroups = "loginTrue", description = "获取userId为1的用户的测试用例")
    public void getUserInfo() throws IOException{
        SqlSession session = DataBaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase", 1);
        System.out.println(System.getProperty("file.encoding"));
        System.out.println("获取到的getUserInfoCase用例数据: " + getUserInfoCase.toString());
        System.out.println("GetUserInfoCase用例的url为: " + TestConfig.getUserInfoUrl);

        JSONArray resultJson = getJsonResult(getUserInfoCase);
        resultJson = new JSONArray(resultJson.getString(0));

        User user = session.selectOne(getUserInfoCase.getExpected(), getUserInfoCase);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        JSONArray jsonArray = new JSONArray(userList);
        System.out.println("jsonArray: " + jsonArray.toString());
        System.out.println("resultJson: " + resultJson.toString());

        Assert.assertEquals(jsonArray.toString(), resultJson.toString());
    }

    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", getUserInfoCase.getUserId());

        post.setHeader("content-type", "application/json");

        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
        post.setEntity(stringEntity);

        TestConfig.httpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.cookieStore).build();
        HttpResponse response = TestConfig.httpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        List<String> resultList = Arrays.asList(result);
        JSONArray jsonArray = new JSONArray(resultList);
        return jsonArray;
    }
}
