package net.kiligo.libary;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.kiligo.lobby.Lobby;

public class Methods {
	
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	
	
	public static void generateConfigFile() {
		
		try {
			Li.getConfig().createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {
			
			Li.setConfigcfg(YamlConfiguration.loadConfiguration(Li.getConfig()));
			Li.getConfigcfg().options().copyDefaults(true);
			
			Li.getConfigcfg().addDefault("MySQL", null);
			Li.getConfigcfg().addDefault("MySQL.host", "localhost");
			Li.getConfigcfg().addDefault("MySQL.port", 3306);
			Li.getConfigcfg().addDefault("MySQL.database", "~~~");
			Li.getConfigcfg().addDefault("MySQL.username", "root");
			Li.getConfigcfg().addDefault("MySQL.password", "~~~");
			
			Li.getConfigcfg().save(Li.getConfig());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	@SuppressWarnings("resource")
	public static void connectPlayer(Player p, String server) {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(bs);
		
		try {
			out.writeUTF("Connect");
			out.writeUTF(server);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		p.sendPluginMessage(Lobby.getLobby(), "BungeeCord", bs.toByteArray());
	}
	
	
	@SuppressWarnings("unchecked")
	public static void createConfigLocation(Location loc, String path, String name, String displayname, int item, String slot, File file, YamlConfiguration cfg) {
		
		cfg.set(path + "." + name +".World", loc.getWorld().getName());
		cfg.set(path + "." + name + ".X", loc.getX());
		cfg.set(path + "." + name + ".Y", loc.getY());
		cfg.set(path + "." + name + ".Z", loc.getZ());
		cfg.set(path + "." + name + ".Yaw", loc.getYaw());
		cfg.set(path + "." + name + ".Pitch", loc.getPitch());
		cfg.set(path + "." + name  + ".Displayname", displayname);
		cfg.set(path + "." + name  + ".Item", item);
		cfg.set(path + "." + name  + ".Slot", slot);
		cfg.set(path + "." + name + ".Lore", "[Bitte eintragen]");
		
		if(cfg.contains("Names")) {
			((List<String>) Li.getConfigcfg().getList("Names")).add(name);	
		} else {
			List<String> list = new ArrayList<String>();
			list.add(name);
			cfg.set("Names", list);
		}
		
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public static void createConfigLocationNPC(Location loc, String path, String name, String displayname, File file, YamlConfiguration cfg) {
		
		cfg.set(path + "." + name +".World", loc.getWorld().getName());
		cfg.set(path + "." + name + ".X", loc.getX());
		cfg.set(path + "." + name + ".Y", loc.getY());
		cfg.set(path + "." + name + ".Z", loc.getZ());
		cfg.set(path + "." + name + ".Yaw", loc.getYaw());
		cfg.set(path + "." + name + ".Pitch", loc.getPitch());
		cfg.set(path + "." + name  + ".Displayname", displayname);
		
		if(cfg.contains("NPCNames")) {
			((List<String>) Li.getConfigcfg().getList("NPCNames")).add(name);	
		} else {
			List<String> list = new ArrayList<String>();
			list.add(name);
			cfg.set("NPCNames", list);
		}
		
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Location getConfigOnlyLocation(String path, String name) {
		World w = Bukkit.getWorld(Li.getConfigcfg().getString(path + "." + name + ".World"));
		double x = Double.valueOf(Li.getConfigcfg().getString(path + "." + name + ".X"));
		double y = Double.valueOf(Li.getConfigcfg().getString(path + "." + name + ".Y"));
		double z = Double.valueOf(Li.getConfigcfg().getString(path + "." + name + ".Z"));
		float yaw = Float.valueOf(Li.getConfigcfg().getString(path + "." + name + ".Yaw"));
		float pitch = Float.valueOf(Li.getConfigcfg().getString(path + "." + name + ".Pitch"));
		
		return new Location(w, x, y, z, yaw, pitch);
	}
	
	
	public static String randomAlphaNumericString(int count) {
		StringBuilder builder = new StringBuilder();
		
		while(count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		
		return builder.toString();
	}
	
}
