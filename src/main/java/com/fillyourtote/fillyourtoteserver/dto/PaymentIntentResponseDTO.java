package com.fillyourtote.fillyourtoteserver.dto;

public class PaymentIntentResponseDTO {
    private String clientSecret;

    public PaymentIntentResponseDTO(String clientSecret) { this.clientSecret = clientSecret; }

    public String getClientSecret() {return clientSecret;}
}
