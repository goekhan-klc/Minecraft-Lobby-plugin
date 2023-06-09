package net.kiligo.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.kiligo.lobby.SQLManager;

public class LobbyItemData {
	
	public static void createUserData(String name, String uuid) {
		if(!name.contains("Bot")) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("INSERT INTO lobbyitems (UUID, Name, playerhiderstate, nickstate) VALUES (?,?,?,?)");
			ps.setString(1, uuid);
			ps.setString(2, name);
			ps.setInt(3, 1);
			ps.setInt(4, 0);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
	}
	
	public static void setNickState(String name, String uuid, int state) {
		
			SQLManager.update("UPDATE lobbyitems SET nickstate = '" + state + "' WHERE UUID = '" + uuid + "'");
			
	}
	
	public static void setHiderState(String name, String uuid, int state) {
		
			SQLManager.update("UPDATE lobbyitems SET playerhiderstate = '" + state + "' WHERE UUID = '" + uuid + "'");
			
	}
	
	
	
	public static int getNickState(String uuid) {

		if(SQLManager.isUserExists(uuid, "lobbyitems")) {
			ResultSet rs = SQLManager.getResult("SELECT nickstate FROM lobbyitems WHERE UUID = '" + uuid + "'");
			
			try {
			while(rs.next()) {
				return rs.getInt(1);
			}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		} else {
			return 0;
		}
		return 0;
	}
	
	public static int getHiderState(String uuid) {
		if(SQLManager.isUserExists(uuid, "lobbyitems")) {
			ResultSet rs = SQLManager.getResult("SELECT playerhiderstate FROM lobbyitems WHERE UUID = '" + uuid + "'");
			
			try {
			while(rs.next()) {
				return rs.getInt(1);
			}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		} else {
			return 1;
		}
		return 1;
	}
	
	
	
	
}
