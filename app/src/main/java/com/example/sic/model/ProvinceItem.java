package com.example.sic.model;

public class ProvinceItem {
    private String name;
    private String id;

    public ProvinceItem(String name, String id) {
        this.name = name;
        this.id = id;
    }
    @Override
    public String toString() {
        return name; // Hoặc bạn có thể trả về một chuỗi mô tả khác tùy ý
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
