package com.CrudJava.rest;

import com.CrudJava.POJO.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/product")
@CrossOrigin(origins = "*")
public interface ProductRest {

    @PostMapping(path = "/addNewProduct")
    public ResponseEntity<String> addNewProduct(@RequestBody(required = true)Map<String, String> requestMap);

    @GetMapping(path = "/getAllProduct")
    public ResponseEntity<List<Product>> getAllProduct();

    @PostMapping(path = "/updateProduct")
    public ResponseEntity<String> updateProduct(@RequestBody(required = true)Map<String, String> requestMap);

    @DeleteMapping(path = "/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId);





}
