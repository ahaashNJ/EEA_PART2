package com.example.eea_part2.Model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private String jwttoken;
    private String userType;
    private String email;

    public JwtResponse(String jwttoken, String userType, String email) {
        this.jwttoken = jwttoken;
        this.userType = userType;
        this.email = email;
    }

    public JwtResponse() {
    }

    public String getJwttoken() {
        return jwttoken;
    }

    public String getUserType() {
        return userType;
    }

    public String getEmail() {
        return email;
    }
}
