package com.example.crypto;

public class UserDetails {

	String username;
	String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserDetails [username=" + username + ", email=" + email + "]";
	}
	

}
