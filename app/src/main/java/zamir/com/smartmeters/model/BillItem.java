package zamir.com.smartmeters.model;

/**
 * Created by engsa on 30/06/2017.
 */

public class BillItem {
    private String month;
    private String value;
    private String meterCode;
    private String totalConsumption;
    private String mostUsedDevices;


    public BillItem(String month, String value, String meterCode, String totalConsumption, String mostUsedDevices) {
        this.month = month;
        this.value = value;
        this.meterCode = meterCode;
        this.totalConsumption = totalConsumption;
        this.mostUsedDevices = mostUsedDevices;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMeterCode() {
        return meterCode;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode;
    }

    public String getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(String totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public String getMostUsedDevices() {
        return mostUsedDevices;
    }

    public void setMostUsedDevices(String mostUsedDevices) {
        this.mostUsedDevices = mostUsedDevices;
    }
}
