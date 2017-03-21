
import dao.DataManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Beacon;

public class iotApp {

    public static void main(String[] args) throws IOException {
     DataManager dm = new DataManager(System.getProperty("user.dir") + "/build/web/WEB-INF");
     ArrayList<Beacon> list = dm.retrieveIntervalData("2017-03-18 13:25:00","2017-03-18 13:30:00");
     //Beacon be = new Beacon("iot40","qwu2840dhwl1","2017-03-18 13:29:53");
     //System.out.println(dm.updateData(be));
     
     for (int i = 0 ; i < list.size(); i++) {
         Beacon b = list.get(i);
         System.out.println(b.getTimestamp() + " | " + b.getBeaconID() + " - " + b.getUuid());
     }

    }
    
}
