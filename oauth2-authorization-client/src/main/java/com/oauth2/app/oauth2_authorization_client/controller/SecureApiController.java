package com.oauth2.app.oauth2_authorization_client.controller;

import com.oauth2.app.oauth2_authorization_client.entity.Product;
import com.oauth2.app.oauth2_authorization_client.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/secure")
@RequiredArgsConstructor
public class SecureApiController {

    private final ProductService productService;

    @GetMapping(value = "/ping")
    public String ping(){
        return "PONG !!";
    }

    @PostMapping(value = "/save-product")
    public Product save(@RequestBody Product product){
        return productService.save(product);
    }

    @GetMapping(value = "/fetch-product")
    public List<Product> fetch(){
        return productService.fetch();
    }
}
