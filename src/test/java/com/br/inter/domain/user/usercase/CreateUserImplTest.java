package com.br.inter.domain.user.usercase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.br.inter.application.request.CreateUserRequest;
import com.br.inter.domain.user.fixture.UserFixture;
import com.br.inter.domain.user.gateway.CreateUserGateway;
import com.br.inter.domain.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CreateUserImplTest {

    @InjectMocks
    private CreateUserImpl createUserImpl;

    @Mock
    private CreateUserGateway createUserGateway;

    @BeforeEach
    void setUp() {
        BDDMockito.when(createUserGateway.save(any(User.class)))
                .thenReturn(UserFixture.createMockUser());
    }

    @Test
    void testExecuteCreatesUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("Fulano de Tal");
        request.setEmail("fulano@example.com");
        request.setPassword("senha123");
        request.setDocument("12345678901");
        User createdUser = createUserImpl.execute(request);
        assertNotNull(createdUser, "The created user should not be null.");
        assertEquals("Fulano de Tal", createdUser.getName(), "User name should match.");
        assertEquals("fulano@example.com", createdUser.getEmail(), "User email should match.");
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        BDDMockito.verify(createUserGateway).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals("Fulano de Tal", savedUser.getName(), "Captured user name should match.");
        assertEquals("fulano@example.com", savedUser.getEmail(), "Captured user email should match.");
        assertEquals("senha123", savedUser.getPassword(), "Captured user password should match.");
        assertEquals("12345678901", savedUser.getDocument(), "Captured user document should match.");
    }
}
