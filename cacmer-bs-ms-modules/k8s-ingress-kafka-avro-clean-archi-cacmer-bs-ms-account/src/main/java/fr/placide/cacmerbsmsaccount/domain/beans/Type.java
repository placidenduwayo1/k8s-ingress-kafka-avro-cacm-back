package fr.placide.cacmerbsmsaccount.domain.beans;

public enum Type {
    CURRENT("current"),
    SAVING("saving");

    Type(String accountType) {
        this.accountType = accountType;
    }
    private final String accountType;

    public String getAccountType() {
        return accountType;
    }
}
