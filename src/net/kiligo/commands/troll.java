package net.kiligo.commands;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class troll implements CommandExecutor {
	
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		ArrayList<Player> nicked = new ArrayList<>();
	
		if(!nicked.contains(p)) {
			nicked.add(p);
			
			CraftPlayer cp = (CraftPlayer)p;
			PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
			connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, cp.getHandle()));
			cp.setPlayerListName("§7GommeHD");
			cp.setDisplayName("§7GommeHD");
			
			
			for(Player pp : Bukkit.getOnlinePlayers()) {
				CraftPlayer cpp = (CraftPlayer)pp;
				PlayerConnection connection1 = ((CraftPlayer)cpp).getHandle().playerConnection;
				connection1.sendPacket(new  PacketPlayOutEntityDestroy(cp.getEntityId()));
				
			}
			
			
	        GameProfile gp = cp.getHandle().getProfile();
	        
	        gp.getProperties().put("textures", new Property("textures", "eyJ0aW1lc3RhbXAiOjE1NzE5MzIwODA5OTMsInByb2ZpbGVJZCI6ImU5MDEzYzJmZGEwMTQyNWZhNDhiNTE2ZjU1ZTk0Mzg2IiwicHJvZmlsZU5hbWUiOiJHb21tZUhEIiwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2Y2OTMyY2VjOTRjZjA5NGZmODM2YmU0ZjViMjQzMWJlNWRhMDE2YmE4OWZkMmFjM2FhMjJlOTNhZTRmZTU3NGUifSwiQ0FQRSI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2U3ZGZlYTE2ZGM4M2M5N2RmMDFhMTJmYWJiZDEyMTYzNTljMGNkMGVhNDJmOTk5OWI2ZTk3YzU4NDk2M2U5ODAifX19"));
	        p.sendMessage( cp.getHandle().getProfile().getProperties().get("textures").toString());
	        
	        EntityHuman eh = ((CraftPlayer) p).getHandle();
	        
	        try {

	        	  Field profileField = eh.getClass().getSuperclass().getDeclaredField("bH");
	        	  profileField.setAccessible(true);

	        	  profileField.set(eh, new GameProfile(UUID.fromString("GommeHD"), "GommeHD"));

	        	} catch (Exception e) {
	        	  e.printStackTrace();
	        	}

	        
            Field ff;
			try {
				ff = gp.getClass().getDeclaredField("name");
	            ff.setAccessible(true);
				
	            ff.set(gp, "GommeHD");
	        	} catch (Exception e) {
		        	  e.printStackTrace();
		        	}
	            
	            
			connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, cp.getHandle()));
		
			ArrayList<Player> online = new ArrayList<>(Bukkit.getOnlinePlayers());
			online.remove(p);
			
			for(Player pp : online) {
				CraftPlayer cpp = (CraftPlayer)pp;
				PlayerConnection connection1 = ((CraftPlayer)cpp).getHandle().playerConnection;
				connection1.sendPacket(new PacketPlayOutNamedEntitySpawn(cp.getHandle()));
				
			}

			
		} else {
			nicked.remove(p);
			
			
		}
		

		
		return false;
	}
	

}
