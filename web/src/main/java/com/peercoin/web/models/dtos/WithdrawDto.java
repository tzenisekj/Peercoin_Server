package com.peercoin.web.models.dtos;

public class WithdrawDto {
    private double amount;
    private String address;
    public WithdrawDto() { }

    public WithdrawDto(String address, double amount) {
        this.address = address;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
