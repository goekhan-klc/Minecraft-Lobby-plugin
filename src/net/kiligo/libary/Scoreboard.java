package net.kiligo.libary;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

import net.kiligo.lobby.Lobby;

public class Scoreboard {
	
	
	@SuppressWarnings("deprecation")
	public static void setScoreboard(Player p) {
		org.bukkit.scoreboard.Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = sb.getObjective("aaa");
		
		if(obj == null) {
			obj = sb.registerNewObjective("aaa", "bbb");
		}
		
		obj.setDisplayName("§f§l" + Li.getNetworkName().toUpperCase());
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		obj.getScore("       ").setScore(14);
		obj.getScore("§fCoins:").setScore(13);
		obj.getScore(updateTeam(sb, "coins", "§e" + "0", "", ChatColor.RED)).setScore(12);
		obj.getScore("     ").setScore(11);
		obj.getScore("§fDein Rang:").setScore(10);
		obj.getScore(updateTeam(sb, "rang", "§7" + p.getName(), "", ChatColor.AQUA)).setScore(9);
		obj.getScore("    ").setScore(8);
		obj.getScore("§fDein Clan:").setScore(7);
		obj.getScore(updateTeam(sb, "clan", "§b" + "Kein Clan", "", ChatColor.BLUE)).setScore(6);
		obj.getScore("  ").setScore(5);
		obj.getScore("§fFreunde:").setScore(4);
		obj.getScore(updateTeam(sb, "freunde", "§a" + "0§7/0", "", ChatColor.DARK_GREEN)).setScore(3);
		obj.getScore(" ").setScore(2);
		obj.getScore("§fSpielzeit:").setScore(1);
		obj.getScore(updateTeam(sb, "spielzeit", "§e" + "0h", "", ChatColor.GRAY)).setScore(0);
		
		Team admin = getTeam(sb, "000Admin", "§4Admin §8| §4", "§4");
		Team spieler = getTeam(sb, "001Spieler", "§7", "§7");
		
		for(Player pl : Bukkit.getOnlinePlayers()) {
			if(pl.hasPermission("rang.admin")) {
				admin.addPlayer(pl);
			} else {
				spieler.addPlayer(pl);
			}
			pl.setScoreboard(sb);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void updateScoreboard(Player p) {
		if(p.getScoreboard() == null) {
			setScoreboard(p);
		}
		
		org.bukkit.scoreboard.Scoreboard sb = p.getScoreboard();
		Objective obj = sb.getObjective("aaa");
		
		obj.getScore(updateTeam(sb, "coins", "§e" + "0", "", ChatColor.RED)).setScore(12);
		obj.getScore(updateTeam(sb, "rang", "§7" + "Spieler", "", ChatColor.AQUA)).setScore(9);
		obj.getScore(updateTeam(sb, "clan", "§b" + "Kein Clan", "", ChatColor.BLUE)).setScore(6);
		obj.getScore(updateTeam(sb, "freunde", "§a" + "0§7/0", "", ChatColor.DARK_GREEN)).setScore(3);
		obj.getScore(updateTeam(sb, "spielzeit", "§e" + "0h", "", ChatColor.GRAY)).setScore(0);
		
		
		Team admin = getTeam(sb, "000Admin", "§4Admin §8| §4", "§4");
		Team spieler = getTeam(sb, "001Spieler", "§7", "§7");
		
		for(Player pl : Bukkit.getOnlinePlayers()) {
			if(pl.hasPermission("rang.admin")) {
				admin.addPlayer(pl);
			} else {
				spieler.addPlayer(pl);
			}
		}
		
	}
	
	
	public static Team getTeam(org.bukkit.scoreboard.Scoreboard sb, String t, String prefix, String suffix) {
		Team team = sb.getTeam(t);
		if(team == null) {
			team = sb.registerNewTeam(t);
		}
		
		team.setPrefix(prefix);
		team.setSuffix(suffix);
		return team;
	}
	
	public static String updateTeam(org.bukkit.scoreboard.Scoreboard sb, String t, String prefix, String suffix, ChatColor entry) {
		Team team = sb.getTeam(t);
		if(team == null) {
			team = sb.registerNewTeam(t);
		}
		
		try {
		team.setPrefix(prefix);
		} catch(IllegalArgumentException | IllegalStateException e) {
			e.printStackTrace();
		}
		team.setSuffix(suffix);
		team.addEntry(entry.toString()); 
		
		return entry.toString();
	}
	
	
	public static void updater() {
		Bukkit.getScheduler().runTaskTimer(Lobby.getLobby(), () -> {
			
			new Runnable() {
				
				@Override
				public void run() {
					for(Player p : Bukkit.getOnlinePlayers()) {
						updateScoreboard(p);
					}
				}
			};
			
		}, 20*5, 20*5);
	}

}
