package codegym.vn.security;

import codegym.vn.entity.AppUser;
import codegym.vn.jpa_repo.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("springUserDetailsService")
@Primary
@RequiredArgsConstructor
public class SpringUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Không tìm thấy user: " + username));

        List<GrantedAuthority> authorities = buildAuthorities(appUser.getRoles());

        return new User(
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.isEnabled(),
                true,   // accountNonExpired
                true,   // credentialsNonExpired
                true,   // accountNonLocked
                authorities);
    }

    /**
     * Chuyển chuỗi CSV role (ví dụ "ROLE_USER,ROLE_ADMIN") thành danh sách authority.
     * Trả về danh sách rỗng nếu chuỗi rỗng.
     */
    private List<GrantedAuthority> buildAuthorities(String rolesCsv) {
        if (rolesCsv == null || rolesCsv.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList(rolesCsv.trim());
    }
}
