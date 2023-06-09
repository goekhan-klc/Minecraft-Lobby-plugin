package net.kiligo.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.kiligo.data.LobbyProfile;
import net.kiligo.data.LocationData;

public class onQuit implements Listener {
	
	@EventHandler
	public void onQuitE(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(null);
		
		LocationData.setLocation(p);
		LobbyProfile.getPlayerProfiles().get(p.getUniqueId().toString()).saveData();
		//Nicht Ressourcen schonend. Kann aber nicht rechtzeitig speichern wenns nicht direkt gemacht wird.
		
		LobbyProfile.getPlayerProfiles().remove(p);
	}
}
