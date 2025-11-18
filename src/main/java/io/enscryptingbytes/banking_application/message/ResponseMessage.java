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


    public static final String BRANCH_CREATION_PASSED = "Branch creation completed.";
    public static final String BRANCH_CREATION_FAILED = "Branch creation failed.";
    public static final String BRANCH_FOUND = "Branch found.";
    public static final String BRANCH_DOES_NOT_EXIST = "Branch does not exist.";
    public static final String BRANCH_UPDATED = "Branch updated.";
    public static final String BRANCH_DELETED = "Branch deleted.";

    public static final String ACCOUNT_CREATION_PASSED = "Account creation completed.";
    public static final String ACCOUNT_CREATION_FAILED = "Account creation failed.";
    public static final String ACCOUNT_FOUND = "Account found.";
    public static final String ACCOUNT_DOES_NOT_EXIST = "Account does not exist.";
    public static final String ACCOUNT_UPDATED = "Account updated.";
    public static final String ACCOUNT_DELETED = "Account deleted.";
}
