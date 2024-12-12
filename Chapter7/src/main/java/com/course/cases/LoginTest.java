package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.utils.ConfigFile;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.BeforeTest;

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

    public void Login(){

    }
}
