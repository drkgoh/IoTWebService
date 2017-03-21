/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author derrickgoh
 */
public class PostParameters {
    String beaconID;
    String uuid;
    String timeStamp;
    
    public PostParameters(String beacon, String uid, String timeStamp){
        this.beaconID = beacon;
        this.uuid = uid;
        this.timeStamp = timeStamp;
    }
    
    
    
}
