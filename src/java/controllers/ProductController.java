/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.Product;
import db.ProductFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.Config;

/**
 *
 * @author Mr. Tuan
 */
@WebServlet(name = "ProductController", urlPatterns = {"/product"})
public class ProductController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = (String)request.getAttribute("action");
        switch (action) {
            case "index":
                index(request, response);
                break;
        }
    }
    
    protected void index(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        int pageSize = 6; // Number of products per page
        HttpSession session = request.getSession();
        Integer page = (Integer) session.getAttribute("page");
        if (page == null) {
            page = 1; // Default to the first page
            session.setAttribute("page", page);
        }

        String pages = request.getParameter("page");
        if (pages != null) {
            page = Integer.parseInt(pages);
            session.setAttribute("page", page);
        }

        String searchKeyword = request.getParameter("search");
        String sortBy = request.getParameter("sortBy");

        ProductFacade pf = new ProductFacade();
        List<Product> list;

        // Only search if a search keyword is provided
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            list = pf.search(searchKeyword, sortBy); // Search and sort
        } else {
            list = pf.select(pageSize, page); // Default selection without sorting
        }

        request.setAttribute("list", list);
        int rowCount = pf.count(); // Get the total number of products
        int totalPage = (int) Math.ceil(rowCount / (double) pageSize); // Calculate total pages
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("search", searchKeyword); // Retain the search keyword
        request.setAttribute("sortBy", sortBy); // Retain the sort option
        request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();
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
