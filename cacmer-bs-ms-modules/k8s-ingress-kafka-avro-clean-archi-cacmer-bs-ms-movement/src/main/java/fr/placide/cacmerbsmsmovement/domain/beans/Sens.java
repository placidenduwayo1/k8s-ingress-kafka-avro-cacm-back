package fr.placide.cacmerbsmsmovement.domain.beans;

public enum Sens {
    BUY("buy"),
    SELL("sell");
    private final String mvtSens;

    Sens(String mvtSens) {
        this.mvtSens = mvtSens;
    }

    public String getMvtSens() {
        return mvtSens;
    }
}
