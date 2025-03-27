package co.edu.icesi.introspringboot2.security;

import co.edu.icesi.introspringboot2.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

public class SecurityUser implements UserDetails {

    private User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPass();
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
