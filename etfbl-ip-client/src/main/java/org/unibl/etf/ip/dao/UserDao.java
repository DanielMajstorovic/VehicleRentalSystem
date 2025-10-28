package org.unibl.etf.ip.dao;

import org.unibl.etf.ip.model.User;

import java.sql.*;

public class UserDao {
	
	
	public boolean changePassword(int userId,String current,String next) throws Exception{
	    String sql="UPDATE USER SET Password=? WHERE UserID=? AND Password=?";
	    try(Connection con=DBUtil.getConnection();
	        PreparedStatement ps=con.prepareStatement(sql)){
	        ps.setString(1,next);
	        ps.setInt   (2,userId);
	        ps.setString(3,current);
	        return ps.executeUpdate()==1;
	    }
	}
	public void deactivate(int userId) throws Exception{
	    try(Connection con=DBUtil.getConnection();
	        PreparedStatement ps=con.prepareStatement("UPDATE USER SET Deleted=1 WHERE UserID=?")){
	        ps.setInt(1,userId); ps.executeUpdate();
	    }
	}


    public User findByUsernameAndPassword(String username, String password) throws Exception {
        String sql = "SELECT * FROM USER WHERE Username=? AND Password=? AND Deleted=0";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public int insert(User u) throws Exception {
        String sql = "INSERT INTO USER (Username, Password, FirstName, LastName, Deleted) VALUES (?,?,?,?,0)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFirstName());
            ps.setString(4, u.getLastName());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public boolean existsByUsername(String username) throws Exception {
        String sql = "SELECT 1 FROM USER WHERE Username=? AND Deleted=0";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    private User map(ResultSet rs) throws Exception {
        User u = new User();
        u.setId(rs.getInt("UserID"));
        u.setUsername(rs.getString("Username"));
        u.setPassword(rs.getString("Password"));
        u.setFirstName(rs.getString("FirstName"));
        u.setLastName(rs.getString("LastName"));
        u.setDeleted(rs.getInt("Deleted") == 1);
        return u;
    }
}
