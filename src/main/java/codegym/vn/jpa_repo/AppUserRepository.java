package codegym.vn.jpa_repo;

import codegym.vn.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository cho {@link AppUser}.
 * Nằm trong package codegym.vn.jpa_repo (nơi @EnableJpaRepositories quét).
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);
}
