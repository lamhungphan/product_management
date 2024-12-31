package com.example.product_management.controller;

import com.example.product_management.model.Product;
import com.example.product_management.model.ProductDto;
import com.example.product_management.service.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @GetMapping({"", "/"})
    public String showAllProducts(Model model) {
        List<Product> productList = repository.findAll();
        model.addAttribute("products", productList);
        return "products/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "products/create";
    }

    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult bindingResult) {

        // Validate image file
        if (productDto.getImageFile() == null || productDto.getImageFile().isEmpty()) {
            bindingResult.addError(new FieldError("productDto", "imageFile", "Image file is required"));
        }

        // Return to the create view if there are validation errors
        if (bindingResult.hasErrors()) {
            return "products/create";
        }

        MultipartFile image = productDto.getImageFile();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

        try {
            // Define upload directory
            String uploadDir = "public/images";
            Path uploadPath = Paths.get(uploadDir);

            // Create directory if it doesn't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the file to the upload directory
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, uploadPath.resolve(storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            // Log error and return error message
            System.err.println("Failed to save file: " + e.getMessage());
            bindingResult.addError(new FieldError("productDto", "imageFile", "Failed to upload image"));
            return "products/create";
        }

        // Map ProductDto to Product entity
        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCreatedAt(createdAt);
        product.setImageFileName(storageFileName);

        // Save product to the database
        repository.save(product);

        // Redirect to the product listing page after successful creation
        return "redirect:/products";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id) {
        try {
            Product product = repository.findById(id).get();
            model.addAttribute("product", product);

            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setBrand(product.getBrand());
            productDto.setCategory(product.getCategory());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());
            model.addAttribute("productDto", productDto);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/products";
        }
        return "products/edit";
    }

    @PostMapping("edit")
    public String updateProduct(Model model, @RequestParam int id, @Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult) {
        try {
            Product product = repository.findById(id).get();
            model.addAttribute("product", product);

            if (bindingResult.hasErrors()) {
                return "products/edit";
            }

            if (!productDto.getImageFile().isEmpty()) {
                // delete old image
                String uploadDir = "public/images";
                Path oldImagePath = Paths.get(uploadDir + product.getImageFileName());
                try {
                    Files.delete(oldImagePath);
                } catch (IOException e) {
                    System.err.println("Failed to delete old image file: " + e.getMessage());
                }

                // save new image file
                MultipartFile image = productDto.getImageFile();
                Date updatedAt = new Date();
                String storageFileName = updatedAt.getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                }
                product.setImageFileName(storageFileName);
            }

            product.setName(productDto.getName());
            product.setBrand(productDto.getBrand());
            product.setCategory(productDto.getCategory());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());

            repository.save(product);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/products/";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id) {
        try {
            Product product = repository.findById(id).get();
            Path imagePath = Paths.get("public/images/" + product.getImageFileName());
            try {
                Files.delete(imagePath);
            } catch (IOException e) {
                System.err.println("Failed to delete image file: " + e.getMessage());
            }
            repository.delete(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/products/";
    }
}
