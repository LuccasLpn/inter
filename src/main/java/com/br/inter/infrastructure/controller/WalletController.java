package com.br.inter.infrastructure.controller;


import com.br.inter.application.request.CreateBalanceRequest;
import com.br.inter.application.response.WalletAddBalanceResponse;
import com.br.inter.domain.wallet.model.Wallet;
import com.br.inter.domain.wallet.usercase.CreateBalanceWallet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final CreateBalanceWallet createBalanceWallet;

    @PostMapping("/{userId}/balance")
    public ResponseEntity<WalletAddBalanceResponse> addBalance(@PathVariable UUID userId,
                                                               @RequestBody CreateBalanceRequest request) {
        Wallet wallet = createBalanceWallet.execute(request, userId);
        return new ResponseEntity<>(new WalletAddBalanceResponse(wallet), HttpStatus.CREATED);
    }
}
