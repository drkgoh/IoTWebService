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
    
    public DataManager(String deployment) throws IOException{
        Gson gson = new Gson();
        String contents = null;
        contents = new String(Files.readAllBytes(Paths.get(deployment + "/classes/config/config.json")));
        cm = gson.fromJson(contents, ConnectionManager.class);
    }
    
    //DateTime format is 2017-03-18 13:27:53
    //Need to rethink retrieveIntervalData
    //1. Need to check the SQL statement to verify accuracy
    //2. Should not return an ArrayList of Beacons, should be an ArrayList of GetResponse.
    //3. Change SQL timestamp to become a proper SQL timestamp/datetime object
    //4. Write bootstrap in case Database is empty.
    public ArrayList<HashMap<String,Object>> retrieveIntervalData(String startTime, String endTime) throws IOException {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        Connection conn = cm.getConnection();
        try {
            //find a way to retrieve the count(*) from the database using java. saves a lot of work
            //String query = "SELECT beacon_id, timestamp, count(*) as count from beacon_data where timestamp > '" + startTime + "' and timestamp < '" + endTime + "' group by timestamp, beacon_id;";
            String query = "select tab.beacon_id, tab.hour,tab.minute, count(*) from (select beacon_id,uuid,hour(timestamp) as hour,minute(timestamp) as minute from beacon_data where timestamp > '" + startTime + "' and timestamp < '" + endTime + "' group by unix_timestamp(timestamp) div 60,uuid,beacon_id order by beacon_id,minute) as tab group by beacon_id, tab.minute";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet set = stmt.executeQuery();
            while (set.next()) {
                HashMap<String, Object> each = new HashMap<>();
                String beaconID = set.getString(1);
                String hour = set.getString(2);
                String minute = set.getString(3);
                String count = set.getString(4);
                each.put("beacon_id", beaconID);
                each.put("hour", Integer.parseInt(hour));
                each.put("minute", Integer.parseInt(minute));
                each.put("count", Integer.parseInt(count));
                list.add(each);

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
    
    public ArrayList<HashMap<String,Object>> retrieveUuidData(String startTime, String endTime, String uuid) throws IOException {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        Connection conn = cm.getConnection();
        try {
            //find a way to retrieve the count(*) from the database using java. saves a lot of work
            //String query = "SELECT beacon_id, timestamp, count(*) as count from beacon_data where timestamp > '" + startTime + "' and timestamp < '" + endTime + "' group by timestamp, beacon_id;";
            String query = "select tab.beacon_id, tab.hour,tab.minute, count(*) from (select beacon_id,uuid,hour(timestamp) as hour,minute(timestamp) as minute from beacon_data where uuid = '" + uuid + "' and timestamp > '" + startTime + "' and timestamp < '" + endTime + "' group by unix_timestamp(timestamp) div 60,uuid,beacon_id order by beacon_id,minute) as tab group by beacon_id, tab.minute";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet set = stmt.executeQuery();
            while (set.next()) {
                HashMap<String, Object> each = new HashMap<>();
                String beaconID = set.getString(1);
                String hour = set.getString(2);
                String minute = set.getString(3);
                String count = set.getString(4);
                each.put("beacon_id", beaconID);
                each.put("hour", Integer.parseInt(hour));
                each.put("minute", Integer.parseInt(minute));
                each.put("count", 10);
                list.add(each);

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
        if(b==null) return false;
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
            e.printStackTrace(System.out);
        }
        return updated;
    }
}
