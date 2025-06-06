package com.course.utils;

import com.course.model.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFile {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.CHINA);

    public static String getUrl(InterfaceName name){
        String address = bundle.getString("test.url");
        String uri = "";
        String testUrl = "";

        if (name == InterfaceName.GET_USER_LIST){
            uri =bundle.getString("getUserList.uri");
        }

        if (name == InterfaceName.LOGIN){
            uri = bundle.getString("login.uri");
        }

        if (name == InterfaceName.UPDATE_USER_INFO){
            uri = bundle.getString("updateUserInfo.uri");
        }

        if (name == InterfaceName.GET_USER_INFO){
            uri = bundle.getString("getUserInfo.uri");
        }

        if (name == InterfaceName.ADD_USER_INFO){
            uri = bundle.getString("addUser.uri");
        }

        testUrl = address + uri;
        return testUrl;
    }
}
