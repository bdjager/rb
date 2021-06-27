package nl.rabobank.controller;

import nl.rabobank.authorizations.Authorization;
import nl.rabobank.service.AccountAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class AccountAccessController {

    @Autowired
    private AccountAccessService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> up() {
        service.insertAccounts();
        return ResponseEntity.ok("up");
    }

    // Users need to be able to retrieve a list of accounts they have read or write access for
    @GetMapping(path = "/accounts/{authorization}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getAccountsWithAuthorisation(@PathVariable final Authorization authorization) {
        final List<String> accountsWithAuthorisation = service.getAccountsWithAuthorisation(authorization);
        return ResponseEntity.ok(accountsWithAuthorisation);
    }

    // Users must be able to create write or read access for payments and savings accounts
    @PostMapping(path = "/access", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeAccess(
            @RequestBody final AccountAccessRequest request) {
        return ResponseEntity.ok(service.changeAuthorisation(request));
    }
}
