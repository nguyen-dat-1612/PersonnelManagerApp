package com.managerapp.personnelmanagerapp.domain.model;

public class RequestList {
    private int maLoaiDon;
    private String tenLoaiDon;

    public RequestList(int maLoaiDon, String tenLoaiDon) {
        this.maLoaiDon = maLoaiDon;
        this.tenLoaiDon = tenLoaiDon;
    }

    public int getMaLoaiDon() {
        return maLoaiDon;
    }

    public void setMaLoaiDon(int maLoaiDon) {
        this.maLoaiDon = maLoaiDon;
    }

    public String getTenLoaiDon() {
        return tenLoaiDon;
    }

    public void setTenLoaiDon(String tenLoaiDon) {
        this.tenLoaiDon = tenLoaiDon;
    }
}
