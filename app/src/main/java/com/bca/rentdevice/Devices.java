package com.bca.rentdevice;



public class Devices {
    public String devicename, codereturn, renter, statuscard;
    public String statusdb,oriposition;
    public String img;

    public Devices() {

    }

    public Devices(String orpos,String devname, String codedev, String renter, String statusdb, String statuscard, String img) {
        this.oriposition = orpos;
        this.devicename = devname;
        this.codereturn = codedev;
        this.renter = renter;
        this.statusdb = statusdb;
        this.statuscard = statuscard;
        this.img = img;

    }

    public String getOriposition() {
        return oriposition;
    }

    public void changeOnRent(String renter, String statuscard){
        this.renter = renter;
        this.statuscard = statuscard;


    }

    public void changeOnReturn(String renter,String statuscard){
        this.renter = renter;
        this.statuscard = statuscard;

    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public void setCodereturn(String codereturn) {
        this.codereturn = codereturn;
    }

    public void setRenter(String renter) {
        this.renter = renter;
    }

    public void setStatuscard(String statuscard) {
        this.statuscard = statuscard;
    }

    public void setStatusdb(String statusdb) {
        this.statusdb = statusdb;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDevicename() {
        return devicename;
    }

    public String getCodereturn() {
        return codereturn;
    }

    public String getRenter() {
        return renter;
    }

    public String getStatuscard() {
        return statuscard;
    }

    public String getStatusdb() {
        return statusdb;
    }

    public String getImg() { return img; }
}
