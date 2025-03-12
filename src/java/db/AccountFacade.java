/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Mr. Tuan
 */
public class AccountFacade {

    public Account select(String email, String password) throws SQLException {
        Account account = null;
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("select * from Customer where email = ? and password = ?");
        stm.setString(1, email);
        stm.setString(2, password);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            account = new Account();
            account.setId(rs.getInt("id"));
            account.setName(rs.getString("name"));
            account.setPhone(rs.getString("phone"));
            account.setEmail(rs.getString("email"));
            account.setPassword(rs.getString("password"));
            account.setRoleId(rs.getString("roleId"));
        }
        con.close();
        return account;
    }

    public void create(Account account) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("insert Customer ([name], [phone], [email], [password]) values (?, ?, ?, ?)");
        stm.setString(1, account.getName());
        stm.setString(2, account.getPhone());
        stm.setString(3, account.getEmail());
        stm.setString(4, account.getPassword());
        stm.executeUpdate();
        con.close();
    }
}
