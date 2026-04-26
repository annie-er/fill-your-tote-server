// AuthResponse.java
package com.fillyourtote.fillyourtoteserver.dto;

public class AuthResponseDTO {
    private String token;
//    private String username;
    private String firstName;
    private String lastName;
    private String email;

//    public AuthResponseDTO(String token, String username, String email) {
//        this.token = token;
//        this.username = username;
//        this.email = email;
//    }

    public AuthResponseDTO(String token, String firstName, String lastName, String email) {
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getToken() { return token; }
//    public String getUsername() { return username; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
}
