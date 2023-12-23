package fr.placide.cacmbsmsaddress.domain.beans;

public class Customer {
    private String customerId;
    private String addressId;
    private Customer(CustomerBuilder builder){
        this.customerId= builder.customerId;
        this.addressId = builder.addressId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public static class CustomerBuilder {
        private String customerId;
        private String addressId;
        public CustomerBuilder customerId(String customerId){
            this.customerId = customerId;
            return this;
        }
        public CustomerBuilder addressId(String addressId){
            this.addressId = addressId;
            return this;
        }
        public Customer build(){
            return new Customer(this);
        }
    }
}
