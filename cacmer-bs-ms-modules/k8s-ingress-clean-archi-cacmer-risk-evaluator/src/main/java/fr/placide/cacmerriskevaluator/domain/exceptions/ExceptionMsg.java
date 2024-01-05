package fr.placide.cacmerriskevaluator.domain.exceptions;

public enum ExceptionMsg {
    REMOTE_ACCOUNT_API("Remote Account API Unreachable Exception"),
    REMOTE_CUSTOMER_API("Remote Account API Unreachable Exception");
    private final String msg;

    ExceptionMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
