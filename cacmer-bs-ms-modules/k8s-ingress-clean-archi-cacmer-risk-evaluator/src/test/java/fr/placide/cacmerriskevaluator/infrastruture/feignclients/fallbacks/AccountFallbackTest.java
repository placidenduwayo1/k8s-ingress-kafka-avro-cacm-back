package fr.placide.cacmerriskevaluator.infrastruture.feignclients.fallbacks;

import fr.placide.cacmerriskevaluator.infrastruture.feignclients.models.AccountModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountFallbackTest {
    private static final AccountFallback UNDER_TEST = new AccountFallback();

    @Test
    void getRemoteAccountById() {
        String accountId="id";
        AccountModel model = UNDER_TEST.getRemoteAccountById(accountId);
        Assertions.assertNull(model);
    }
}