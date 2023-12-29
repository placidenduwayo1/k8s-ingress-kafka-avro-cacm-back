package fr.placide.cacmerbsmsmovement.domain.beans;

import lombok.Getter;

@Getter
public enum Sens {
    BUY("buy"),
    SELL("sell");
    private final String mvtSens;

    Sens(String mvtSens) {
        this.mvtSens = mvtSens;
    }

}
