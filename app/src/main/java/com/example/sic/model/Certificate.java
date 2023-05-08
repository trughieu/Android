package com.example.sic.model;

import java.util.List;

public class Certificate {
    public String thumbprint;
    public String credentialId;
    public Integer version;
    public String remainingSigningCounter;
    public String status;
    public List<String> certificates;
    public String issuerDN;
    public String serialNumber;
    public String subjectDN;
    public String validFrom;
    public String validTo;
    public String purpose;
    public Boolean kakChanged;
    public String authMode;
    String tv_1;
    int tv_2;
    String tv_3;


    public Certificate(String tv_1, int tv_2, String tv_3) {
        this.tv_1 = tv_1;
        this.tv_2 = tv_2;
        this.tv_3 = tv_3;
    }

    public String getTv_1() {
        return tv_1;
    }

    public void setTv_1(String tv_1) {
        this.tv_1 = tv_1;
    }

    public int getTv_2() {
        return tv_2;
    }

    public void setTv_2(int tv_2) {
        this.tv_2 = tv_2;
    }

    public String getTv_3() {
        return tv_3;
    }

    public void setTv_3(String tv_3) {
        this.tv_3 = tv_3;
    }
}