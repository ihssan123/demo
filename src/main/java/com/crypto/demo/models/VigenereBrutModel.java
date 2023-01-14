package com.crypto.demo.models;

public class VigenereBrutModel {
    private  String message;
    private int keyLength;

    public VigenereBrutModel(String message, int keyLength) {
        this.message = message;
        this.keyLength = keyLength;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setKeyLength(int keyLength) {
        this.keyLength = keyLength;
    }

    public String getMessage() {
        return message;
    }

    public int getKeyLength() {
        return keyLength;
    }
}
