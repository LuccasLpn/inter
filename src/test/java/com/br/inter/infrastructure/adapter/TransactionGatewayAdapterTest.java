package com.br.inter.infrastructure.adapter;

import com.br.inter.application.request.RemittanceRequest;
import com.br.inter.application.response.RemittanceResponse;
import com.br.inter.domain.transaction.fixture.RemittanceRequestFixture;
import com.br.inter.domain.transaction.fixture.RemittanceResponseFixture;
import com.br.inter.infrastructure.repository.enums.TransactionType;
import com.br.inter.infrastructure.strategy.transfer.TransactionStrategyFactory;
import com.br.inter.infrastructure.strategy.transfer.TransactionStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionGatewayAdapterTest {

    @Mock
    private TransactionStrategyFactory transactionStrategyFactory;

    @Mock
    private TransactionStrategy transactionStrategy;

    @InjectMocks
    private TransactionGatewayAdapter transactionGatewayAdapter;

    private RemittanceRequest validRemittanceRequest;
    private RemittanceResponse validRemittanceResponse;

    @BeforeEach
    void setUp() {
        validRemittanceRequest = RemittanceRequestFixture.createValidRemittanceRequest();
        validRemittanceResponse = RemittanceResponseFixture.createValidRemittanceResponse();
    }

    @Test
    void shouldCreateTransactionSuccessfully() {
        BDDMockito.when(transactionStrategyFactory.getStrategy(TransactionType.TRANSFER)).thenReturn(transactionStrategy);
        BDDMockito.when(transactionStrategy.processTransaction(validRemittanceRequest)).thenReturn(validRemittanceResponse);
        RemittanceResponse response = transactionGatewayAdapter.createTransaction(validRemittanceRequest);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(validRemittanceResponse, response);
        BDDMockito.then(transactionStrategyFactory).should(times(1)).getStrategy(TransactionType.TRANSFER);
        BDDMockito.then(transactionStrategy).should(times(1)).processTransaction(validRemittanceRequest);
    }

    @Test
    void shouldThrowExceptionWhenStrategyNotFound() {
        BDDMockito.when(transactionStrategyFactory.getStrategy(TransactionType.CONVERSION))
                .thenThrow(new IllegalArgumentException("No strategy found for transaction type: CONVERSION"));
        validRemittanceRequest.setTransactionType(TransactionType.CONVERSION);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionGatewayAdapter.createTransaction(validRemittanceRequest);
        });
        Assertions.assertEquals("No strategy found for transaction type: CONVERSION", exception.getMessage());
        BDDMockito.then(transactionStrategyFactory).should(times(1)).getStrategy(TransactionType.CONVERSION);
    }
}