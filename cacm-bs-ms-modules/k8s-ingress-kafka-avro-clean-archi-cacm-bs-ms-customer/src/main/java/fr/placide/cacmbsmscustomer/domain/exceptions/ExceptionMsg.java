package fr.placide.cacmbsmscustomer.domain.exceptions;

public enum ExceptionMsg {
    CUSTOMER_NOT_FOUND("Customer Not Found Exception"),
    CUSTOMER_EXISTS("Customer Already Exists Exception"),
    REMOTE_ADDRESS_API("Remote Address Api Exception"),
    FIELDS_INVALID("Customer Fields Invalid Exception"),
    NOT_FOUND("Customer Not Found Exception"),
    RISK_INVALID("Customer Risk Value Invalid Exception"),
    STATUS_INVALID("Customer Status Value Invalid Exception");

    private final String msg;

    ExceptionMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
