package com.peercoin.web.models;

import com.peercoin.core.paymentmethods.PaymentMethod;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FiatMethod extends PaymentMethod {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
