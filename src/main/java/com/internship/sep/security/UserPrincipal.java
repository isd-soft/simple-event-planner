package com.internship.sep.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.internship.sep.models.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Builder
@ToString
@AllArgsConstructor
@Getter
@Setter
public class UserPrincipal implements UserDetails {
    private static final String ROLE_PREFIX = "ROLE_";

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(User user) {
        return new UserPrincipalBuilder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .authorities( generateAuthorities(user) )
                .build();
    }

    public static List<? extends GrantedAuthority> generateAuthorities(User user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole()));
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

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
