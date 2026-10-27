package codegym.vn.security;

import codegym.vn.entity.AppUser;
import codegym.vn.jpa_repo.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * Tạo user seed khi khởi động ứng dụng nếu bảng 'users' chưa có user này.
 *
 * Nguyên tắc an toàn:
 * - Không hardcode password. Ưu tiên đọc từ biến môi trường AUTH_SEED_PASSWORD,
 *   sau đó mới đến property auth.seed.password.
 * - Nếu password rỗng thì bỏ qua seed (log cảnh báo), không tạo user không mật khẩu.
 * - Password lưu dưới dạng BCrypt hash.
 * - Không log giá trị password.
 */
@Component
@RequiredArgsConstructor
public class AppUserSeeder {

    private static final Logger log = LoggerFactory.getLogger(AppUserSeeder.class);

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${auth.seed.username:admin}")
    private String seedUsername;

    // Ưu tiên biến môi trường AUTH_SEED_PASSWORD, sau đó property auth.seed.password.
    @Value("${AUTH_SEED_PASSWORD:${auth.seed.password:}}")
    private String seedPassword;

    @Value("${auth.seed.roles:ROLE_USER,ROLE_ADMIN}")
    private String seedRoles;

    @PostConstruct
    @Transactional
    public void seed() {
        if (seedPassword == null || seedPassword.trim().isEmpty()) {
            log.warn("Bỏ qua seed user '{}': auth.seed.password (hoặc AUTH_SEED_PASSWORD) đang trống. "
                    + "Đặt biến môi trường AUTH_SEED_PASSWORD để tạo user.", seedUsername);
            return;
        }

        if (appUserRepository.findByUsername(seedUsername).isPresent()) {
            log.info("User seed '{}' đã tồn tại, bỏ qua tạo mới.", seedUsername);
            return;
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(seedUsername);
        appUser.setPassword(passwordEncoder.encode(seedPassword));
        appUser.setEnabled(true);
        appUser.setRoles(seedRoles);
        appUserRepository.save(appUser);

        log.info("Đã tạo user seed '{}' với roles '{}'.", seedUsername, seedRoles);
    }
}
