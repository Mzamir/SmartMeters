package zamir.com.smartmeters.model;

/**
 * Created by engsa on 04/07/2017.
 */

public class Device {
    String name;
    String consumption;

    public Device(String consumption, String name) {
        this.consumption = consumption;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }
}
