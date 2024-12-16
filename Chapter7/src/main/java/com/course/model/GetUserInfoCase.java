package com.course.model;

import lombok.Data;

@Data
public class GetUserInfoCase {
    private int userId;
    private String expected;

    @Override
    public String toString() {
        return "GetUserInfoCase{" +
                "userId=" + userId +
                ", expected='" + expected + '\'' +
                '}';
    }
}
