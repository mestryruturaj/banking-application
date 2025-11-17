package io.enscryptingbytes.banking_application.message;

public class ResponseMessage {
    public static final String USER_CREATION_PASSED = "User creation completed.";
    public static final String USER_UPDATED = "User updated.";
    public static final String USER_DELETED = "User deleted.";
    public static final String USER_CREATION_FAILED = "User creation failed.";
    public static final String USER_WITH_MOBILE_NUMBER_EXIST = "User with entered mobile number already exists.";
    public static final String USER_FOUND = "User found.";
    public static final String USER_DOES_NOT_EXIST = "User does not exist.";
    public static final String SUCCESS = "Success";
    public static final String FAILED = "Failed";
    public static final String SOMETHING_WENT_WRONG = "An unexpected error occurred. Please try again later.";
    public static final String VALIDATION_FAILED = "Input validation failed. See details in 'response' field.";

}
