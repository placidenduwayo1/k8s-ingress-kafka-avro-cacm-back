package fr.placide.cacmerbsmsmovement.domain.beans;

public class Movement {
    private String mvtId;
    private String sens;
    private double amount;
    private String createdAt;
    private String accountId;
    private Account account;
    private Movement(MovementBuilder builder){
        this.mvtId = builder.mvtId;
        this.sens = builder.sens;
        this.amount = builder.amount;
        this.createdAt = builder.createdAt;
        this.accountId= builder.accountId;
        this.account = builder.account;
    }

    public String getMvtId() {
        return mvtId;
    }

    public void setMvtId(String mvtId) {
        this.mvtId = mvtId;
    }

    public String getSens() {
        return sens;
    }

    public void setSens(String sens) {
        this.sens = sens;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Movement [" +
                "sens:'" + sens + '\'' +
                ", amount:" + amount +
                ", created at:'" + createdAt + '\'' +
                ", account:" + account +
                ']';
    }

    public static class MovementBuilder {
        private String mvtId;
        private String sens;
        private double amount;
        private String createdAt;
        private String accountId;
        private Account account;

        public MovementBuilder mvtId(String mvtId) {
            this.mvtId = mvtId;
            return this;
        }

        public MovementBuilder sens(String sens) {
            this.sens = sens;
            return this;
        }

        public MovementBuilder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public MovementBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public MovementBuilder accountId(String accountId){
            this.accountId = accountId;
            return this;
        }

        public MovementBuilder account(Account account) {
            this.account = account;
            return this;
        }
        public Movement build(){
            return new Movement(this);
        }
    }
}
