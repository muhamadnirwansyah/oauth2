package com.oauth2.app.oauth2_authorization_server.repository;

import com.oauth2.app.oauth2_authorization_server.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {
}
