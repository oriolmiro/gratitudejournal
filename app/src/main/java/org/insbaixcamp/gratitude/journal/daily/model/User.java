package org.insbaixcamp.gratitude.journal.daily.model;

import org.insbaixcamp.gratitude.journal.daily.tools.SettingsManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String userName;
    private String androidVersion;
    private String deviceBrand;
    private String deviceModel;
    private String deviceLanguage;
    private String deviceCountry;
    private String deviceTimezone;
    private String email;


    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceLanguage() {
        return deviceLanguage;
    }

    public void setDeviceLanguage(String deviceLanguage) {
        this.deviceLanguage = deviceLanguage;
    }

    public String getDeviceCountry() {
        return deviceCountry;
    }

    public void setDeviceCountry(String deviceCountry) {
        this.deviceCountry = deviceCountry;
    }

    public String getDeviceTimezone() {
        return deviceTimezone;
    }

    public void setDeviceTimezone(String deviceTimezone) {
        this.deviceTimezone = deviceTimezone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public User(String userId, String userName, JSONObject deviceInfo) {
        this.userId = userId;
        this.userName = userName;
        try {
            this.androidVersion = deviceInfo.getString("androidVersion");
            this.deviceBrand = deviceInfo.getString("deviceBrand");
            this.deviceModel = deviceInfo.getString("deviceModel");
            this.deviceLanguage = deviceInfo.getString("deviceLanguage");
            this.deviceCountry = deviceInfo.getString("deviceCountry");
            this.deviceTimezone = deviceInfo.getString("deviceTimezone");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public User(String userId, String userName,String email,  JSONObject deviceInfo) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        try {
            this.androidVersion = deviceInfo.getString("androidVersion");
            this.deviceBrand = deviceInfo.getString("deviceBrand");
            this.deviceModel = deviceInfo.getString("deviceModel");
            this.deviceLanguage = deviceInfo.getString("deviceLanguage");
            this.deviceCountry = deviceInfo.getString("deviceCountry");
            this.deviceTimezone = deviceInfo.getString("deviceTimezone");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", androidVersion='" + androidVersion + '\'' +
                ", deviceBrand='" + deviceBrand + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", deviceLanguage='" + deviceLanguage + '\'' +
                ", deviceCountry='" + deviceCountry + '\'' +
                ", deviceTimezone='" + deviceTimezone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
