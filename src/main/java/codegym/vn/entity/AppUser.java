package codegym.vn.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity ánh xạ bảng 'users' dùng cho xác thực (HTTP Basic Auth / Spring Security).
 *
 * Lưu ý:
 * - {@code password} lưu dưới dạng BCrypt hash (không lưu plaintext).
 * - {@code roles} là chuỗi CSV các role, ví dụ: "ROLE_USER,ROLE_ADMIN".
 * - Entity nằm trong package codegym.vn.entity nên được PersistenceConfig
 *   (packagesToScan = "codegym.vn.entity") tự động quét.
 * - Không nhầm lẫn với codegym.vn.model.User (DTO của form web).
 */
@Entity
@Table(name = "users")
@Data
public class AppUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    /** BCrypt hash cua mat khau. */
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    /** Chuỗi CSV các role, ví dụ "ROLE_USER,ROLE_ADMIN". */
    @Column(name = "roles")
    private String roles;
}
