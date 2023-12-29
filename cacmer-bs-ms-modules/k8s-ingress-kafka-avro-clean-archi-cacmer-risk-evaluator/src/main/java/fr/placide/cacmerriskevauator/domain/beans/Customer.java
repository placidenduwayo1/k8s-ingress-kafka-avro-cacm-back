package fr.placide.cacmerriskevauator.domain.beans;

public class Customer {
    private String customerId;
    private String firstname;
    private String lastname;
    private String risk;
    private String status;
    private Customer(CustomerBuilder builder){
        this.customerId= builder.customerId;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.risk = builder.risk;
        this.status = builder.status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class CustomerBuilder {
        private String customerId;
        private String firstname;
        private String lastname;
        private String risk;
        private String status;

        public CustomerBuilder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public CustomerBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public CustomerBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public CustomerBuilder risk(String risk) {
            this.risk = risk;
            return this;
        }

        public CustomerBuilder status(String status) {
            this.status = status;
            return this;
        }

        public Customer build(){
            return new Customer(this);
        }
    }
}
