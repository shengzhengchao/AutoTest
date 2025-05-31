package com.course.testng.parameter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {

    @Test(dataProvider = "data")
    public void testDataProvider(String name, int age) {
        System.out.println("name = " + name + " , age = " + age);
    }

    @DataProvider(name="data")
    public Object [][] providerData(){
        return new Object[][]{
                {"Tom", 10},
                {"Jerry", 20},
                {"Mary", 30},
        };
    }

    //跟test方法名传递不同的参数
    @Test(dataProvider = "methodData")
    public void test1(String name, int age){
        System.out.println("test1111 : name = " + name + " , age = " + age);
    }

    @Test(dataProvider = "methodData")
    public void test2(String name, int age){
        System.out.println("test2222 : name = " + name + " , age = " + age);
    }

    @DataProvider(name = "methodData")
    public Object[][] methodProviderData(Method method){
        Object[][] result = null;
        if("test1".equals(method.getName())){
            result = new Object[][]{
                    {"Tom", 15},
                    {"Jerry", 25}
            };
        }else if("test2".equals(method.getName())){
            result = new Object[][]{
                    {"Jack", 35},
                    {"Mary", 45}
            };
        }
        return result;
    }
}
