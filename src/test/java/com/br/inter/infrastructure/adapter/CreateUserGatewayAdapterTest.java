package com.br.inter.infrastructure.adapter;

import com.br.inter.domain.user.model.User;
import com.br.inter.infrastructure.fixture.UserFixture;
import com.br.inter.infrastructure.repository.UserRepository;
import com.br.inter.infrastructure.repository.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
class CreateUserGatewayAdapterTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserGatewayAdapter createUserGatewayAdapter;

    @Test
    void testSaveUser() {
        User user = UserFixture.createUser();
        UserEntity savedEntity = new UserEntity();
        savedEntity.setId(1L);
        BDDMockito.when(userRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(savedEntity);
        User result = createUserGatewayAdapter.save(user);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(savedEntity.getId(), result.getId());
    }

}