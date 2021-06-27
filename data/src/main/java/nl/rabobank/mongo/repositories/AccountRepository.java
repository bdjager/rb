package nl.rabobank.mongo.repositories;

import nl.rabobank.account.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
}
