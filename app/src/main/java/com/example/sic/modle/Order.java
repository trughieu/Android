package com.example.sic.modle;

public class Order {

    String tv_1;
    String tv_2;
    String tv_3;

    public Order(String tv_1, String tv_2, String tv_3) {
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

    public String getTv_2() {
        return tv_2;
    }

    public void setTv_2(String tv_2) {
        this.tv_2 = tv_2;
    }

    public String getTv_3() {
        return tv_3;
    }

    public void setTv_3(String tv_3) {
        this.tv_3 = tv_3;
    }
}
