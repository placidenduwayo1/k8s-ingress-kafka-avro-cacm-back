package fr.placide.cacmerbsmsaccount.domain.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionMsg {
    REMOTE_CUSTOMER_UNREACHABLE("Remote Customer API Unreachable Exception, maybe api is down"),
    REMOTE_CUSTOMER_STATUS("Remote Customer API Status Invalid Exception, Customer deactivated"),
    ACCOUNT_NOT_FOUND("Account Not Found Exception"),
    ACCOUNT_FIELDS("Account Fields Invalid Exception"),
    ACCOUNT_TYPE("Account Type Invalid Exception");
    private final String msg;

    ExceptionMsg(String msg) {
        this.msg = msg;
    }

}
