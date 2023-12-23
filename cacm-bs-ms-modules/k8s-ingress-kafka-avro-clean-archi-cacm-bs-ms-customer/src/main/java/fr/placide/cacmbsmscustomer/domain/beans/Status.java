package fr.placide.cacmbsmscustomer.domain.beans;

public enum Status {
    ACTIVE("active"),
    ARCHIVE ("archive");
    private final String theStatus;

    Status(String theStatus) {
        this.theStatus = theStatus;
    }

    public String getTheStatus() {
        return theStatus;
    }
}