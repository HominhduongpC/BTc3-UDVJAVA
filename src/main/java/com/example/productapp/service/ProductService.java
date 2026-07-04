package com.example.productapp.service;

import com.example.productapp.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();

    public ProductService() {
        products.add(new Product(1, "Laptop", 15000000));
        products.add(new Product(2, "Mouse", 250000));
    }

    // Lấy tất cả sản phẩm
    public List<Product> findAll() {
        return products;
    }

    // Tìm sản phẩm theo ID
    public Product findById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Thêm sản phẩm mới (tự sinh ID = ID lớn nhất + 1)
    public void add(Product product) {
        int nextId = products.stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0) + 1;
        product.setId(nextId);
        products.add(product);
    }

    // Xóa sản phẩm theo ID
    public void delete(int id) {
        products.removeIf(p -> p.getId() == id);
    }

    // Tìm kiếm sản phẩm theo tên (không phân biệt hoa thường)
    public List<Product> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return products;
        }
        String kw = keyword.toLowerCase();
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getName() != null && p.getName().toLowerCase().contains(kw)) {
                result.add(p);
            }
        }
        return result;
    }
}
