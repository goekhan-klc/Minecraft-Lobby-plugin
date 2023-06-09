package net.kiligo.listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.server.info.ServerInfo;
import net.kiligo.data.LobbyProfile;
import net.kiligo.items.LobbySwitcher;
import net.kiligo.items.Navigator;
import net.kiligo.items.ProfileInventory;
import net.kiligo.libary.ItemHandler;
import net.kiligo.libary.Li;
import net.kiligo.libary.Methods;
import net.kiligo.lobby.Lobby;

public class onInteract implements Listener {
	
	@EventHandler
	public void onInteractE(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if(e.getAction() != null) {
			if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				
				if(e.getItem().getItemMeta().getDisplayName().equals("§cNavigator §7<Rechtsklick>")) {
					Navigator.openNavigatorInventory(p);
					
				}
				

				
				if(e.getItem().getItemMeta().getDisplayName().equals("§aAlle Spieler sichtbar §7<Rechtsklick>")) {
					LobbyProfile.getPlayerProfiles().get(p.getUniqueId().toString()).setPlayerhiderstate(2);
					p.getInventory().setItem(1,	ItemHandler.getDye("§5Nur VIPs sichtbar §7<Rechtsklick>", DyeColor.PURPLE, Li.getLoreTr() + "§7Verstecke die Spieler die\n§7du nicht sehen möchtest", 1));
			
					for(Player all : Bukkit.getOnlinePlayers()) {
						if(!all.hasPermission("rang.special")) {
							p.hidePlayer(all);
						}
					}
				}
				
				if(e.getItem().getItemMeta().getDisplayName().equals("§5Nur VIPs sichtbar §7<Rechtsklick>")) {
					
					LobbyProfile.getPlayerProfiles().get(p.getUniqueId().toString()).setPlayerhiderstate(0);
					p.getInventory().setItem(1,	ItemHandler.getDye("§7Keine Spieler sichtbar §7<Rechtsklick>", DyeColor.GRAY, Li.getLoreTr() + "§7Verstecke die Spieler die\n§7du nicht sehen möchtest", 1));
					
					for(Player all : Bukkit.getOnlinePlayers()) {
						p.hidePlayer(all);
					}
					

				}
				
				if(e.getItem().getItemMeta().getDisplayName().equals("§7Keine Spieler sichtbar §7<Rechtsklick>")) {

					if(Li.isSilent()) {
						p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);	
					} else {
					
					LobbyProfile.getPlayerProfiles().get(p.getUniqueId().toString()).setPlayerhiderstate(1);
					p.getInventory().setItem(1,	ItemHandler.getDye("§aAlle Spieler sichtbar §7<Rechtsklick>", DyeColor.LIME, Li.getLoreTr() + "§7Verstecke die Spieler die\n§7du nicht sehen möchtest", 1));
			
					for(Player all : Bukkit.getOnlinePlayers()) {
						p.showPlayer(all);
					}
					}
				}
				
	
				
				if(e.getItem().getItemMeta().getDisplayName().equals("§cSilentlobby §7<Rechtsklick>")) {
		        	  ArrayList<ServerInfo> list = new ArrayList<>(CloudAPI.getInstance().getServers("Silentlobby"));
		        	  
		        	  if(list.size() != 0) {
		        		  
		        		  Random r = new Random();
		        		  int i = r.nextInt(list.size());
		        		  
		        		  ServerInfo si = list.get(i);
		        		  Methods.connectPlayer(p, si.getServiceId().getServerId());
		        		  
		        	  } else {
		        		  p.sendMessage(Li.getPrefix() + "§cEs ist keine Silentlobby online..");
		        	  }
		        	  
		        	  p.updateInventory();
					
				}
				
				if(e.getItem().getItemMeta().getDisplayName().equals("§dInventar §7<Rechtsklick>")) {
					p.sendMessage(Li.getPrefix() + "§7Diese Funktion kommt noch...");
				}
				
				
				
				
				if(e.getItem().getItemMeta().getDisplayName().equals("§cAutomatischer Nickname §7<Rechtsklick>")) {
					LobbyProfile.getPlayerProfiles().get(p.getUniqueId().toString()).setNickstate(1);
					p.getInventory().setItem(6, ItemHandler.getMaterial(Material.NAME_TAG, "§aAutomatischer Nickname §7<Rechtsklick>", Li.getLoreTr() + "§7Verstecke deine Identität in Spielen"));
					
				}
				
				if(e.getItem().getItemMeta().getDisplayName().equals("§aAutomatischer Nickname §7<Rechtsklick>")) {
					LobbyProfile.getPlayerProfiles().get(p.getUniqueId().toString()).setNickstate(0);
					p.getInventory().setItem(6, ItemHandler.getMaterial(Material.NAME_TAG, "§cAutomatischer Nickname §7<Rechtsklick>", Li.getLoreTr() + "§7Verstecke deine Identität in Spielen"));
				}
				
				
		
				
				if(e.getItem().getItemMeta().getDisplayName().equals("§bLobby wechseln §7<Rechtsklick>")) {
					LobbySwitcher.openLobbyInventory(p);
					
				}
				
				if(e.getItem().getItemMeta().getDisplayName().equals("§aProfil §7<Rechtsklick>")) {
					ProfileInventory.openProfile(p);
					
				}
			}
		
		}
		
	}
	
	
	@EventHandler
	public void onEntityInteractE(PlayerInteractAtEntityEvent e) {
		
		if(e.getRightClicked().getType().equals(EntityType.ITEM_FRAME)) {
			e.setCancelled(true);
		}
	}
	
	

}
