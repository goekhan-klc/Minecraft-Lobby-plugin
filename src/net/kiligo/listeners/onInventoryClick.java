package net.kiligo.listeners;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class onInventoryClick implements Listener {
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		e.setCancelled(true);
	}

}
