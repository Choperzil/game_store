/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Mr. Tuan
 */
public class OrderFacade {
    public void insert(int customerId ,Order order) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("INSERT OrderDetail(customerID, productId, quantity, oldPrice, newPrice, total) VALUES (?, ?, ?, ?, ?, ?)");
        stm.setInt(1, customerId);
        stm.setInt(2, order.getProductId());
        stm.setInt(3, order.getQuantity());
        stm.setDouble(4, order.getOldPrice());
        stm.setDouble(5, order.getNewPrice());
        stm.setDouble(6, order.getTotal());
        stm.executeUpdate();
        con.close();
    }
}
