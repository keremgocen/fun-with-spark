package models;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by kerem on 8/16/16.
 */
@Data
public class PurchaseList {
    private List<Purchase> purchases;
}
