package nl.rabobank.mongo;

import nl.rabobank.account.Account;
import nl.rabobank.account.PaymentAccount;
import nl.rabobank.account.SavingsAccount;
import nl.rabobank.authorizations.Authorization;
import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.mongo.repositories.AccountRepository;
import nl.rabobank.mongo.repositories.PowerOfAttorneyRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AccountDAO {

    private final AccountRepository accountRepository;
    private final PowerOfAttorneyRepository powerOfAttorneyRepository;
    private final MongoTemplate mongoTemplate;

    public AccountDAO(AccountRepository accountRepository, PowerOfAttorneyRepository powerOfAttorneyRepository, MongoTemplate mongoTemplate) {
        this.accountRepository = accountRepository;
        this.powerOfAttorneyRepository = powerOfAttorneyRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void insertAccountsForTesting() {
        PaymentAccount paymentAccount1 = new PaymentAccount("1", "Alpha", 1000.00D);
        PaymentAccount paymentAccount2 = new PaymentAccount("2", "Beta", 2000.00D);
        SavingsAccount savingsAccount1 = new SavingsAccount("3", "Gamma", 3000.00D);
        SavingsAccount savingsAccount2 = new SavingsAccount("4", "Delta", 4000.00D);

        accountRepository.insert(paymentAccount1);
        accountRepository.insert(paymentAccount2);
        accountRepository.insert(savingsAccount1);
        accountRepository.insert(savingsAccount2);

        PowerOfAttorney powerOfAttorney1 = PowerOfAttorney
                .builder()
                .account(paymentAccount1)
                .authorization(Authorization.READ)
                .grantorName(paymentAccount1.getAccountHolderName())
                .granteeName("Admin")
                .build();

        PowerOfAttorney powerOfAttorney2 = PowerOfAttorney
                .builder()
                .account(paymentAccount2)
                .authorization(Authorization.READ)
                .grantorName(paymentAccount2.getAccountHolderName())
                .granteeName("Admin")
                .build();


        powerOfAttorneyRepository.insert(powerOfAttorney1);
        powerOfAttorneyRepository.insert(powerOfAttorney2);

    }

    public String changeAccess(String accountNumber, Authorization authorization, String granteeName) {

        final Optional<Account> account = accountRepository.findById(accountNumber);

        if (account.isPresent()) {
            final PowerOfAttorney powerOfAttorney = PowerOfAttorney.builder()
                    .account(account.get())
                    .authorization(authorization)
                    .grantorName(account.get().getAccountHolderName())
                    .granteeName(granteeName)
                    .build();

            powerOfAttorneyRepository.insert(powerOfAttorney);

            return "Changed access to account " + account.get().getAccountNumber();
        } else {
            return "Account not found";
        }
    }

    public List<String> retrieveAccountsWithAuthorisation(Authorization authorization) {
        Query query = new Query();
        query.addCriteria(Criteria.where("authorization").is(authorization.toString()));
        List<PowerOfAttorney> poa = mongoTemplate.find(query, PowerOfAttorney.class);
        List<String> accountNumbers = new ArrayList<>();
        poa.forEach(p -> accountNumbers.add(p.getAccount().getAccountNumber()));
        return accountNumbers;
    }
}
