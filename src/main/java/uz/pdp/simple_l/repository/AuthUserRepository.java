package uz.pdp.simple_l.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.simple_l.entity.AuthUser;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);

    Optional<AuthUser> findByEmail(String email);

    Optional<AuthUser> findById(Long id);

    @Query("SELECT a FROM AuthUser a WHERE a.isActive = true")
    Page<AuthUser> findAllActiveUsers(Pageable pageable);

    @Query("SELECT a from AuthUser a where a.isActive = false")
    Page<AuthUser> findAllInActiveUsers(Pageable pageable);

    @Query("SELECT count(u) FROM AuthUser u WHERE u.isActive = true")
    long countActiveUsers();
}