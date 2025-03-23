/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import cart.Cart;
import cart.Item;
import db.Account;
import db.Order;
import db.OrderFacade;
import db.Product;
import db.ProductFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

/**
 *
 * @author Mr. Tuan
 */
@WebServlet(name = "CartController", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String LAYOUT = "/WEB-INF/layouts/main.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = (String)request.getAttribute("action");
        switch (action) {
            case "index":
                index(request, response);
                break;
            case "add": 
                add(request, response);
                break;
            case "remove":
                remove(request, response);
                break;
            case "empty":
                empty(request, response);
                break;    
            case "update":
                update(request, response);
                break;
            case "checkout":
                checkout(request, response);
                break;
        }
    }
    
    protected void index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(LAYOUT).forward(request, response);
    }
    
    protected void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          try {
            int id = Integer.parseInt(request.getParameter("id"));
            ProductFacade pf = new ProductFacade();
            Product product = pf.select(id);
            if (product == null) {
                throw new ServletException("Product not found: " + id);
            }
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }
            cart.add(product, 1);
            request.getRequestDispatcher("/").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid product ID");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    
    protected void remove(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        cart.remove(id);
        request.getRequestDispatcher("/cart/index.do").forward(request, response);
    }
    
    protected void empty(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        cart.empty();
        request.getRequestDispatcher("/cart/index.do").forward(request, response);
    }
    
    protected void update(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        cart.update(id, quantity);
        request.getRequestDispatcher("/cart/index.do").forward(request, response);
    }
    
    protected void checkout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        Account account = (Account)session.getAttribute("account");

        if (account == null) {
            request.setAttribute("message", "Please log in to checkout!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        if (cart == null || cart.getItems().isEmpty()) {
            request.setAttribute("message", "Your cart is empty!");
            request.getRequestDispatcher("/cart/index.do").forward(request, response);
            return;
        }

        try {
            OrderFacade of = new OrderFacade();
            for (Item item : cart.getItems()) {
                Order order = new Order();
                order.setCustomerID(account.getId());
                order.setProductId(item.getProduct().getId());
                order.setQuantity(item.getQuantity());
                order.setOldPrice(item.getProduct().getPrice());
                order.setNewPrice(item.getProduct().getCost()); // Assuming getCost() returns discounted price
                order.setTotal(item.getCost());
                of.insert(account.getId(), order);
            }
            cart.empty(); // Clear the cart after successful checkout
            request.setAttribute("message", "Checkout successful! Thank you for your purchase.");
            request.getRequestDispatcher("/cart/index.do").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error during checkout", e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
