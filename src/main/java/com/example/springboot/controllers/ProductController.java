package com.example.springboot.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dtos.ProductRecordDTO;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class ProductController {
    // Autowired is used for dependency injection
    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDTO product) {
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(product, productModel);        
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }
    
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOne(@PathVariable(value="id") UUID id) {
        Optional<ProductModel> productModel =  productRepository.findById(id);
        if(productModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findById(id));
    }
    
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id, @RequestBody @Valid ProductRecordDTO product) {
        Optional<ProductModel> productModel = productRepository.findById(id);
        if(productModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        ProductModel newProductModel = productModel.get();
        BeanUtils.copyProperties(product, newProductModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(newProductModel));
    }
    

}
