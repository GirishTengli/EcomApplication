package com.app.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ProductRequest {

    private String name;
    private String description;
    private String category;
    private String price;
    private Integer stockQuantity;
    private String imageUrl;

}
