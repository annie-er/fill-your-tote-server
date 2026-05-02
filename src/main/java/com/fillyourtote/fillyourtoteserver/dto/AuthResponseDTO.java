// AuthResponse.java
package com.fillyourtote.fillyourtoteserver.dto;

public class AuthResponseDTO {
    private String token;
    private String firstName;
    private String lastName;
    private String email;

    public AuthResponseDTO(String token, String firstName, String lastName, String email) {
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getToken() { return token; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
}
