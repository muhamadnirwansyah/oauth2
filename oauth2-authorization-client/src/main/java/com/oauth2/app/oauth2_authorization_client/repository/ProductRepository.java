package com.oauth2.app.oauth2_authorization_client.repository;

import com.oauth2.app.oauth2_authorization_client.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
