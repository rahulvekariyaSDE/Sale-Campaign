package com.example.Sale.Campaign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SaleCampaignApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaleCampaignApplication.class, args);
	}

}
