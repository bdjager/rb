package nl.rabobank.account;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Account")
@Value
public class PaymentAccount implements Account
{
    @Id
    String accountNumber;
    String accountHolderName;
    Double balance;
}
