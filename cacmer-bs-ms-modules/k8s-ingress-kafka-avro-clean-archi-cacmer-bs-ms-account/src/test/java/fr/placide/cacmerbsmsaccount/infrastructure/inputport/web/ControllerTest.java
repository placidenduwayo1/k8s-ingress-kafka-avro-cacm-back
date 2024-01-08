package fr.placide.cacmerbsmsaccount.infrastructure.inputport.web;

import fr.placide.cacmerbsmsaccount.domain.inputport.AccountInputService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ControllerTest {
    @Mock
    private AccountInputService accountInputService;
    @InjectMocks
    private Controller underTest;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getWelcome() {
        //PREPARE

        //EXECUTE
        //TEST
    }

    @Test
    void getAccountsByCustomersName() {
    }

    @Test
    void getAccountByCustomerId() {
    }

    @Test
    void create() {
    }

    @Test
    void getAll() {
    }

    @Test
    void get() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }
}