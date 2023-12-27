package fr.placide.cacmbsmscustomer.domain.exceptions.business_exc;

public class RemoteAddressApiException extends Exception{
    public RemoteAddressApiException(String address) {
        super(address);
    }
}
