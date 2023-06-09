package net.kiligo.lobby;

import org.bukkit.plugin.java.JavaPlugin;

import net.kiligo.commands.NPCCommand;
import net.kiligo.commands.RangCommand;
import net.kiligo.commands.setSpawnCommand;
import net.kiligo.commands.troll;
import net.kiligo.items.LobbySwitcher;
import net.kiligo.items.Navigator;
import net.kiligo.items.ProfileInventory;
import net.kiligo.libary.FakePlayer;
import net.kiligo.libary.Li;
import net.kiligo.libary.Methods;
import net.kiligo.listeners.WorldProtectors;
import net.kiligo.listeners.onInteract;
import net.kiligo.listeners.onInventoryClick;
import net.kiligo.listeners.onJoin;
import net.kiligo.listeners.onMove;
import net.kiligo.listeners.onQuit;

public class Lobby extends JavaPlugin {
	
	private static Lobby instance;
	private static SQLManager SQLManager;
	
	
	public void onEnable() {
		
		instance = this;
		
		if(!Li.getConfig().exists()) {
			
			Methods.generateConfigFile();
		} else {
			Li.setConfigcfg(YamlConfiguration.loadConfiguration(Li.getConfig()));
		}
		
		
		Bukkit.getPluginManager().registerEvents(new WorldProtectors(), this);
		Bukkit.getPluginManager().registerEvents(new onJoin(), this);
		Bukkit.getPluginManager().registerEvents(new onInteract(), this);
		Bukkit.getPluginManager().registerEvents(new LobbySwitcher(), this);
		Bukkit.getPluginManager().registerEvents(new Navigator(), this);
		Bukkit.getPluginManager().registerEvents(new onMove(), this);
		Bukkit.getPluginManager().registerEvents(new onQuit(), this);
		Bukkit.getPluginManager().registerEvents(new onInventoryClick(), this);
		
		getCommand("setspawn").setExecutor(new setSpawnCommand());
		getCommand("addnpc").setExecutor(new NPCCommand());
		getCommand("troll").setExecutor(new troll());
		getCommand("rang").setExecutor(new RangCommand());
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		SQLManager = new SQLManager();
		SQLManager.connect();
		SQLManager.generateDataTables();
		
		Navigator.loadNavigator();
		FakePlayer.loadNPCs();
		ProfileInventory.registerHeads();
		
		if(CloudAPI.getInstance().getServerId().contains("Silentlobby")) {
			Li.setSilent(true);
		} else {
			Li.setSilent(false);
		}
		
		net.kiligo.libary.Scoreboard.updater();
		
		System.out.println("[Lobby] Plugin loaded");
	}
	
	
	public void onDisable() {
		System.out.println("[Lobby] Plugin unloaded");
	}
	
	
	public static Lobby getLobby() {
		return instance;
		
	}

}
