package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DataBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {
    @BeforeTest(groups = "loginTrue", description = "测试准备工作, 获取Httpclient对象")
    public void BeforeTest(){
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GET_USER_INFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GET_USER_LIST);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADD_USER_INFO);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATE_USER_INFO);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);

        TestConfig.httpClient = HttpClients.createDefault();
    }

    @Test(groups = "loginTrue", description = "用户登录成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession session = DataBaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 1);
        System.out.println("取到的loginCase对象为: " + loginCase.toString());
        System.out.println("登录的loginUrl为: " + TestConfig.loginUrl);

        //第一步: 发送请求
        String result = getResult(loginCase);
        System.out.println("result: " + result);
        //第二步: 验证结果
        Assert.assertEquals(loginCase.getExpected(), result);
    }

    @Test(groups = "loginFalse", description = "用户登录失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession session = DataBaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 2);
        System.out.println("取到的loginCase对象为: " + loginCase.toString());
        System.out.println("登录的loginUrl为: " + TestConfig.loginUrl);

        //第一步: 发送请求
        String result = getResult(loginCase);
        System.out.println("result值为: " + result);
        //第二步: 验证结果
        Assert.assertEquals(loginCase.getExpected(), result);
    }

    private String getResult(LoginCase loginCase) throws IOException {
        String result;
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", loginCase.getUserName());
        jsonObject.put("password", loginCase.getPassword());

        //设置请求头
        post.setHeader("content-type", "application/json");
        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
        post.setEntity(stringEntity);
        HttpResponse response = TestConfig.httpClient.execute(post, TestConfig.context);

        //获取到cookieStore
        TestConfig.cookieStore = TestConfig.context.getCookieStore();
        result = EntityUtils.toString(response.getEntity());
        System.out.println("登录结果为: " + result);
        return result;

    }
}
