package net.kiligo.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.kiligo.libary.Methods;
import net.kiligo.lobby.SQLManager;


public class LocationData {

	public static void setLocation(Player p) {
		if(SQLManager.isUserExists(p.getUniqueId().toString(), "locations")) {
		
			Location loc = p.getLocation();	
			
			SQLManager.update("UPDATE locations SET x = " + loc.getX() + ", y = " + loc.getY() + ", z = " + loc.getZ()
				+ ", yaw = " + loc.getYaw() + ", Pitch = " + loc.getPitch() + " WHERE UUID = '" + p.getUniqueId().toString() + "'");
			
			
		} else {
			if(!p.getName().contains("Fakespieler")) {
			Location loc = p.getLocation();
			try {
				PreparedStatement ps = SQLManager.getConnection().prepareStatement("INSERT INTO locations (UUID, Name, x, y, z, yaw, pitch) VALUES (?,?,?,?,?,?,?)");
				ps.setString(1, p.getUniqueId().toString());
				ps.setString(2, p.getName());
				ps.setDouble(3, loc.getX());
				ps.setDouble(4, loc.getY());
				ps.setDouble(5, loc.getZ());
				ps.setFloat(6, loc.getYaw());
				ps.setFloat(7, loc.getPitch());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		}
		
		
		
	}
	
	
	public static Location getLocation(Player p) {
		if(SQLManager.isUserExists(p.getUniqueId().toString(), "locations")) {
			ResultSet rs = SQLManager.getResult("SELECT x, y, z, yaw, pitch FROM locations WHERE UUID = '" + p.getUniqueId().toString() + "'");
			
			try {
				while(rs.next()) {
					Double x = rs.getDouble(1);
					Double y = rs.getDouble(2);
					Double z = rs.getDouble(3);
					Float yaw = rs.getFloat(4);
					Float pitch = rs.getFloat(5);
					
					y++;
					return new Location (Bukkit.getWorld("world"), x, y, z, yaw, pitch);
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			return Methods.getConfigOnlyLocation("Spawns", "Spawn");
		}
		
		return null;
	}
}
