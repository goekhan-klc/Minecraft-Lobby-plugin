package net.kiligo.libary;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class Li {
	
	private static final String networkName = "Extact.net";
	private static final String loreTr = "§8-------------------\n§f";
	private static final String prefix = "§cLobby §7| ";
	private static final String rankpr = "§4Rang §7| ";
	private static boolean issilent;
	


	private static final File config = new File("plugins//Lobby//config.yml");
	private static YamlConfiguration configcfg = null;
	
	private static final Location spawnLocation = new Location(Bukkit.getWorld("world"), -4.5, 15, 48.5, -90, 0);
	
	
	public static String getLoreTr() {
		return loreTr;
	}
	public static String getNetworkName() {
		return networkName;
	}
	public static File getConfig() {
		return config;
	}
	public static YamlConfiguration getConfigcfg() {
		return configcfg;
	}
	public static void setConfigcfg(YamlConfiguration configcfg) {
		Li.configcfg = configcfg;
	}
	
	public static Location getSpawnLocation() {
		return spawnLocation;
	}
	public static String getPrefix() {
		return prefix;
	}
	
	public static String getRankPr() {
		return rankpr;
	}
	public static boolean isSilent() {
		return issilent;
	}
	
	public static void setSilent(boolean silent) {
		issilent = silent;;
	}

	
	
	
	


}
