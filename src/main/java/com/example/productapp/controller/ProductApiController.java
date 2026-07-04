package com.example.productapp.controller;

import com.example.productapp.model.Product;
import com.example.productapp.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST API cho sản phẩm - viết lại các chức năng của ProductController
 * nhưng trả về DỮ LIỆU DẠNG CHỮ (JSON), không trả về trang HTML.
 * Mở trên trình duyệt sẽ chỉ thấy chữ (JSON).
 *
 * Base URL: /api/products
 */
@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/products            -> danh sách sản phẩm (JSON)
    // GET /api/products?keyword=lap -> tìm theo tên
    @GetMapping
    public List<Product> listProducts(@RequestParam(required = false) String keyword) {
        return productService.searchByName(keyword);
    }

    // GET /api/products/1 -> chi tiết 1 sản phẩm (JSON)
    @GetMapping("/{id}")
    public ResponseEntity<Product> productDetail(@PathVariable int id) {
        Product product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    // POST /api/products -> thêm sản phẩm mới (nhận JSON, trả JSON)
    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        productService.add(product);
        return ResponseEntity.ok(product);
    }

    // DELETE /api/products/1 -> xóa sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        Product product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productService.delete(id);
        return ResponseEntity.ok("Đã xóa sản phẩm có id = " + id);
    }

    // PUT /api/products/1/image -> thêm / cập nhật ảnh cho sản phẩm
    // Body JSON: { "imageUrl": "https://example.com/laptop.jpg" }
    @PutMapping("/{id}/image")
    public ResponseEntity<Product> updateImage(
            @PathVariable int id,
            @RequestBody Map<String, String> body
    ) {
        Product updated = productService.updateImage(id, body.get("imageUrl"));
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }
}
