package com.example.authService.dto;

public class UserDTO {
	public Long userId;
    public String fullName;
    public String email;
    public String phone;
    public String role;

    public String passportNumber;
    public String nationality;
    
	public void setUserId(Long userId2) {
		this.userId = userId2;
		
	}

	public void setFullName(String fullName2) {
		this.fullName = fullName2;	
	}

	public void setEmail(String email2) {
		this.email = email2;	
	}

	public void setPhone(String phone2) {
		this.phone = phone2;		
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setPassportNumber(String passportNumber2) {
		this.passportNumber = passportNumber2;
	}

	public void setNationality(String nationality2) {
		this.nationality = nationality2;
	}
	
	
	
}
