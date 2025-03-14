/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import db.Product;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PHT
 */
public class Cart {

    private Map<Integer, Item> map = null;

    public Cart() {
        this.map = new HashMap<>();
    }

    //Thêm item vào cart
    public void add(Product product, int quantity) {
        int id = product.getId();
        if (this.map.keySet().contains(id)) {
            //nếu item đã có trong cart thì chỉ tăng quantity
            Item item = this.map.get(id);
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            //nếu item chưa có trong cart thì thêm item vào cart
            Item item = new Item(product, quantity);
            this.map.put(id, item);
        }
    }

    public Collection<Item> getItems() {
        return this.map.values();
    }

    public double getTotal() {
        double total = 0;
        for (Item item : this.map.values()) {
            total += item.getCost();
        }
        return total;
    }

    public int getQuantity() {
        int quantity = 0;
        for (Item item : this.map.values()) {
            quantity += item.getQuantity();
        }
        return quantity;
    }

    public void remove(int id) {
        this.map.remove(id);
    }

    public void empty() {
        this.map.clear();
    }

    public void update(int id, int quantity) {
        Item item = this.map.get(id);
        item.setQuantity(quantity);
    }

//    public void checkout(int customerId) throws ClassNotFoundException, SQLException {
//        Date date = new Date();
//        int employeeId = 2;
//        String status = "NEW";//NEW->SHIPPING->CANCEL,CLOSE
//        OrderHeader oh = new OrderHeader(date, status, customerId, employeeId);
//
//        for (Item item : this.getItems()) {
//            OrderDetail od = new OrderDetail(item.getProduct().getId(), item.getQuantity(), item.getProduct().getPrice(), item.getProduct().getDiscount());
//            oh.add(od);
//        }
//
//        OrderHeaderFacade ohf = new OrderHeaderFacade();
//        ohf.insert(oh);
//    }
}
