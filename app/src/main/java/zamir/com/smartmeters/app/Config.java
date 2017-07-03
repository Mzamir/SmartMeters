package zamir.com.smartmeters.app;

/**
 * Created by MahmoudSamir on 2/15/2017.
 */

public class Config {

    //URL to our login.php file
    public static final String LOGIN_URL = "http://192.168.1.6/SmartMetersAnalyticsData/login.php";
    public static final String UPDATE_URL = "http://192.168.1.6/SmartMetersAnalyticsData/update_user.php";

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_meterCode = "meterCode";
    public static final String KEY_buildingNumber = "buildingNumber";
    public static final String KEY_apartmentNumber = "apartmentNumber";
    public static final String KEY_username = "username";
    public static final String KEY_address = "address";
    public static final String KEY_registrationToken = "registrationToken";
    public static final String KEY_created_at = "created_at";
    public static final String KEY_updated_at = "updated_at";
    public static final String RESPONSE_SUCCESS = "success";
    public static final String RESPONSE_MESSAGE = "message";
    public static final String SHARED_PREF_NAME = "user";
    public static final String FIRST_TIME_TO_LOGIN = "firstTime";

    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";
    public static final String SHARED_USER = "user";
    public static final String SHARED_NOTIFICATIONS = "notifications";
    public static final String SHARED_NOTIFICATIONS_MESSAGE = "message";
}
