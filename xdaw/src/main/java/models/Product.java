package models;

import lombok.Data;

import java.util.*;

/**
 * Created by kerem on 8/16/16.
 */
@Data
public class Product {
    private int id;
    private String face;
    private int size;
    private int price;

    private HashSet<String> recent;
    private transient int recentSize;
    private transient List<Product> products = new ArrayList<>();

    /*
     * Utility method for keeping a record of all products
     */
    public void addProduct(Product newProd){
        products.add(newProd);
    }

    /*
     * Extracts all products sorted by their id (size of their 'recent' lists)
     */
    public List getAllProducts(){

        products.sort(Comparator.comparing(Product::getRecentSize).reversed());

        return products;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", face='" + face + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", recent=" + recent +
                '}';
    }
}

