package com.course.testng.mutilThread;

import org.testng.annotations.Test;

public class MultiThreadOnXml2 {

    @Test
    public void test1() {
        System.out.printf("test21: Thread Id : %s%n", Thread.currentThread().getId());
    }

    @Test
    public void test2() {
        System.out.printf("test22: TThread Id : %s%n", Thread.currentThread().getId());
    }

    @Test
    public void test3() {
        System.out.printf("test23: TThread Id : %s%n", Thread.currentThread().getId());
    }
}
