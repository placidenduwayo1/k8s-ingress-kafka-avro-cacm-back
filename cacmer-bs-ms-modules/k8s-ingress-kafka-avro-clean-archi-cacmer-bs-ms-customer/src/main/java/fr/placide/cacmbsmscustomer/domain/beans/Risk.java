package fr.placide.cacmbsmscustomer.domain.beans;

public enum Risk {
    LOW("low"),
    HIGH("high");
    private final String theRisk;

    Risk(String theRisk) {
        this.theRisk = theRisk;
    }
    public String getTheRisk() {
        return theRisk;
    }
}
