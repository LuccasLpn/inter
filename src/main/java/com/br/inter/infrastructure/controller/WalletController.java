package com.br.inter.infrastructure.controller;


import com.br.inter.application.request.CreateBalanceRequest;
import com.br.inter.application.response.WalletAddBalanceResponse;
import com.br.inter.domain.wallet.model.Wallet;
import com.br.inter.domain.wallet.usercase.CreateBalanceWallet;
import com.br.inter.domain.wallet.usercase.FindAllWallets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final CreateBalanceWallet createBalanceWallet;
    private final FindAllWallets findAllWallets;

    @PostMapping("/{userId}/balance")
    public ResponseEntity<WalletAddBalanceResponse> addBalance(@PathVariable UUID userId,
                                                               @RequestBody CreateBalanceRequest request) {
        Wallet wallet = createBalanceWallet.execute(request, userId);
        return new ResponseEntity<>(new WalletAddBalanceResponse(wallet), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Wallet>> findAllWallets(@PathVariable UUID userId) {
        List<Wallet> lsWallets = findAllWallets.findAllWallets(userId);
        return new ResponseEntity<>(lsWallets, HttpStatus.OK);
    }
}
