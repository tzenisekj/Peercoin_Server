package com.peercoin.core.paymentmethods;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peercoin.core.PaymentEntity;
import com.peercoin.core.currency.Currency;

import java.io.IOException;

public class PaymentMethod implements PaymentEntity {
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public static PaymentMethod parseJson(String message) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readValue(message,PaymentMethod.class);
    }

    @Override
    public String toString() {
        return "name: " + this.name;
    }
}
