package models;

import lombok.Data;

import java.util.Date;

/**
 * Created by kerem on 8/16/16.
 */
@Data
public class Purchase {
    private int id;
    private String username;
    private int productId;
    private Date date;

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", productId=" + productId +
                ", date=" + date +
                '}';
    }
}
