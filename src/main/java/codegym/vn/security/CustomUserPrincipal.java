package codegym.vn.security;

import codegym.vn.entity.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Bản UserDetails dùng class riêng của dự án: bọc trực tiếp {@link AppUser}.
 *
 * Phương án thay thế cho việc dùng
 * {@link org.springframework.security.core.userdetails.User} có sẵn của
 * Spring Security (xem {@link SpringUserDetailsService}).
 */
public class CustomUserPrincipal implements UserDetails {

    private final AppUser appUser;

    public CustomUserPrincipal(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String rolesCsv = appUser.getRoles();
        if (rolesCsv != null && !rolesCsv.trim().isEmpty()) {
            for (String role : rolesCsv.split(",")) {
                String trimmed = role.trim();
                if (!trimmed.isEmpty()) {
                    authorities.add(new SimpleGrantedAuthority(trimmed));
                }
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getUsername();
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
        return appUser.isEnabled();
    }
}
