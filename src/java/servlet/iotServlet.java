/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.DataManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Beacon;

/**
 *
 * @author derrickgoh
 */
public class iotServlet extends HttpServlet {
    private DataManager dm;
    
    public iotServlet() throws IOException{
        super();
        dm = new DataManager();
    }
    
    
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
        response.setContentType("application/json;charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String timeStart = request.getHeader("timeStart");
            String timeEnd = request.getHeader("timeEnd");
            timeStart = timeStart.replace('T', ' ');
            timeEnd = timeEnd.replace('T', ' ');
            dm.retrieveIntervalData(timeStart, timeEnd);
        }
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
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String jsonString = new String();
            for (String line; (line = request.getReader().readLine()) != null; jsonString += line);
            Beacon inputBeacon = gson.fromJson(jsonString, Beacon.class);
            boolean updated = dm.updateData(inputBeacon);
            out.println("status:" + updated);
        }
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
