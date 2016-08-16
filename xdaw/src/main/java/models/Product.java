package models;

import lombok.Data;

/**
 * Created by kerem on 8/16/16.
 */
@Data
public class Product {
    private int id;
    private String face;
    private int size;
    private int price;

    @Override
    public String toString() {
        return "models.Product{" +
                "id=" + id +
                ", face='" + face + '\'' +
                ", size=" + size +
                ", price=" + price +
                '}';
    }
}

