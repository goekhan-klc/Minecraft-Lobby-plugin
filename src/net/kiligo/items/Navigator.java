package net.kiligo.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import net.kiligo.libary.ItemHandler;
import net.kiligo.libary.Li;
import net.kiligo.libary.Methods;
import net.kiligo.libary.NavigatorItem;

public class Navigator implements Listener {
	
	
	@EventHandler
	public void onClickE(InventoryClickEvent e) {
		
		if(e.getClickedInventory() != null) {
			if(e.getInventory().getTitle().equals("§cNavigator")) {
				if(e.getWhoClicked() instanceof Player) {
					if(e.getCurrentItem() != null) {
						e.setCancelled(true);
						Player p = (Player) e.getWhoClicked();
						
						for(NavigatorItem ni : NavigatorItem.getItems()) {
							if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ni.getDisplayname())) {
								p.closeInventory();
								p.teleport(ni.getLoc());
								
							}
							
						}
					}
				}
			}
		}
		
	}
	
	public static void loadNavigator() {
		
		for(String spawn : Li.getConfigcfg().getStringList("Names")) {
			@SuppressWarnings("deprecation")
			NavigatorItem item = new NavigatorItem(spawn, Li.getConfigcfg().getString("Spawns." + spawn + ".Displayname").replaceAll("&", "§").replaceAll("_", " ")
					, Material.getMaterial(Integer.valueOf(Li.getConfigcfg().getInt("Spawns." + spawn + ".Item"))), Integer.valueOf(Li.getConfigcfg().getString("Spawns." + spawn + ".Slot"))
					, Li.getLoreTr() + Li.getConfigcfg().getString("Spawns." + spawn + ".Lore").replaceAll("&", "§"), Methods.getConfigOnlyLocation("Spawns", spawn));
			
			NavigatorItem.getItems().add(item);
		}
		
	}

	
	public static void openNavigatorInventory(Player p) {
		 Inventory inv = Bukkit.createInventory(null, 9*4, "§cNavigator");
		 
		 
		 for(NavigatorItem item : NavigatorItem.getItems()) {
			 inv.setItem(item.getSlot(), ItemHandler.getMaterial(item.getItem(), item.getDisplayname(), item.getLore()));
		 }
		 
		 p.openInventory(inv);
		p.updateInventory();
		
	}
}
