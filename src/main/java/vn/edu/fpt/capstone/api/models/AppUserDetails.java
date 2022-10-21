package vn.edu.fpt.capstone.api.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import vn.edu.fpt.capstone.api.constants.StudentStatus;
import vn.edu.fpt.capstone.api.entities.Role;
import vn.edu.fpt.capstone.api.entities.User;

import java.util.Collection;
import java.util.List;

public record AppUserDetails(User user, List<Role> roles) implements UserDetails {

    private static final List<StudentStatus> ENABLED_STATUSES = List.of(StudentStatus.ENABLED, StudentStatus.NOT_UPDATE_INFO);

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollectionUtils.isEmpty(roles)) return AuthorityUtils.NO_AUTHORITIES;
        return roles.stream().map(Role::getCode).map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6";
    }

    @Override
    public String getUsername() {
        return user.getEduEmail();
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
        return ENABLED_STATUSES.contains(user.getStatus());
    }

}
