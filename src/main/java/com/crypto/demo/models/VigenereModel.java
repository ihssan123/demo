package com.crypto.demo.models;

public class VigenereModel {
   private String message;
 private String Key;

    public VigenereModel(String message, String key) {
        this.message = message;
        Key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        this.Key = key;
    }


}
