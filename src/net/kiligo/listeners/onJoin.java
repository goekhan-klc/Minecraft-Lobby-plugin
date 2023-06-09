package net.kiligo.listeners;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.kiligo.data.LobbyItemData;
import net.kiligo.data.LobbyProfile;
import net.kiligo.data.LocationData;
import net.kiligo.libary.FakePlayer;
import net.kiligo.libary.ItemHandler;
import net.kiligo.libary.Li;
import net.kiligo.libary.Scoreboard;
import net.kiligo.lobby.Lobby;
import net.kiligo.lobby.SQLManager;

public class onJoin implements Listener {
	
	
	@EventHandler
	public void onJoinE(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		e.setJoinMessage(null);
		
		//p.teleport(Li.getSpawnLocation());		
		p.getInventory().clear();
		
		if(Li.isSilent()) {
			for(Player others : Bukkit.getOnlinePlayers()) {
				p.hidePlayer(others);
				others.hidePlayer(p);
			}
			p.sendMessage(Li.getPrefix() + "§eHier bist du in der Silentlobby. Du bist nun vollkommen ungestört!");
		}
		
		if(!SQLManager.isUserExists(p.getUniqueId().toString(), "lobbyitems")) {
			LobbyItemData.createUserData(p.getName(), p.getUniqueId().toString());
		}
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		es.execute(() -> {
			Bukkit.getScheduler().runTask(Lobby.getLobby(), () -> {
				for(LobbyProfile lp : LobbyProfile.getPlayerProfiles().values()) {
					
					if(lp.getPlayerhiderstate()==0) {
						Bukkit.getPlayer(lp.getPlayername()).hidePlayer(p);
					} else if(lp.getPlayerhiderstate()==2) {
						if(!p.hasPermission("rang.special")) {
							Bukkit.getPlayer(lp.getPlayername()).hidePlayer(p);
						}
					}
			}
				
			});
		});
		
		es.execute(() -> {
			Bukkit.getScheduler().runTaskLater(Lobby.getLobby(), () -> {
					giveItems(p);

			},2);
		}); 
		
		Scoreboard.setScoreboard(p);
		
		FakePlayer.getNpcPlayer().put(p, new ArrayList<FakePlayer>());
		spawnNPCs(p);
		
		PacketReader pr = new PacketReader(p);
		pr.inject();
		
		
		es.execute(() -> {
			Bukkit.getScheduler().runTaskLater(Lobby.getLobby(), () -> {
				p.teleport(LocationData.getLocation(p));
				//p.teleport(new Location(Bukkit.getWorld("world"),(double) 100, (double) 100, (double) 100));
			}, 40);
		});
		
		es.shutdown();
	}
	
	
	
	
	public static void giveItems(Player p) {
		p.getInventory().setItem(0, ItemHandler.getMaterial(Material.COMPASS, "§cNavigator §7<Rechtsklick>", Li.getLoreTr() + "§7Teleportiere dich zu besonderen\n§7Punkten oder steige in Modis ein"));
		p.getInventory().setItem(4, ItemHandler.getMaterial(Material.CHEST, "§dInventar §7<Rechtsklick>", Li.getLoreTr() + "§7Zeige deine Gadgets & Cosmetics"));
		p.getInventory().setItem(7, ItemHandler.getMaterial(Material.NETHER_STAR, "§bLobby wechseln §7<Rechtsklick>", Li.getLoreTr() + "§7Wechsel auf einen anderen Lobbyserver"));
		p.getInventory().setItem(8, ItemHandler.getHead(p.getName(), "§aProfil §7<Rechtsklick>", Li.getLoreTr() + "§7Zeige deine Freunde & weiteres an", 1));
		
		if(p.hasPermission("rang.vip") && !Li.isSilent()) {
			p.getInventory().setItem(2, ItemHandler.getMaterial(Material.TNT, "§cSilentlobby §7<Rechtsklick>", Li.getLoreTr() + "§7Verschwinde in eine isolierte Lobby"));
		}
		
		p.getInventory().setItem(1,	ItemHandler.getDye("§aAlle Spieler sichtbar §7<Rechtsklick>", DyeColor.LIME, Li.getLoreTr() + "§7Verstecke die Spieler die\n§7du nicht sehen möchtest", 1));
		if(p.hasPermission("rang.special")) p.getInventory().setItem(6, ItemHandler.getMaterial(Material.NAME_TAG, "§cAutomatischer Nickname §7<Rechtsklick>", Li.getLoreTr() + "§7Verstecke deine Identität in Spielen"));
		
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		es.execute(() -> {
			
			Bukkit.getScheduler().runTaskLater(Lobby.getLobby(), () -> {

				LobbyProfile lp = new LobbyProfile(p.getName(), p.getUniqueId().toString());
				lp.getDataStacks();
				
				if(!Li.isSilent()) {
					int i = LobbyProfile.getPlayerProfiles().get(p.getUniqueId().toString()).getPlayerhiderstate();
					
					
					if(i==0) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							p.hidePlayer(all);
						}
					} else if(i==2) {
						for(Player all : Bukkit.getOnlinePlayers()) {
							if(!all.hasPermission("rang.special")) {
								p.hidePlayer(all);
							}
						}
					} else {
						for(Player all : Bukkit.getOnlinePlayers()) {
							p.showPlayer(all);
						}
					}
					
					if(i == 2) {
						p.getInventory().setItem(1,	ItemHandler.getDye("§5Nur VIPs sichtbar §7<Rechtsklick>", DyeColor.PURPLE, Li.getLoreTr() + "§7Verstecke die Spieler die\n§7du nicht sehen möchtest", 1));
					} else if(i == 1) {
						p.getInventory().setItem(1,	ItemHandler.getDye("§aAlle Spieler sichtbar §7<Rechtsklick>", DyeColor.LIME, Li.getLoreTr() + "§7Verstecke die Spieler die\n§7du nicht sehen möchtest", 1));
					} else {
						p.getInventory().setItem(1,	ItemHandler.getDye("§7Keine Spieler sichtbar §7<Rechtsklick>", DyeColor.GRAY, Li.getLoreTr() + "§7Verstecke die Spieler die\n§7du nicht sehen möchtest", 1));
					}
				} else {
					p.getInventory().setItem(1,	ItemHandler.getDye("§7Keine Spieler sichtbar §7<Rechtsklick>", DyeColor.GRAY, Li.getLoreTr() + "§7Verstecke die Spieler die\n§7du nicht sehen möchtest", 1));
				}
				
				if(p.hasPermission("rang.special")) {
					if(LobbyProfile.getPlayerProfiles().get(p.getUniqueId().toString()).getNickstate() == 0) {
						p.getInventory().setItem(6, ItemHandler.getMaterial(Material.NAME_TAG, "§cAutomatischer Nickname §7<Rechtsklick>", Li.getLoreTr() + "§7Verstecke deine Identität in Spielen"));
					} else {
						p.getInventory().setItem(6, ItemHandler.getMaterial(Material.NAME_TAG, "§aAutomatischer Nickname §7<Rechtsklick>", Li.getLoreTr() + "§7Verstecke deine Identität in Spielen"));
					}
				}
				
			}, 8);
			
		});
	
		es.shutdown();
	}
	
	private void spawnNPCs(Player p) {
		ExecutorService executor = Executors.newCachedThreadPool();
		
		executor.execute(() -> {
			Bukkit.getScheduler().runTask(Lobby.getLobby(), () -> {
				
				for(FakePlayer fp : FakePlayer.getNpcsToSpawn()) {
					fp.spawn(p);
				}
				
			});
		});
		
		
	}
	

}
