package net.kiligo.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.kiligo.libary.Li;
import net.kiligo.lobby.SQLManager;


public class PermsData {

	
	public static String getRankPrefix(String rankname) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("SELECT Prefix FROM RangsystemRaenge WHERE Name = ?");
			ps.setString(1, rankname);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String prefix = rs.getString(1);
				return prefix;
			}
		} catch (SQLException e) {
			System.out.println("[KiligoRanks] Ein Fehler ist während einer Abfrage aufgelaufen.. (Möglich: Du hast einen bestimmten Rang nicht erstellt)");
		}
		return "";
	}
	
	
	public static String getRankColor(String rankname) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("SELECT Color FROM RangsystemRaenge WHERE Name = ?");
			ps.setString(1, rankname);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String color = rs.getString(1);
				return color;
			}
		} catch (SQLException e) {
			System.out.println("[KiligoRanks] Ein Fehler ist während einer Abfrage aufgelaufen.. (Möglich: Du hast einen bestimmten Rang nicht erstellt)");
		}
		return "§f";
	}
	
	public static String getPlayerRank(String p) {
		if(isUserExist(p)) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("SELECT Rang FROM RangsystemSpieler WHERE Name = ?");
			ps.setString(1, p);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String rang = rs.getString(1);
				return rang;
			}
			} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Spieler";
	} else {
		return "Spieler";
	}
	}
	
	public static String getPlayerName(String player) {
			String rankname = null;
			String playername = null;
			
	if(isUserExist(player)) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("SELECT Rang, Name FROM RangsystemSpieler WHERE Name = ?");
			ps.setString(1, player);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
					rankname = rs.getString(1);
					playername = rs.getString(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			return getRankColor(rankname) + playername;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "§a" + player;
	} else {
		return "§a" + player;
	}
	}
	
	public static String getRankName(String rang) {
		String rankname = null;
	try {
		PreparedStatement ps = SQLManager.getConnection().prepareStatement("SELECT Name FROM RangsystemRaenge WHERE Name = ?");
		ps.setString(1, rang);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			rankname = rs.getString(1);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	try {
		return getRankColor(rankname) + rankname;
	} catch(Exception e) {
		e.printStackTrace();
	}
	return "§f" + rankname;
}
	
	public static String getRankNameNoColor(String rang) {
		String rankname = null;
	try {
		PreparedStatement ps = SQLManager.getConnection().prepareStatement("SELECT Name FROM RangsystemRaenge WHERE Name = ?");
		ps.setString(1, rang);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			rankname = rs.getString(1);
			return rankname;
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return "Unbekannt";
}
	
	public static long getPlayerRankDauer(String player) {
		long dauer;
	try {
		PreparedStatement ps = SQLManager.getConnection().prepareStatement("SELECT Dauer FROM RangsystemSpieler WHERE Name = ?");
		ps.setString(1, player);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			dauer = rs.getLong(1);
			return dauer;
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return -1;
}
	
	public static boolean isUserExist(String p) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("SELECT * FROM RangsystemSpieler WHERE Name = ?");
			ps.setString(1, p);
			ResultSet rs = ps.executeQuery();

			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public static void createUserRegister(Player p) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("INSERT INTO RangsystemSpieler(UUID, Name, Rang, Dauer) VALUES (?,?,?,?)");
			ps.setString(1, p.getUniqueId().toString());
			ps.setString(2, p.getName());
			ps.setString(3, "Spieler");
			ps.setString(4, "-1");
			ps.executeUpdate();
		} catch (SQLException e) {
			p.sendMessage(Li.getRankPr() + "§cWährend dem erstellen deines Accounts ist ein Fehler aufgetreten...");
			e.printStackTrace();
		}
	}
	
	public static void createUserRegisterOffline(String name) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("INSERT INTO RangsystemSpieler(Name, Rang, Dauer) VALUES (?,?,?)");
			ps.setString(1, name);
			ps.setString(2, "Spieler");
			ps.setString(3, "-1");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static int getPermLevel(Player p) {
		String rank = getPlayerRank(p.getName());
		
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("SELECT Permlevel FROM RangsystemRaenge WHERE Name = ?");
			ps.setString(1, rank);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int level = rs.getInt(1);
				return level;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getPermLevelOffline(String p) {
		String rank = getPlayerRank(p);
		
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("SELECT Permlevel FROM RangsystemRaenge WHERE Name = ?");
			ps.setString(1, rank);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int level = rs.getInt(1);
				return level;
			}
		} catch (SQLException e) {
			return 0;
		}
		return 0;
	}
	
	public static int getRankPermLevel(String rank) {
		
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("SELECT Permlevel FROM RangsystemRaenge WHERE Name = ?");
			ps.setString(1, rank);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int level = rs.getInt(1);
				return level;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void setRank(String player, String rang, String dauer) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("UPDATE RangsystemSpieler SET Rang = ?, Dauer = ? WHERE Name = ?");
			ps.setString(1, rang);
			ps.setString(2, dauer);
			ps.setString(3, player);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isRankExists(String name) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("SELECT * FROM RangsystemRaenge WHERE Name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void deleteRank(String name) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("DELTE FROM RangsystemRaenge WHERE Name = ?");
			ps.setString(1, name);
			ps.executeLargeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void createRank(String name, String color, String prefix, int level) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("INSERT INTO RangsystemRaenge(Name,Color,Prefix,Permlevel) VALUES (?,?,?,?)");
			ps.setString(1, name);
			ps.setString(2, color);
			ps.setString(3, prefix);
			ps.setInt(4, level);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setRankColor(String name, String color) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("UPDATE RangsystemRaenge SET Color = ? WHERE Name = ?");
			ps.setString(1, color);
			ps.setString(2, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setRankPrefix(String name, String prefix) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("UPDATE RangsystemRaenge SET Prefix = ? WHERE Name = ?");
			ps.setString(1, prefix);
			ps.setString(2, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setRankPermLevel(String name, int level) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("UPDATE RangsystemRaenge SET Permlevel = ? WHERE Name = ?");
			ps.setInt(1, level);
			ps.setString(2, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> getAllRanks() {
		ArrayList<String> ränge = new ArrayList<>();
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("SELECT Name FROM RangsystemRaenge ORDER BY Permlevel");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ränge.add(PermsData.getRankColor(rs.getString("Name")) + rs.getString("Name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ränge;
	}
	
	
	public static void deleteAccount(String name) {
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("DELETE FROM RangsystemSpieler WHERE Name = ?");
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("DELETE FROM belohnung WHERE Name = ?");
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("DELETE FROM coinsystem WHERE Name = ?");
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("DELETE FROM features WHERE Name = ?");
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("DELETE FROM freunde WHERE Name = ?");
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("DELETE FROM lobbyitems WHERE Name = ?");
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("DELETE FROM locations WHERE Name = ?");
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = SQLManager.getConnection().prepareStatement("DELETE FROM team WHERE Name = ?");
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
 	
}
