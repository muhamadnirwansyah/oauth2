package com.oauth2.app.oauth2_authorization_server.repository;

import com.oauth2.app.oauth2_authorization_server.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {

    Optional<Roles> findByName(@Param("name")String name);
}
