package com.example.trtapp.requestListRecyclerView;

public class RequestObject {

    private String requestId;
    private String customerRideId;
    private String service;
    private String destination;
    private String destinationLat;
    private String destinationLng;

    private String name;
    private String phone;
    private String profileImageUrl;

    public RequestObject() {
    }

    public RequestObject(String requestId,String customerRideId, String service, String destination, String destinationLat, String destinationLng, String name, String phone, String profileImageUrl) {
        this.requestId = requestId;
        this.customerRideId = customerRideId;
        this.service = service;
        this.destination = destination;
        this.destinationLat = destinationLat;
        this.destinationLng = destinationLng;
        this.name = name;
        this.phone = phone;
        this.profileImageUrl = profileImageUrl;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCustomerRideId() {
        return customerRideId;
    }

    public void setCustomerRideId(String customerRideId) {
        this.customerRideId = customerRideId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(String destinationLat) {
        this.destinationLat = destinationLat;
    }

    public String getDestinationLng() {
        return destinationLng;
    }

    public void setDestinationLng(String destinationLng) {
        this.destinationLng = destinationLng;
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
}
