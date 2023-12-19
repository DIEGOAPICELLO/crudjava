package com.CrudJava.serviceImpl;

import com.CrudJava.POJO.Product;
import com.CrudJava.dao.ProductDao;
import com.CrudJava.serivice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;
    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            if(ValidateAddNewProduct(requestMap, false)){
                productDao.save(getProductFromMap(requestMap, false));
                return new ResponseEntity<String>("{\"message\":\"" + "Product Add Succesfully." + "\" }", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("{\"message\":\"" + "Invalid Data" + "\" }", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":\"" + "Something Went Wrong at Product Service Impl." + "\" }", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean ValidateAddNewProduct(Map<String, String> requestMap, Boolean validDataId) {

        if (requestMap.containsKey("name") && requestMap.containsKey("price") && requestMap.containsKey("description")){
            if(validDataId && requestMap.containsKey("productId")){
                return true;
            } else if (!validDataId){

            }
            return true;
        }
        return false;
    }

    private Product getProductFromMap(Map<String, String> requestMap, Boolean isAdd) {

        Product product = new Product();
        if(isAdd) {
            product.setId(Integer.parseInt(requestMap.get("productId")));
        }

        product.setName(requestMap.get("name"));
        product.setPrice((double) Float.parseFloat(requestMap.get("price")));
        product.setDescription(requestMap.get("description"));
        return product;
    }

    @Override
    public ResponseEntity<List<Product>> getAllProduct() {
        try {
            return new ResponseEntity<List<Product>>(productDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return new ResponseEntity<List<Product>>(new ArrayList<Product>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if(ValidateAddNewProduct(requestMap, true)){
                Optional optional = productDao.findById(Integer.parseInt(requestMap.get("productId")));
                if(!optional.isEmpty()){
                    productDao.save(getProductFromMap(requestMap, true));
                    return new ResponseEntity<String>("{\"message\":\"" + "Product Updated Sucessfuly" + "\" }", HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("{\"message\":\"" + "Product is not exist" + "\" }", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<String>("{\"message\":\"" + "Invalid Data" + "\" }", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":\"" + "Something Went Wrong" + "\" }", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(int productId) {
        try {
            if(productId >= 0){
                productDao.deleteById(productId);
                return new ResponseEntity<String>("{\"message\":\"" + "Product Deleted Successfuly" + "\" }", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("{\"message\":\"" + "Invalid Product Id" + "\" }", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":\"" + "Something Went Wrong" + "\" }", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
