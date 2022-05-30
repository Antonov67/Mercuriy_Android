package com.example.mercuriy_android;

import java.io.Serializable;

public class Device implements Serializable {
    private String adress, port, netAdress, vladelec;

    public Device(String adress, String port, String netAdress, String vladelec) {
        this.adress = adress;
        this.port = port;
        this.netAdress = netAdress;
        this.vladelec = vladelec;
    }

    public String getAdress() {
        return adress;
    }

    public String getPort() {
        return port;
    }

    public String getNetAdress() {
        return netAdress;
    }

    public String getVladelec() {
        return vladelec;
    }
}
