package models;

import lombok.Data;

import java.util.List;

/**
 * Created by kerem on 8/16/16.
 */
@Data
public class Result {
    private int id;
    private String face;
    private int size;
    private int price;
    private List<String> recent;

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", face='" + face + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", recent=" + recent +
                '}';
    }
}
