/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import cart.Cart;
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
        }
    }
    
    protected void index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(LAYOUT).forward(request, response);
    }
    
    protected void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        try {
//            int id = Integer.parseInt(request.getParameter("id"));
//            ProductFacade pf = new ProductFacade();
//            Product product = pf.select(id);
//            HttpSession session = request.getSession();
//            Cart cart = (Cart)session.getAttribute("cart");
//            cart.add(product, 1);
//            request.getRequestDispatcher("/").forward(request, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
