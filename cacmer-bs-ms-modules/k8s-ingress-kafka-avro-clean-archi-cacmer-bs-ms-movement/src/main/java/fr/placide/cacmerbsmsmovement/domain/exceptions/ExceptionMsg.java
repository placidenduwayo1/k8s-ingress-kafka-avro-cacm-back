package fr.placide.cacmerbsmsmovement.domain.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionMsg {
    REMOTE_ACCOUNT_API("Remote Account API Unreachable Exception"),
    REMOTE_ACCOUNT_API_TYPE("Remote Saving Account API Type Not Accepted Exception"),
    REMOTE_CUSTOMER_API("Remote Customer API Unreachable Exception"),
    MVT_FIELDS_INVALID("Movement Fields Invalid Exception"),
    MVT_ASSIGNED_ACCOUNT("Movement Already Assigned Account Exception"),
    MVT_NOT_FOUND("Movement Not Found Exception"),
    MVT_SENS("Movement Sens Invalid Exception");
    private final String msg;

    ExceptionMsg(String msg) {
        this.msg = msg;
    }
}
