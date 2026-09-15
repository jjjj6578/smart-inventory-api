package com.example.inventory.service;

import com.example.inventory.exception.ResourceNotFoundException;
import com.example.inventory.model.InventoryItem;
import com.example.inventory.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public List<InventoryItem> getAllItems() {
        log.info("Fetching all inventory items from database.");
        return repository.findAll();
    }

    public InventoryItem getItemById(Long id) {
        log.info("Fetching inventory item with ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
    }

    public InventoryItem createItem(InventoryItem item) {
        log.info("Saving new inventory item: {}", item.getName());
        return repository.save(item);
    }

    public InventoryItem updateItem(Long id, InventoryItem updatedItem) {
        log.info("Updating inventory item with ID: {}", id);
        InventoryItem existingItem = getItemById(id);
        existingItem.setName(updatedItem.getName());
        existingItem.setCategory(updatedItem.getCategory());
        existingItem.setPrice(updatedItem.getPrice());
        existingItem.setStockQuantity(updatedItem.getStockQuantity());
        return repository.save(existingItem);
    }

    public void deleteItem(Long id) {
        log.info("Deleting inventory item with ID: {}", id);
        InventoryItem item = getItemById(id);
        repository.delete(item);
    }
}