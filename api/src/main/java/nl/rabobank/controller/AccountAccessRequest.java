package nl.rabobank.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.rabobank.authorizations.Authorization;

public class AccountAccessRequest {
    @JsonProperty( value = "nr", required = true)
    String accountNumber;
    @JsonProperty(value = "auth", required = true)
    Authorization authorization;
    @JsonProperty( value = "granteeName" , required = true)
    String granteeName;

    public String getAccountNumber() {
        return accountNumber;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public String getGranteeName() {
        return granteeName;
    }
}
