package com.example.Sale.Campaign.Service;

import com.example.Sale.Campaign.Model.PriceHistory;
import com.example.Sale.Campaign.Model.Product;
import com.example.Sale.Campaign.Model.ResponseDTO;
import com.example.Sale.Campaign.Repository.PriceHistoryRepository;
import com.example.Sale.Campaign.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    public ResponseDTO<Product> saveProduct(Product product){
        try{
            Product savedProduct = productRepository.save(product);
            double discountAmount =  (product.getCurrentPrice() * (product.getDiscount() / 100));
            saveHistory(product, product.getCurrentPrice(), LocalDate.now(), discountAmount);
            return new ResponseDTO<>(savedProduct, HttpStatus.OK, "Product saved successfully");
        } catch (Exception e){
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "failed to save product" + e.getMessage());
        }
    }

    public ResponseDTO<List<Product>> saveProductList(List<Product> productList){
        try{
            List<Product> savedProduct = productRepository.saveAll(productList);
            for (Product products : savedProduct){
                double discountAmount =  (products.getCurrentPrice() * (products.getDiscount() / 100));
                saveHistory(products, products.getCurrentPrice(), LocalDate.now(), discountAmount);
            }
            return new ResponseDTO<>(savedProduct, HttpStatus.OK, "Product saved successfully");
        } catch (Exception e){
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "failed to save product" + e.getMessage());
        }
    }


    public void saveHistory(Product product, double price, LocalDate date, double discountPrice){
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setProduct(product);
        priceHistory.setPrice(price);
        priceHistory.setLocalDate(date);
        priceHistory.setDiscountPrice(discountPrice);
        priceHistoryRepository.save(priceHistory);
    }

    public ResponseDTO<Page<Product>> getAllPaginated(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Product> housesPage = productRepository.findAll(pageable);
            return new ResponseDTO<>(housesPage, HttpStatus.OK, "get houses");
        } catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "failed to get houses " + e.getMessage());
        }
    }
}
