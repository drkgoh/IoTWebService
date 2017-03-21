package dao;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Beacon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Yuanyang
 */
public class DataManager {

    private ConnectionManager cm;
    
    public DataManager() throws IOException{
        Gson gson = new Gson();
        String contents = null;
        try {
            contents = new String(Files.readAllBytes(Paths.get("./build/web/WEB-INF/classes/config/config.json")));
        } catch (Exception e){
            contents = new String(Files.readAllBytes(Paths.get("/classes/config/config.json")));
        }
        cm = gson.fromJson(contents, ConnectionManager.class);
    }
    
    //DateTime format is 2017-03-18 13:27:53
    public ArrayList<Beacon> retrieveIntervalData(String startTime, String endTime) throws IOException {
        ArrayList<Beacon> list = new ArrayList<>();
        Connection conn = cm.getConnection();
        try {
            //find a way to retrieve the count(*) from the database using java. saves a lot of work
            String query = "SELECT beacon_id, timestamp, count(*) as count from beacon_data where timestamp > '" + startTime + "' and timestamp < '" + endTime + "' group by timestamp, beacon_id;";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet set = stmt.executeQuery();

            while (set.next()) {
                String beaconID = set.getString(1);
                String uuid = set.getString(2);
                String timestamp = set.getString(3);

                list.add(new Beacon(beaconID, uuid, timestamp));

            }

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return list;
    }

    public ArrayList<Beacon> retrieveAllData() {
        ArrayList<Beacon> list = new ArrayList<>();
        Connection conn = cm.getConnection();
        try {

            String query = "Select * from beacon_data";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet set = stmt.executeQuery();

            while (set.next()) {
                String beaconID = set.getString(1);
                String uuid = set.getString(2);
                String timestamp = set.getString(3);
                list.add(new Beacon(beaconID, uuid, timestamp));
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        } finally {
            try {
                conn.close();

            } catch (SQLException ex) {
                Logger.getLogger(DataManager.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        return list;
    }

    public boolean updateData(Beacon b) {
        Connection conn = cm.getConnection();
        boolean updated = false;
        String uuid = b.getUuid();
        String beaconID = b.getBeaconID();
        String timestamp = b.getTimestamp();
        try {
            String query = "Insert into beacon_data values ('" + beaconID + "','" + uuid + "','" + timestamp + "');";
            PreparedStatement stmt = conn.prepareStatement(query);
            updated = stmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
    }
}
