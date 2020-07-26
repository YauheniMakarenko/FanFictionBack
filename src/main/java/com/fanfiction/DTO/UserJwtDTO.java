package com.fanfiction.DTO;

import java.util.List;
import java.util.Objects;

public class UserJwtDTO {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private List<String> roles;

	public UserJwtDTO(String accessToken, Long id, String username, String email, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

	public UserJwtDTO(String token) {
		this.token = token;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserJwtDTO that = (UserJwtDTO) o;
		return Objects.equals(token, that.token) &&
				Objects.equals(type, that.type) &&
				Objects.equals(id, that.id) &&
				Objects.equals(username, that.username) &&
				Objects.equals(email, that.email) &&
				Objects.equals(roles, that.roles);
	}

	@Override
	public int hashCode() {
		return Objects.hash(token, type, id, username, email, roles);
	}
}
