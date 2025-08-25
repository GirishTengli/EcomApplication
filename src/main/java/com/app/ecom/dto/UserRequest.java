package com.app.ecom.dto;

import com.app.ecom.model.UserRole;
import lombok.Data;

@Data
public class UserRequest {

    private String id;
    private String username;
    private String lastname;
    private String email;
    private String phone;
    private AddressDTO address;

}
