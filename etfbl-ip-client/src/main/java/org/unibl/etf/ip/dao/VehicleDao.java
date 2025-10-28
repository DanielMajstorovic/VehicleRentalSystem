package org.unibl.etf.ip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.ip.model.Vehicle;

public class VehicleDao {
	
	
	public List<Vehicle> listAvailableScooters() throws Exception {
	    String sql = 
	        "SELECT v.VehicleID, v.UID, v.Model, v.PricePerSecond, "+
	               "m.Name AS Manufacturer "+
	        "FROM VEHICLE v "+
	        "JOIN E_SCOOTER s ON s.VEHICLE_VehicleID = v.VehicleID "+
	        "JOIN MANUFACTURER m ON m.ManufacturerID = v.MANUFACTURER_ManufacturerID "+
	        "WHERE v.Status='AVAILABLE' AND v.Deleted=0";
	    try(Connection con=DBUtil.getConnection();
	        PreparedStatement ps=con.prepareStatement(sql);
	        ResultSet rs=ps.executeQuery()){

	        List<Vehicle> list=new ArrayList<>();
	        while(rs.next()){
	            Vehicle v=new Vehicle();
	            v.setId(rs.getInt("VehicleID"));
	            v.setUid(rs.getString("UID"));
	            v.setModel(rs.getString("Model"));
	            v.setPricePerSecond(rs.getDouble("PricePerSecond"));
	            v.setManufacturerName(rs.getString("Manufacturer"));
	            list.add(v);
	        }
	        return list;
	    }
	}

	
	
	public List<Vehicle> listAvailableEBikes() throws Exception {
	    String sql = 
	        "SELECT v.VehicleID, v.UID, v.Model, v.PricePerSecond, "+
	               "m.Name AS Manufacturer "+
	        "FROM VEHICLE v "+
	        "JOIN E_BIKE eb           ON eb.VEHICLE_VehicleID = v.VehicleID "+
	        "JOIN MANUFACTURER m      ON m.ManufacturerID     = v.MANUFACTURER_ManufacturerID "+
	        "WHERE v.Status='AVAILABLE' AND v.Deleted=0";
	    try (Connection con = DBUtil.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        List<Vehicle> list = new ArrayList<>();
	        while (rs.next()) {
	            Vehicle v = new Vehicle();
	            v.setId(rs.getInt("VehicleID"));
	            v.setUid(rs.getString("UID"));
	            v.setModel(rs.getString("Model"));
	            v.setPricePerSecond(rs.getDouble("PricePerSecond"));
	            v.setManufacturerName(rs.getString("Manufacturer"));
	            list.add(v);
	        }
	        return list;
	    }
	}

	
	
	public List<Vehicle> listAvailableCars() throws Exception {
	    String sql = "SELECT v.VehicleID, v.UID, v.Model, v.PricePerSecond, "+
	               "m.Name AS Manufacturer "+
	        "FROM VEHICLE v "+
	        "JOIN CAR car           ON car.VEHICLE_VehicleID = v.VehicleID "+
	        "JOIN MANUFACTURER m ON m.ManufacturerID = v.MANUFACTURER_ManufacturerID "+
	        "WHERE v.Status='AVAILABLE' AND v.Deleted=0";
	    try (Connection con = DBUtil.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        List<Vehicle> list = new ArrayList<>();
	        while (rs.next()) {
	            Vehicle v = new Vehicle();
	            v.setId   (rs.getInt("VehicleID"));
	            v.setUid  (rs.getString("UID"));
	            v.setModel(rs.getString("Model"));
	            v.setPricePerSecond(rs.getDouble("PricePerSecond"));
	            v.setManufacturerName(rs.getString("Manufacturer"));
	            list.add(v);
	        }
	        return list;
	    }
	}

	public void setPositionAndRented(String uid,double x,double y) throws Exception {
	    String sql="UPDATE VEHICLE SET X=?,Y=?,Status='RENTED' WHERE UID=?";
	    try(Connection con=DBUtil.getConnection();
	        PreparedStatement ps=con.prepareStatement(sql)){
	        ps.setDouble(1,x); ps.setDouble(2,y); ps.setString(3,uid); ps.executeUpdate();
	    }
	}

	

    public void setPositionAndAvailable(String vehicleUid, double x, double y) throws Exception {
        String sql = "UPDATE VEHICLE SET X=?, Y=?, Status='AVAILABLE' WHERE UID=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, x);
            ps.setDouble(2, y);
            ps.setString(3, vehicleUid);
            ps.executeUpdate();
        }
    }
}
