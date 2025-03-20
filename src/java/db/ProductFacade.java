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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mr. Tuan
 */
public class ProductFacade {

    public List<Product> select() throws SQLException {
        Connection con = DBContext.getConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select * from Product");
        List<Product> list = new ArrayList<>();
        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            product.setDiscount(rs.getDouble("discount"));
            product.setCategoryId(rs.getInt("categoryId"));
            list.add(product);
        }
        con.close();
        return list;
    }

    public Product select(int id) throws SQLException {
        Product product = null;
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("select * from Product where id = ?");
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            product = new Product();
            product.setId(rs.getInt("id"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            product.setDiscount(rs.getDouble("discount"));
            product.setCategoryId(rs.getInt("categoryId"));
        }
        con.close();
        return product;
    }

    public List<Product> select(int pageSize, int page) throws SQLException {
        Connection con = DBContext.getConnection();
        PreparedStatement stm = con.prepareStatement("select * from Product order by id offset ? rows fetch next ? rows only");
        stm.setInt(1, (page - 1) * pageSize);
        stm.setInt(2, pageSize);
        ResultSet rs = stm.executeQuery();
        List<Product> list = new ArrayList<>();
        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            product.setDiscount(rs.getDouble("discount"));
            product.setCategoryId(rs.getInt("categoryId"));
            list.add(product);
        }
        con.close();
        return list;
    }

    public int count() throws SQLException {
        int rowCount = 0;
        Connection con = DBContext.getConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select count(*) row_count from Product");
        while (rs.next()) {
            rowCount = rs.getInt("row_count");
        }
        con.close();
        return rowCount;
    }

    public List<Product> search(String keyword) throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE description LIKE ?";

        try (Connection con = DBContext.getConnection();
                PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, "%" + keyword + "%"); // Use wildcard for partial matches
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setDiscount(rs.getDouble("discount"));
                product.setCategoryId(rs.getInt("categoryId"));
                list.add(product);
            }
        }
        return list;
    }

    public List<Product> selectSorted(String sortBy) throws SQLException {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM Product";

        // Validate sortBy to prevent SQL injection
        if (sortBy != null && !sortBy.isEmpty()) {
            // Only allow specific fields to be sorted
            if (sortBy.equals("price") || sortBy.equals("description")) {
                query += " ORDER BY " + sortBy; // Append sorting criteria
            }
        }

        try (Connection con = DBContext.getConnection();
                Statement stm = con.createStatement();
                ResultSet rs = stm.executeQuery(query)) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setDiscount(rs.getDouble("discount"));
                product.setCategoryId(rs.getInt("categoryId"));
                list.add(product);
            }
        }
        return list;
    }

    public List<Product> search(String keyword, String sortBy) throws SQLException {
        List<Product> list = new ArrayList<>();
        Connection con = DBContext.getConnection();

        // SQL query to search for products
        String sql = "SELECT * FROM Product WHERE description LIKE ?";

        // Append sorting to the query if sortBy is provided
        if (sortBy != null && !sortBy.isEmpty()) {
            sql += " ORDER BY " + sortBy; // Add sorting criteria
        }

        PreparedStatement stm = con.prepareStatement(sql);
        stm.setString(1, "%" + keyword + "%"); // Search pattern

        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            product.setDiscount(rs.getDouble("discount"));
            product.setCategoryId(rs.getInt("categoryId"));
            list.add(product);
        }
        con.close();
        return list;
    }
}
