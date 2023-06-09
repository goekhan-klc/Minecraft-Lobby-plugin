package net.kiligo.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldProtectors implements Listener {

	@EventHandler
	public void onWeather(WeatherChangeEvent e) {
		if(e.toWeatherState()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPortal(PlayerPortalEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
		
		if(e.getEntityType().equals(EntityType.VILLAGER)) {
			return;
		}
		
        if(e.getEntityType() != EntityType.ITEM_FRAME || e.getEntityType() != EntityType.ARMOR_STAND || e.getEntityType() != EntityType.VILLAGER) { 
            e.setCancelled(true);
        }
	} 
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		e.setCancelled(true);
	}
	
	
	@EventHandler 
	public void onPlayer(FoodLevelChangeEvent e) {
		e.setCancelled(true);
		
	}
	
	@EventHandler
	public void on(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void on(EntityDamageEvent e) {

		if(e.getEntityType() == EntityType.PLAYER) {
			e.setCancelled(true);
		}
		
		if(e.getEntityType() == EntityType.VILLAGER) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlock(BlockPlaceEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler 
	public void on(PlayerAchievementAwardedEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onBlock(BlockBreakEvent e) {
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockFromTo(BlockFromToEvent e) {
	  Material m = e.getBlock().getType();
	  
	  if(m.equals(Material.WATER) || m.equals(Material.STATIONARY_WATER) || m.equals(Material.SIGN) || m.equals(Material.CARPET)) {
	    e.setCancelled(true);
	  }
	}
	
	
}
