package net.kiligo.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.DefaultType;
import de.dytanic.cloudnet.lib.server.info.ServerInfo;
import net.kiligo.libary.ItemHandler;
import net.kiligo.libary.Li;
import net.kiligo.libary.Methods;

public class LobbySwitcher implements Listener{

	 public static CloudAPI playerManager = CloudAPI.getInstance();
	
	@EventHandler
	public void onClickE(InventoryClickEvent e) {
		
		if(e.getClickedInventory() != null) {
			if(e.getInventory().getTitle().equals("§bLobby wechseln")) {
				if(e.getWhoClicked() instanceof Player) {
					if(e.getCurrentItem() != null) {
						e.setCancelled(true);
						Player p = (Player) e.getWhoClicked();
						
						p.closeInventory();
						Methods.connectPlayer(p, "Lobby-" + e.getCurrentItem().getItemMeta().getDisplayName().substring(8));

								;
					}
				}
			}
		}
		
	}
	

	
	
	public static void openLobbyInventory(Player p) {
		ArrayList<ServerInfo> lobbyServers = new ArrayList<ServerInfo>(CloudAPI.getInstance().getServers("Lobby"));
		ArrayList<String> serverNames = new ArrayList<>();
		
		String playerServer = playerManager.getOnlinePlayer(p.getUniqueId()).getServer();
		
		
		for(ServerInfo name : lobbyServers) {
			serverNames.add(name.getServiceId().getServerId());
		}
		
		Collections.sort(serverNames);
		
		Inventory inv = Bukkit.createInventory(null, 4*9, "§bLobby wechseln");
		
		
		p.openInventory(inv);
	
		
		for(int i=0; i < serverNames.size(); i++) {
			
			if(playerServer != CloudAPI.getInstance().getServerId()) {
			
				inv.setItem(i, ItemHandler.getSkullByValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmZjNWUyODZkOWMxZDE5ZDZkNjA1OWQ2OWIzMzEzOWYxOGNlNjlmYWVmZjBmZWU4OTRkMDRlOTNlN2NiOTg2NyJ9fX0="
					, "§fLobby " + serverNames.get(i).substring(6), Li.getLoreTr() + "§b" + CloudAPI.getInstance().getServerInfo(serverNames.get(i)).getOnlineCount() + " §fSpieler online\n\n§fKlicke zum verbinden"));
			
			} else {
				
				inv.setItem(i, ItemHandler.getSkullByValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWM5MDZkNjg4ZTY1ODAyNTY5ZDk3MDViNTc5YmNlNTZlZGM4NmVhNWMzNmJkZDZkNmZjMzU1MTZhNzdkNCJ9fX0=="
					, "§fLobby " + serverNames.get(i).substring(6), Li.getLoreTr() + "§b" + CloudAPI.getInstance().getServerInfo(serverNames.get(i)).getOnlineCount() + " §fSpieler online\n\n§aVerbunden"));
					
				}
		}
		
		p.updateInventory();
	}
	
}
