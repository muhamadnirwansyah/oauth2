package com.oauth2.app.oauth2_authorization_client.service;

import com.oauth2.app.oauth2_authorization_client.entity.Product;
import com.oauth2.app.oauth2_authorization_client.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(Product product){
        return productRepository.save(product);
    }

    public List<Product> fetch(){
        return productRepository.findAll();
    }
}
