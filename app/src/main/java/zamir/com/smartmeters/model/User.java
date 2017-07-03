package zamir.com.smartmeters.model;

import java.util.ArrayList;

/**
 * Created by MahmoudSamir on 2/20/2017.
 */
public class User {
    private String email;
    private String password;
    private String meterCode;
    private String buildingNumber;
    private String apartmentNumber;
    private String username;
    private String address;
    private String registrationToken;
    private String updated_at;
    private String created_at;

    private ArrayList<String> myDevices = null;

    public User() {
    }

    public User(String email, String password, String meterCode, String buildingNumber, String apartmentNumber, String username, String address) {
        this.email = email;
        this.password = password;
        this.meterCode = meterCode;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.username = username;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMeterCode() {
        return meterCode;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void addDevice(String deviceName) {
        if (this.myDevices == null) {
            this.myDevices = new ArrayList<>();
        }
        this.myDevices.add(deviceName);
    }

    public ArrayList<String> getMyDevices() {
        return this.myDevices;
    }

    public void setMyDevices(ArrayList<String> myDevices) {
        if (this.myDevices == null) {
            this.myDevices = new ArrayList<>();
        }
        this.myDevices = myDevices;
    }
}
