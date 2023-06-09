package net.kiligo.lobby;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import net.kiligo.libary.Li;

public class SQLManager {

	private static Connection connection;
	private String hostname;
	private int port;
	private String database;
	private String username;
	private String password;
	
	
	public SQLManager() {
		this.hostname = Li.getConfigcfg().getString("MySQL.host");
		this.port = Li.getConfigcfg().getInt("MySQL.port");
		this.database = Li.getConfigcfg().getString("MySQL.database");
		this.username = Li.getConfigcfg().getString("MySQL.username");
		this.password = Li.getConfigcfg().getString("MySQL.password");
	}

	
	public void connect() {
		try {
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database + "?autoReconnect=true", username, password);
			System.out.println("[Lobby] Database connected via MySQL");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[Lobby] An error oncurred whilst connecting to the MySQL database...");
			Lobby.getLobby().getPluginLoader().disablePlugin(Lobby.getLobby());
		}
	}
	
	
    public static void update(String qry) {
    	try {
    		PreparedStatement ps = connection.prepareStatement(qry);
    		ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    
    public static ResultSet getResult(String qry) {
    	try {
    		PreparedStatement ps = connection.prepareStatement(qry);
    		return ps.executeQuery();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    
    public boolean isConnected() {
		
    	return (connection == null ? false : true);
    }
    
    
	public static boolean isUserExists(String uuid, String table) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("SELECT * FROM " + table + " WHERE UUID = ?");
			ps.setString(1, uuid);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
    
    
    
    
    public void generateDataTables() {
		PreparedStatement ps;
		
		try {
			ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS locations (UUID VARCHAR(50), Name VARCHAR(16), x DOUBLE, y DOUBLE, z DOUBLE, yaw FLOAT, pitch FLOAT)");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    
    
	try {
		ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS lobbyitems (UUID VARCHAR(50), Name VARCHAR(16), playerhiderstate INT, nickstate INT)");
		ps.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	}

}
    
    

	public static Connection getConnection() {
		return connection;
	}


	public void setConnection(Connection connection) {
		SQLManager.connection = connection;
	}


	public String getHostname() {
		return hostname;
	}


	public void setHostname(String hostname) {
		this.hostname = hostname;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public String getDatabase() {
		return database;
	}


	public void setDatabase(String database) {
		this.database = database;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
}
