package fr.placide.cacmerbsmsaccount.domain.exceptions;

public enum ExceptionMsg {
    REMOTE_CUSTOMER_UNREACHABLE("Remote Customer API Unreachable Exception"),
    REMOTE_CUSTOMER_STATUS("Remote Customer API Status Invalid Exception"),
    ACCOUNT_NOT_FOUND("Account Not Found Exception"),
    ACCOUNT_FIELDS("Account Fields Invalid Exception"),
    ACCOUNT_TYPE("Account Type Invalid Exception"),
    ACCOUNT_EXISTS("Account Already Exists with Customer Exception");
    private final String msg;

    ExceptionMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
