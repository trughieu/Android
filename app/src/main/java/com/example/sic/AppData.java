package com.example.sic;

import android.graphics.Bitmap;

public class AppData {
    private static AppData instance;
    private String appTitle;
    private boolean register;
    private String documentType;
    boolean chip;
    private String phone;
    private String email;
    private String JWT;
    private boolean KakPrivate;

    public boolean isKakPrivate() {
        return KakPrivate;
    }

    public void setKakPrivate(boolean kakPrivate) {
        KakPrivate = kakPrivate;
    }

    public String getJWT() {
        return JWT;
    }

    public void setJWT(String JWT) {
        this.JWT = JWT;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isChip() {
        return chip;
    }

    public void setChip(boolean chip) {
        this.chip = chip;
    }

    private Bitmap imageFront;
    private Bitmap imageBack;
    private Bitmap Face;

    public Bitmap getFace() {
        return Face;
    }

    public void setFace(Bitmap face) {
        Face = face;
    }

    public Bitmap getImageFront() {
        return imageFront;
    }

    public void setImageFront(Bitmap imageFront) {
        this.imageFront = imageFront;
    }

    public Bitmap getImageBack() {
        return imageBack;
    }

    public void setImageBack(Bitmap imageBack) {
        this.imageBack = imageBack;
    }

    private AppData() {}

    public static synchronized AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public void resetImage() {
        imageBack = null;
        imageFront=null;
    }
}
