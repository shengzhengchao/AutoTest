package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
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

@Test(dependsOnGroups = "loginTrue", description = "添加用户接口测试case")
public class AddUserTest {
    public void addUser() throws IOException, InterruptedException {
        //获取测试数据
        SqlSession session = DataBaseUtil.getSqlSession();
        AddUserCase addUserCase = session.selectOne("addUserCase", 1);
        System.out.println(System.getProperty("file.encoding"));
        System.out.println("获取到的addUserCase用例数据: " + addUserCase.toString());
        System.out.println("addUserCase用例的url为: " + TestConfig.addUserUrl);

        //发请求获取结果
        String result = getResult(addUserCase);
        //验证返回结果
        session.commit();
        session.close();

        session = DataBaseUtil.getSqlSession();
//        Thread.sleep(10000);
        User user = session.selectOne("addUser", addUserCase);
        System.out.println(user.toString());
        Assert.assertEquals(addUserCase.getExpected(), result);
    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        String result;
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", addUserCase.getUserName());
        jsonObject.put("password", addUserCase.getPassword());
        jsonObject.put("sex", addUserCase.getSex());
        jsonObject.put("age", addUserCase.getAge());
        jsonObject.put("permission", addUserCase.getPermission());
        jsonObject.put("isDelete", addUserCase.getIsDelete());

        //设置头信息
        post.setHeader("content-type", "application/json");
        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
        post.setEntity(stringEntity);

        //设置cookies
        TestConfig.httpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.cookieStore).build();

        //执行post请求获取结果
        HttpResponse response = TestConfig.httpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(result);
        return result;
    }

}
