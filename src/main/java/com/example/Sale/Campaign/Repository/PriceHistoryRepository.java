package com.example.Sale.Campaign.Repository;

import com.example.Sale.Campaign.Model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory,Integer> {
    @Query(value = "SELECT * FROM price_history WHERE product_id = :productId AND local_date = :date ORDER BY id DESC LIMIT 1", nativeQuery = true)
    PriceHistory findTopByProductIdAndDate(@Param("productId") int productId, @Param("date") LocalDate date);
}
