package com.example.trtapp.Models;

public class DriverObject {

    private String requestId;
    private String name;
    private String phone;
    private String profileImageUrl;
    private String service;
    private String driverId;

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public DriverObject() {
    }

    public DriverObject(String requestId,String driverId, String name, String phone, String profileImageUrl, String service) {
        this.requestId = requestId;
        this.driverId = driverId;
        this.name = name;
        this.phone = phone;
        this.profileImageUrl = profileImageUrl;
        this.service = service;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
