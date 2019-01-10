package com.bca.rentdevice;

public class Users {
    public String username,pin,status;

    public Users() {

    }

    public Users(String username,String pin,String status){
        this.username=username;
        this.pin=pin;
        this.status=status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
