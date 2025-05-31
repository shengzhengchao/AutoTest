package com.course.utils;

import java.util.Random;

public class RandomLetters {
    public static void main(String[] args) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 大写字母
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(letters.length());
            sb.append(letters.charAt(index));
        }
        System.out.println("随机字母: " + sb.toString());
    }
}
