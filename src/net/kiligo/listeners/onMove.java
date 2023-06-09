package net.kiligo.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.kiligo.libary.FakePlayer;

public class onMove implements Listener {
	
	@EventHandler
	public void onMoveE(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		if(FakePlayer.getNpcPlayer().get(p) != null) { 
		for(FakePlayer fp : FakePlayer.getNpcPlayer().get(p)) {
			if(fp.getLoc().distance(p.getLocation()) > 40) {
				if(fp.isSpawned()) fp.despawn(p);
			}
			
			if(fp.getLoc().distance(p.getLocation()) < 40) {
				if(!fp.isSpawned()) fp.spawn(p);
				
				fp.setHeadLoc(fp.getLoc());
			}
			
			}
		}
	}

}
