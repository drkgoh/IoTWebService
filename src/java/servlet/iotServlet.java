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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
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
    
//    public iotServlet() throws IOException{
////        try {
//            ServletContext context = this.getServletContext();
//            String fullPath = context.getRealPath("/WEB-INF");
//            dm = new DataManager(fullPath);
////        } catch (Exception ex) {
////            Logger.getLogger(iotServlet.class.getName()).log(Level.SEVERE, null, ex);
////        }
//        
//    }
    
    
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
        DataManager dm = new DataManager(getServletContext().getRealPath("/WEB-INF"));
        //System.out.println(getServletContext().getRealPath("/WEB-INF"));
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setContentType("application/json;charset=utf-8");
        //System.out.println("THIS WORKS!!!!!");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String timeStart = request.getHeader("timeStart");
            String timeEnd = request.getHeader("timeEnd");
            List list = dm.retrieveIntervalData(timeStart, timeEnd);
            Gson gson = new Gson();
            
            try {
                if(list == null || list.isEmpty()){
                   throw new NullPointerException("No results found!");
                }
//                out.println("[\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot22\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 20,\n" +
//"    \"minute\": 28\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot22\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 76,\n" +
//"    \"minute\": 29\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot23\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 51,\n" +
//"    \"minute\": 28\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot24\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 17,\n" +
//"    \"minute\": 28\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot34\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 90,\n" +
//"    \"minute\": 28\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot34\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 87,\n" +
//"    \"minute\": 29\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot34\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 14,\n" +
//"    \"minute\": 30\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot35\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 73,\n" +
//"    \"minute\": 28\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot35\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 29,\n" +
//"    \"minute\": 29\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot35\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 84,\n" +
//"    \"minute\": 30\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot36\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 11,\n" +
//"    \"minute\": 28\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot36\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 31,\n" +
//"    \"minute\": 29\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot36\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 19,\n" +
//"    \"minute\": 30\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot36\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 91,\n" +
//"    \"minute\": 31\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot36\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 71,\n" +
//"    \"minute\": 32\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot37\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 82,\n" +
//"    \"minute\": 28\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot37\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 63,\n" +
//"    \"minute\": 29\n" +
//"  },\n" +
//"  {\n" +
//"    \"beacon_id\": \"iot37\",\n" +
//"    \"hour\": 16,\n" +
//"    \"count\": 51,\n" +
//"    \"minute\": 30\n" +
//"  }\n" +
//"]");
                out.println(gson.toJson(list));
            }
            catch(Exception e){
                out.println(gson.toJson(e));
            }
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
        DataManager dm = new DataManager(getServletContext().getRealPath("/WEB-INF"));
        //System.out.println(getServletContext().getRealPath("/WEB-INF"));
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setContentType("application/json");
        //System.out.println("THIS WORKS?????!!!!!");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String jsonString = new String();
            for (String line; (line = request.getReader().readLine()) != null; jsonString += line);
            System.out.println(jsonString);
            try {
                Beacon inputBeacon = gson.fromJson(jsonString, Beacon.class);
                boolean updated = dm.updateData(inputBeacon);
                String jsonOutput = "";
                jsonOutput += "{" + "status: " + updated + "}";
                //System.out.println("Status of update: " + updated);
                out.println("{\"status\": "+ updated + ",\n\"beacon\": "+ gson.toJson(inputBeacon) + "}");
            } catch (Exception e){
                out.println(gson.toJson(e));
            }
        } catch (Exception e){
            e.printStackTrace(System.out);
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
    }

}
