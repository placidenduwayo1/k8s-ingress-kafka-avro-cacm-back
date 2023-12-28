package fr.placide.cacmerbsmsmovement.domain.beans;

public class Movement {
    private String mvtId;
    private String sens;
    private String accountId;
    private Account account;
    private String customerId;
    private Customer customer;
    private Movement(MovementBuilder builder){
        this.mvtId = builder.mvtId;
        this.sens = builder.sens;
        this.accountId= builder.accountId;
        this.account = builder.account;
        this.customerId = builder.customerId;
        this.customer = builder.customer;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Movement [" +
                "movement id:'" + mvtId + '\'' +
                ",movement sens:'" + sens + '\'' +
                ", account:" + account +
                ", customer:" + customer +
                ']';
    }

    public static class MovementBuilder {
        private String mvtId;
        private String sens;
        private String accountId;
        private Account account;
        private String customerId;
        private Customer customer;

        public MovementBuilder mvtId(String mvtId) {
            this.mvtId = mvtId;
            return this;
        }

        public MovementBuilder sens(String sens) {
            this.sens = sens;
            return this;
        }

        public MovementBuilder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public MovementBuilder account(Account account) {
            this.account = account;
            return this;
        }

        public MovementBuilder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public MovementBuilder customer(Customer customer) {
            this.customer = customer;
            return this;
        }
        public Movement build(){
            return new Movement(this);
        }
    }
}
