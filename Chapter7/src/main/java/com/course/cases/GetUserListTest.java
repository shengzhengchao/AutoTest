package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
import com.course.model.User;
import com.course.utils.DataBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetUserListTest {

    @Test(dependsOnGroups = "loginTrue", description = "获取性别为男(sex=1)的用户信息")
    public void getUserListInfo() throws IOException {
        //获取测试数据
        SqlSession session = DataBaseUtil.getSqlSession();
        GetUserListCase getUserListCase = session.selectOne("getUserListCase", 1);
        System.out.println("获取到的getUserLIstCase用例数据: " + getUserListCase.toString());
        System.out.println("getUserListCase用例的url为: " + TestConfig.getUserListUrl);

        //发送请求获取结果
        JSONArray resultJson = getJsonResult(getUserListCase);

        //验证
        List<User> userList = session.selectList(getUserListCase.getExpected(), getUserListCase);
        for (User user : userList) {
            System.out.println("获取的user: " + user.toString());
        }

        //将userList转成Json数组
        JSONArray userListJson = new JSONArray(userList);
        Assert.assertEquals(userListJson.length(), resultJson.length());

        for (int i = 0; i < resultJson.length(); i++) {
            JSONObject expect = (JSONObject) resultJson.get(i);
            JSONObject actual = (JSONObject) userListJson.get(i);
            Assert.assertEquals(expect.toString(), actual.toString());
        }
    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        String result;
        HttpPost post = new HttpPost(TestConfig.getUserListUrl);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", getUserListCase.getUserName());
        jsonObject.put("sex", getUserListCase.getSex());
        jsonObject.put("age", getUserListCase.getAge());

        post.setHeader("content-type", "application/json");
        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
        post.setEntity(stringEntity);
        //设置cookies
        TestConfig.httpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.cookieStore).build();
        HttpResponse response = TestConfig.httpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        JSONArray jsonArray = new JSONArray(result);
        return jsonArray;
    }
}