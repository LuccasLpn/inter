package com.br.inter.infrastructure.controller;


import com.br.inter.application.request.RemittanceRequest;
import com.br.inter.application.response.RemittanceResponse;
import com.br.inter.domain.transaction.usercase.TransactionCreate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionCreate transactionCreate;

    @PostMapping("/remittance")
    public ResponseEntity<RemittanceResponse> createRemittance(@Valid @RequestBody RemittanceRequest request) {
        RemittanceResponse response = transactionCreate.createTransaction(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
