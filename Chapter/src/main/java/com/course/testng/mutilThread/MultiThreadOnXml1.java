package com.course.testng.mutilThread;

import org.testng.annotations.Test;

public class MultiThreadOnXml1 {

    @Test
    public void test1() {
        System.out.printf("test11: Thread Id : %s%n", Thread.currentThread().getId());
    }

    @Test
    public void test2() {
        System.out.printf("test12: TThread Id : %s%n", Thread.currentThread().getId());
    }

    @Test
    public void test3() {
        System.out.printf("test13: TThread Id : %s%n", Thread.currentThread().getId());
    }
}
