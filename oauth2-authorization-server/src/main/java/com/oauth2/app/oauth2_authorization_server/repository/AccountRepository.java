package com.oauth2.app.oauth2_authorization_server.repository;

import com.oauth2.app.oauth2_authorization_server.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(@Param("username")String username);

    @Query(value = "SELECT * FROM t_account WHERE roles_id=:roleId",nativeQuery = true)
    Optional<Account> getAccountByRoleId(@Param("roleId")Long roleId);
}
