package com.br.inter.domain.transaction.usercase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.br.inter.application.request.RemittanceRequest;
import com.br.inter.application.response.RemittanceResponse;
import com.br.inter.domain.transaction.fixture.RemittanceRequestFixture;
import com.br.inter.domain.transaction.fixture.RemittanceResponseFixture;
import com.br.inter.domain.transaction.gateway.TransactionGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class TransactionCreateImplTest {

    @InjectMocks
    private TransactionCreateImpl transactionCreate;

    @Mock
    private TransactionGateway transactionGateway;

    private RemittanceResponse expectedResponse;

    @BeforeEach
    void setUp() {
        expectedResponse = RemittanceResponseFixture.createValidRemittanceResponse();
        BDDMockito.when(transactionGateway.createTransaction(any(RemittanceRequest.class)))
                .thenReturn(expectedResponse);
    }

    @Test
    void testCreateTransaction() {
        RemittanceRequest request = RemittanceRequestFixture.createValidRemittanceRequest();
        RemittanceResponse response = transactionCreate.createTransaction(request);
        assertEquals(expectedResponse, response);
        verify(transactionGateway).createTransaction(request);
    }
}
