package zamir.com.smartmeters.model;

/**
 * Created by engsa on 30/06/2017.
 */

public class Notification {
    private String  message;

    public Notification(String message, String title) {
        this.message = message;
//        this.title = title;
    }

    public Notification(String message) {
        this.message = message;
    }

//    public String getTitle() {
//        return title;
//    }

//    public void setTitle(String title) {
//        this.title = title;
//    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
