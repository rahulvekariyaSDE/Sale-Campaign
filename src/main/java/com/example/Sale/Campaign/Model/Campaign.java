package com.example.Sale.Campaign.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name="campaigns")
public class Campaign {

    @Id
    private  int id;

    @Column
    private String campaignName;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate startDate;


    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate endDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "campaign")
    @JsonManagedReference
    private List<CampaignDiscount> campaignDiscounts;

    public Campaign(){
        this.id = generateUniqueId();
    }

    private int generateUniqueId() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String accNo = uuid.substring(0,6);
        return (int) (Integer.parseInt(accNo,16) % 1000000L);
    }


}
