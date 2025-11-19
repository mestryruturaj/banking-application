package io.enscryptingbytes.banking_application.message;

public class ExceptionMessage {
    public static final String INVALID_INPUT = "Invalid input.";

    public static final String USER_ALREADY_EXISTS = "User already exist.";
    public static final String USER_DOES_NOT_EXISTS = "User does not exist.";

    public static final String BRANCH_ALREADY_EXISTS = "Branch already exist.";
    public static final String BRANCH_DOES_NOT_EXISTS = "Branch does not exist.";

    public static final String ACCOUNT_ALREADY_EXISTS = "Account already exist.";
    public static final String ACCOUNT_DOES_NOT_EXISTS = "Account does not exist.";
    public static final String ACCOUNT_CREATION_FAILED_DUE_TO_INVALID_USER = "Account creation failed due to invalid user. Either input valid user or consider creating one.";
    public static final String ACCOUNT_CREATION_FAILED_DUE_TO_INVALID_BRANCH = "Account creation failed due to invalid branch.";
}
