package ru.adaliza.chatbot.service;

import ru.adaliza.chatbot.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts(Long userId);

    void removeAllProducts(Long userId);

    void addProduct(Long chatId, String productName);
}
