package com.sathya.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
	
	 private String email;
	 private String password;


	    // response fields
	    private String token;
	    private String name;

}
