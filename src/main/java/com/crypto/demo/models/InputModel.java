package com.crypto.demo.models;

public class InputModel {
    public InputModel() {
    }

    public InputModel(String message, int a, int b) {
        this.message = message;
        this.a = a;
        this.b = b;
    }

    private String message;
    private int a;
    private int b;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }




}
