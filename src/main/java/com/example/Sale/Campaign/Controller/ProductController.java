package com.example.Sale.Campaign.Controller;

import com.example.Sale.Campaign.Model.Product;
import com.example.Sale.Campaign.Model.ResponseDTO;
import com.example.Sale.Campaign.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("save-product")
    public ResponseDTO<Product> saveProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @PostMapping("save-product-list")
    public ResponseDTO<List<Product>> saveProductList(@RequestBody List<Product> product){
        return productService.saveProductList(product);
    }

    @GetMapping("getAllPaginated")
    public ResponseDTO<Page<Product>> getAllProductsPaginated(@RequestParam(defaultValue = "1") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        return productService.getAllPaginated(page, size);
    }

}
