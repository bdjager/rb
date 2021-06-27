package nl.rabobank.service;

import nl.rabobank.authorizations.Authorization;
import nl.rabobank.controller.AccountAccessRequest;
import nl.rabobank.mongo.AccountDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountAccessService {

    private final AccountDAO accountDAO;

    public AccountAccessService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void insertAccounts() {
        accountDAO.insertAccountsForTesting();
    }

    public String changeAuthorisation(AccountAccessRequest accountAccessRequest) {
        return accountDAO.
                changeAccess(accountAccessRequest.getAccountNumber(),
                        accountAccessRequest.getAuthorization(),
                        accountAccessRequest.getGranteeName());
    }

    public List<String> getAccountsWithAuthorisation(Authorization authorization) {
        return accountDAO.retrieveAccountsWithAuthorisation(authorization);
    }
}