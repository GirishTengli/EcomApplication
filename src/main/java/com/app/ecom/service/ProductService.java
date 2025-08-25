package com.app.ecom.service;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.model.Product;
import com.app.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ResourcePatternResolver resourcePatternResolver;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product saveProduct=  productRepository.save(product);
        return mapToProductResponse(saveProduct);
    }

    public List<ProductResponse> getAllProducts(){
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToProductResponse).collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id){
       return productRepository.findById(id)
                .map(product ->{
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                } ).orElse(false);
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse response = new ProductResponse();
        response.setId(String.valueOf(savedProduct.getId()));
        response.setName(savedProduct.getName());
        response.setActive(savedProduct.isActive());
        response.setCategory(savedProduct.getCategory());
        response.setDescription(savedProduct.getDescription());
        response.setPrice(savedProduct.getPrice());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setStockQuantity(savedProduct.getStockQuantity());
        return  response;
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
    }

    public Object getProducts(String keyword) {
        return productRepository.searchProducts(keyword)
                .stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }
}
