package com.peercoin.core.currency;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Currency {
    protected String name;
    protected String ticker;
    protected String className;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return this.ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;

    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public static Currency parseJson(String message) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readValue(message,Currency.class);
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\nTicker: " + this.ticker;
    }
}
