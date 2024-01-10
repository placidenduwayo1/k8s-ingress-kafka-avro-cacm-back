package fr.placide.cacmbsmscustomer.domain.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class Account {
    private String accountId;
    private String customerId;
}
