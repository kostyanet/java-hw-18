public enum UserServiceErrors {

    NOT_FOUND("User not found!"),
    EMAIL_EXISTS("Email is not unique!"),
    READONLY_FIELD("Field cannot be modified!");

    private final String message;

    UserServiceErrors(String msg) {
        message = msg;
    }

    public String getMessage() {
        return message;
    }
}
