package com.techassignment.createqrcode.model;

import android.util.Log;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by creson on 1/30/15.
 */
public class QRCode implements Serializable {

    public static final String QR_CODE_URL = "https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=";

    private String data;
    private String url;

    public QRCode(String data, String url) {
        this.data = data;
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static List<QRCode> getInitialData() {
        QRCode qrCode0 = new QRCode("YouNow", QR_CODE_URL + "YouNow");
        QRCode qrCode1 = new QRCode("Eran", QR_CODE_URL + "Eran");
        QRCode qrCode2 = new QRCode("Dax", QR_CODE_URL + "Dax");
        QRCode qrCode3 = new QRCode("Adi", QR_CODE_URL + "Adi");
        QRCode qrCode4 = new QRCode("Erez", QR_CODE_URL + "Erez");
        QRCode qrCode5 = new QRCode("Bikrant", QR_CODE_URL + "Bikrant");

        List<QRCode> initialQRCodes = new ArrayList<QRCode>();

        initialQRCodes.add(qrCode0);
        initialQRCodes.add(qrCode1);
        initialQRCodes.add(qrCode2);
        initialQRCodes.add(qrCode3);
        initialQRCodes.add(qrCode4);
        initialQRCodes.add(qrCode5);

        return initialQRCodes;
    }

    public static QRCode getRandomQRCode() {
        SecureRandom random = new SecureRandom();
        String str = new BigInteger(130, random).toString(32);

        QRCode randomQRCode = new QRCode(str, QR_CODE_URL + str);

        Log.i("QRCode Model", String.format("New Random Text: %s generated", randomQRCode.getData()));

        return randomQRCode;
    }

    public static QRCode createNewQRCode(String data) {
        QRCode newQRCode = new QRCode(data, QR_CODE_URL + data);

        Log.i("QRCode Model", String.format("New Random Text: %s generated", newQRCode.getData()));

        return newQRCode;
    }

}
