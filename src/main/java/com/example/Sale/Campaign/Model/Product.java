package com.example.Sale.Campaign.Model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
public class Product {

    @Id
    private  int id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private double mrp;

    @Column
    private double currentPrice;

    @Column
    private float discount;

    @Column
    private int inventory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PriceHistory> priceHistories;


    public Product() {
        this.id = generateUniqueId();
    }

    private int generateUniqueId() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String accNo = uuid.substring(0,6);
        return (int) (Integer.parseInt(accNo,16) % 1000000L);
    }


}
