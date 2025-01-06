package mvc.som.constant;

public class AppConstants {

    private AppConstants() {

    }

    public static final String PHONE_REGEXP = "^(?:\\D*\\d\\D*){3,}";

    public static final String EMAIL_REGEXP = "^([\\w\\-\\._]+)@(\\w+)\\.(\\w+)";

    public static final String REQUIRED_FIELD = "This field cannot be empty.";

    public static final String INVALID_VALUE = "Invalid value entered.";

}
