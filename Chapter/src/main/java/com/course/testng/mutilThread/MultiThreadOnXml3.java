package com.course.testng.mutilThread;

import org.testng.annotations.Test;

public class MultiThreadOnXml3 {

    @Test
    public void test1() {
        System.out.printf("test31: Thread Id : %s%n", Thread.currentThread().getId());
    }

    @Test
    public void test2() {
        System.out.printf("test32: TThread Id : %s%n", Thread.currentThread().getId());
    }

    @Test
    public void test3() {
        System.out.printf("test33: TThread Id : %s%n", Thread.currentThread().getId());
    }
}
