package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
import com.course.model.User;
import com.course.utils.DataBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdateUserInfoTest {
    @Test(dependsOnGroups = "loginTrue", description = "更改用户信息测试用例")
    public void updateUserInfo() throws IOException {
        SqlSession session = DataBaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase", 1);
        System.out.println(System.getProperty("file.encoding"));
        System.out.println("获取到的更新用户信息用例数据: " + updateUserInfoCase.toString());
        System.out.println("更行用户信息的用例的url为: " + TestConfig.updateUserInfoUrl);

        int result = getResult(updateUserInfoCase);
        User user = session.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);

        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
    }



    @Test(dependsOnGroups = "loginTrue", description = "删除用户信息测试用例")
    public void deleteUser() throws IOException {
        SqlSession session = DataBaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase", 2);
        System.out.println(System.getProperty("file.encoding"));
        System.out.println("获取到的删除用户信息用例数据: " + updateUserInfoCase.toString());
        System.out.println("删除用户信息的用例的url为: " + TestConfig.updateUserInfoUrl);

        int result = getResult(updateUserInfoCase);
        User user = session.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);

        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
    }

    private int getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.updateUserInfoUrl);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", updateUserInfoCase.getUserId());
        jsonObject.put("userName", updateUserInfoCase.getUserName());
        jsonObject.put("sex", updateUserInfoCase.getSex());
        jsonObject.put("age", updateUserInfoCase.getAge());
        jsonObject.put("permission", updateUserInfoCase.getPermission());
        jsonObject.put("isDelete", updateUserInfoCase.getIsDelete());
        //设置请求头
        post.setHeader("content-type", "application/json");
        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
        post.setEntity(stringEntity);

        TestConfig.httpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.cookieStore).build();
        HttpResponse response = TestConfig.httpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        return Integer.parseInt(result);
    }
}
