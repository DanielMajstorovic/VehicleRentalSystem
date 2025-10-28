package org.unibl.etf.ip.dao;

import org.unibl.etf.ip.model.Rental;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDao {
	
	
	public int createRental(Rental r) throws Exception {
	    String sql = "INSERT INTO RENTAL "+
	        "(StartTime,StartX,StartY,DriversLicense,PaymentCard, "+
	         "VEHICLE_VehicleID,CLIENT_USER_UserID) "+
	      "VALUES (?,?,?,?,?,?,?)";
	    try(Connection con=DBUtil.getConnection();
	        PreparedStatement ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){

	        ps.setTimestamp(1,r.getStartTime());
	        ps.setDouble   (2,r.getStartX());
	        ps.setDouble   (3,r.getStartY());
	        ps.setString   (4,r.getDriversLicense());
	        ps.setString   (5,r.getPaymentCard());
	        ps.setInt      (6,r.getVehicleId());
	        ps.setInt      (7,r.getClientId());
	        ps.executeUpdate();
	        ResultSet rs=ps.getGeneratedKeys();
	        if(rs.next()) return rs.getInt(1);
	    }
	    return -1;
	}

	
	
	public List<Rental> findFinishedByClient(int clientUserId) throws Exception {
	    String sql = "SELECT r.RentalID, r.StartTime, r.EndTime, r.TotalAmount, "+
	             "CONCAT(m.Name,' ',v.Model) AS VehName "+
	      "FROM RENTAL r "+
	      "JOIN VEHICLE v   ON v.VehicleID = r.VEHICLE_VehicleID "+
	      "JOIN MANUFACTURER m ON m.ManufacturerID = v.MANUFACTURER_ManufacturerID "+
	      "WHERE r.CLIENT_USER_UserID=? AND r.EndTime IS NOT NULL "+
	      "ORDER BY r.EndTime DESC";
	    try (Connection con = DBUtil.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, clientUserId);
	        ResultSet rs = ps.executeQuery();
	        List<Rental> list = new ArrayList<>();
	        while (rs.next()) {
	            Rental r = new Rental();
	            r.setId(rs.getInt("RentalID"));
	            r.setStartTime(rs.getTimestamp("StartTime"));
	            r.setEndTime  (rs.getTimestamp("EndTime"));
	            r.setTotalAmount(rs.getDouble("TotalAmount"));
	            r.setVehicleModel(rs.getString("VehName")); 
	            list.add(r);
	        }
	        return list;
	    }
	}

	

	public void finishRental(int rentalId, Timestamp end, double endX, double endY, int duration, double total)
			throws Exception {

		String sql = 
				"UPDATE RENTAL "+
				"SET EndTime=?, EndX=?, EndY=?, Duration=?, TotalAmount=? "+
				"WHERE RentalID=?";
		try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setTimestamp(1, end);
			ps.setDouble(2, endX);
			ps.setDouble(3, endY);
			ps.setInt(4, duration);
			ps.setDouble(5, total);
			ps.setInt(6, rentalId);
			ps.executeUpdate();
		}
	}


	public Rental findActiveByClient(int clientUserId) throws Exception {
		String sql = "SELECT r.RentalID, r.StartTime, " + "v.UID, v.Model, v.PricePerSecond " + "FROM RENTAL r "
				+ "JOIN VEHICLE v ON v.VehicleID = r.VEHICLE_VehicleID "
				+ "WHERE r.CLIENT_USER_UserID = ? AND r.EndTime IS NULL " + "ORDER BY r.StartTime ASC " + "LIMIT 1";
		try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, clientUserId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Rental r = new Rental();
				r.setId(rs.getInt("RentalID"));
				r.setStartTime(rs.getTimestamp("StartTime"));
				r.setVehicleUid(rs.getString("UID"));
				r.setVehicleModel(rs.getString("Model"));
				r.setPricePerSecond(rs.getDouble("PricePerSecond"));
				return r;
			}
		}
		return null;
	}
}
