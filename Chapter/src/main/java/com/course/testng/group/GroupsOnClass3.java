package com.course.testng.group;

import org.testng.annotations.Test;

@Test(groups = "teacher")
public class GroupsOnClass3 {
    public void teacher1(){
        System.out.println("GroupsOnClass333中的teacher11111运行");
    }

    public void teacher2(){
        System.out.println("GroupsOnClass333中的teacher22222运行");
    }

}
