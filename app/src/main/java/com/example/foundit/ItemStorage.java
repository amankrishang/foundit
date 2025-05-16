package com.example.foundit;

import java.util.ArrayList;
import java.util.List;

// This class stores all added items temporarily
public class ItemStorage {
    private static final List<Item> itemList = new ArrayList<>();

    // Add an item to the list
    public static void addItem(Item item) {
        itemList.add(item);
    }

    // Return all items
    public static List<Item> getItems() {
        return new ArrayList<>(itemList); // Return a copy
    }
}
