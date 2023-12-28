package fr.placide.cacmbsmscustomer.domain.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Account {
    private String customerId;
}
