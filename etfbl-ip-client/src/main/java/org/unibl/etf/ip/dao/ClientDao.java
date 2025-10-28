package org.unibl.etf.ip.dao;

import org.unibl.etf.ip.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientDao {
	
	
	public Client findById(int userId)throws Exception{
	    String sql="SELECT * FROM CLIENT WHERE USER_UserID=?";
	    try(Connection con=DBUtil.getConnection();
	        PreparedStatement ps=con.prepareStatement(sql)){
	        ps.setInt(1,userId);
	        ResultSet rs=ps.executeQuery();
	        if(rs.next()){
	           Client c=new Client();
	           c.setUserId(userId);
	           c.setEmail(rs.getString("Email"));
	           c.setPhone(rs.getString("Phone"));
	           c.setIdNumber(rs.getString("IDNumber"));
	           return c;
	        }
	    }
	    return null;
	}

	

	public void adjustBalance(int userId, double delta) throws Exception {
		String sql = "UPDATE CLIENT SET Balance = Balance + ? WHERE USER_UserID=?";
		try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setDouble(1, delta); 
			ps.setInt(2, userId);
			ps.executeUpdate();
		}
	}

	public void insert(Client c) throws Exception {
		String sql = " INSERT INTO CLIENT (USER_UserID, IDNumber, PassportNumber, Email, Phone, HasAvatarImage, Balance) VALUES (?,?,?,?,?, ?, 100000.00) ";
		try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, c.getUserId());
			ps.setString(2, c.getIdNumber());
			ps.setString(3, c.getPassportNumber());
			ps.setString(4, c.getEmail());
			ps.setString(5, c.getPhone());
			ps.setInt(6, c.isHasAvatarImage() ? 1 : 0);
			ps.executeUpdate();
		}
	}
	
	
	
	
	public String getFullNameByClientId(int userId) throws Exception {
        String sql = ""
            + "SELECT u.FirstName, u.LastName "
            + "FROM CLIENT c "
            + "  JOIN `USER` u ON c.USER_UserID = u.UserID "
            + "WHERE c.USER_UserID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("FirstName")
                         + " "
                         + rs.getString("LastName");
                } else {
                    return null;
                }
            }
        }
    }
	
	
	
}
