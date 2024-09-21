package com.example.Sale.Campaign.Scheduler;


import com.example.Sale.Campaign.Model.Campaign;
import com.example.Sale.Campaign.Model.CampaignDiscount;
import com.example.Sale.Campaign.Model.PriceHistory;
import com.example.Sale.Campaign.Model.Product;
import com.example.Sale.Campaign.Repository.CampaignRepository;
import com.example.Sale.Campaign.Repository.PriceHistoryRepository;
import com.example.Sale.Campaign.Repository.ProductRepository;
import com.example.Sale.Campaign.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class PriceScheduler {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    @Autowired
    private ProductService productService;

    @Scheduled(cron = "0 0 0 * * *")
    public void adjustProductPrices() {
        LocalDate today = LocalDate.now();
        List<Campaign> activeSales = campaignRepository.findAllByStartDate(today);

        for (Campaign campaign : activeSales) {
            List<CampaignDiscount> discounts = campaign.getCampaignDiscounts();
            for (CampaignDiscount discount : discounts) {
                Product product = productRepository.findById(discount.getProductId()).orElse(null);
                if (product != null) {
                    double discountAmount = (product.getCurrentPrice() * (discount.getDiscount() / 100));
                    double newPrice = (product.getCurrentPrice() - discountAmount);

                    if (newPrice >= 0) {
                        product.setCurrentPrice(newPrice);
                        product.setDiscount(discount.getDiscount());
                        productRepository.save(product);
                        productService.saveHistory(product, newPrice, LocalDate.now(), discountAmount);
                    }
                }
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void revertPrice(){
        LocalDate today = LocalDate.now();
        List<Campaign> endedSales = campaignRepository.findAllByEndDate(today);

        for (Campaign campaign : endedSales) {
            List<CampaignDiscount> discounts = campaign.getCampaignDiscounts();
            for (CampaignDiscount discount : discounts) {
                Product product = productRepository.findById(discount.getProductId()).orElse(null);
                if (product != null) {

                    LocalDate date = campaign.getStartDate();
                    PriceHistory priceHistory = priceHistoryRepository.findTopByProductIdAndDate(product.getId(), date);
                    if (priceHistory != null) {
                        double previousPrice = priceHistory.getDiscountPrice();
                        product.setCurrentPrice(priceHistory.getPrice() + previousPrice);
                        productRepository.save(product);
                        productService.saveHistory(product, priceHistory.getPrice() + previousPrice, LocalDate.now(), priceHistory.getDiscountPrice());
                    }

                }
            }
        }
    }


}
