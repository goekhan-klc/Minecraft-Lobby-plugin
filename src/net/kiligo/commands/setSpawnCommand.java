package net.kiligo.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.kiligo.items.Navigator;
import net.kiligo.libary.Li;
import net.kiligo.libary.Methods;

public class setSpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// /setspawn name displayname item slot
		
		if(sender instanceof Player) {
			final Player p = (Player) sender;
			
			if(p.hasPermission("rang.admin")) {
				if(args.length == 4) {
					
					final Location loc = p.getLocation();
					final String name = args[0];
					final String displayname = args[1];
					final int item = Integer.valueOf(args[2]);
					final String slot = args[3];
					
					Methods.createConfigLocation(loc, "Spawns", name, displayname, item, slot, Li.getConfig(), Li.getConfigcfg());
					p.sendMessage(Li.getPrefix() + "§7Der Spawn mit dem Namen " + displayname.replaceAll("&", "§") + " §7wurde erstellt!");
					p.sendMessage(Li.getPrefix() + "§e§lBitte fülle die Lore selber in der Config aus!");
					Navigator.loadNavigator();
					
				} else {
					p.sendMessage(Li.getPrefix() + "§cFalsche Benutzung! §7/setspawn <name> <displayname> <item> <slot>");
				}
			
			} else {
				p.sendMessage(Li.getPrefix() + "§cDu hast keine Rechte diesen Befehl zu benutzen");
			}
		}
		
		
		return false;
	}




}
