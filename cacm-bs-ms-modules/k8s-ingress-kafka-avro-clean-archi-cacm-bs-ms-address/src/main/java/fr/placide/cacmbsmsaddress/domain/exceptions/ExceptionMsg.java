package fr.placide.cacmbsmsaddress.domain.exceptions;


@SuppressWarnings("LombokGetterMayBeUsed")
public enum ExceptionMsg {
    ADDRESS_EXIST ("Address Already Exists Exception"),
    FIELDS_INVALID("Address Fields Invalid Exception"),
    NOT_FOUND("Address Not Found Exception"),
    REMOTE_CUSTOMER_ERROR("Remote Customer API Unreachable Exception"),
    ADDRESS_ASSIGNED_CUSTOMER("Address Already Assigned Customer Exception");
    private final String msg;

    ExceptionMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
