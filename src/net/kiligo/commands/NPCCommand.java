package net.kiligo.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.kiligo.libary.Li;
import net.kiligo.libary.Methods;

public class NPCCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// /setspawn name displayname item slot
		
		if(sender instanceof Player) {
			final Player p = (Player) sender;
			
			if(p.hasPermission("rang.admin")) {
				if(args.length == 2) {
					
					if(args[1].length() > 16) {
						p.sendMessage(Li.getPrefix() + "§cDer Displayname ist zu lang");
						return true;
					}
					
					final Location loc = p.getLocation();
					final String name = args[0];
					final String displayname = args[1];
					
					Methods.createConfigLocationNPC(loc, "FakePlayers", name, displayname, Li.getConfig(), Li.getConfigcfg());
					p.sendMessage(Li.getPrefix() + "§7Der NPC mit dem Namen " + displayname.replaceAll("&", "§").replaceAll("_", " ") + " §7wurde erstellt!");
					
				} else {
					p.sendMessage(Li.getPrefix() + "§cFalsche Benutzung! §7/addnpc <name> <displayname>");
					return true;
				}
			
			} else {
				p.sendMessage(Li.getPrefix() + "§cDu hast keine Rechte diesen Befehl zu benutzen");
				return true;
			}
		}
		
		
		return false;
	}

}
