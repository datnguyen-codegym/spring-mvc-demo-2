package codegym.vn.security;

import codegym.vn.entity.AppUser;
import codegym.vn.jpa_repo.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Bản thay thế của UserDetailsService: trả về {@link CustomUserPrincipal}
 * bọc entity {@link AppUser} của dự án (thay cho class User có sẵn của Spring).
 *
 * Không đánh dấu @Primary để tránh xung đột với
 * {@link SpringUserDetailsService} (bản mặc định).
 *
 * Cách chuyển sang dùng bản này:
 * 1. Bỏ @Primary ở {@link SpringUserDetailsService} và thêm @Primary tại đây; hoặc
 * 2. Trong {@link SecurityConfig}, đổi tham chiếu UserDetailsService (ví dụ inject
 *    theo tên bean "customUserDetailsService") khi cấu hình DaoAuthenticationProvider.
 */
@Service("customUserDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Không tìm thấy user: " + username));
        return new CustomUserPrincipal(appUser);
    }
}
