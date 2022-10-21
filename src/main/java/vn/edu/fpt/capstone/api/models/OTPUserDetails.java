package vn.edu.fpt.capstone.api.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.edu.fpt.capstone.api.constants.AppRole;
import vn.edu.fpt.capstone.api.entities.CompanyContact;

import java.util.Collection;
import java.util.stream.Stream;

public record OTPUserDetails(CompanyContact contact) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(AppRole.LINE_MANAGER).map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6";
    }

    @Override
    public String getUsername() {
        return contact.getEmail();
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
