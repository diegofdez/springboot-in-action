package com.diegofdez.springbootinaction.readingList;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Reader implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	private String username;
	private String fullname;
	private String password;
	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	// Security methods
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		/*In security check we'll look for READER role. But in the authorities list we must add the ROLE_ prefix.
		 * https://stackoverflow.com/questions/33205236/spring-security-added-prefix-role-to-all-roles-name */
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_READER"));
	}

	
	// Keep account enabled "forever"
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
