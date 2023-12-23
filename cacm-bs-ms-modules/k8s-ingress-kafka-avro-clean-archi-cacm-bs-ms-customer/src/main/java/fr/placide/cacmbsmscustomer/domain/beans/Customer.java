package fr.placide.cacmbsmscustomer.domain.beans;

public class Customer {
    private String customerId;
    private String firstname;
    private String lastname;
    private String createdAt;
    private String risk;
    private String status;
    private String addressId;
    private Address address;

    private Customer(CustomerBuilder builder){
        customerId = builder.customerId;
        firstname = builder.firstname;
        lastname = builder.lastname;
        createdAt = builder.createdAt;
        risk = builder.risk;
        status = builder.status;
        addressId = builder.addressId;
        address = builder.address;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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
    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public static class CustomerBuilder {
        private String customerId;
        private String firstname;
        private String lastname;
        private String createdAt;
        private String risk;
        private String status;
        private String addressId;
        private Address address;

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

        public CustomerBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
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
        public CustomerBuilder  addressId(String addressId){
            this.addressId = addressId;
            return this;
        }
        public CustomerBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public Customer build(){
            return new Customer(this);
        }
    }

    @Override
    public String toString() {
        return "customer [" +
                "id: '" + customerId + '\'' +
                ", firstname: '" + firstname + '\'' +
                ", lastname: '" + lastname + '\'' +
                ", created at: '" + createdAt + '\'' +
                ", risk: '" + risk + '\'' +
                ", status: '" + status + '\'' +
                ", address: " + address +
                ']';
    }
}
