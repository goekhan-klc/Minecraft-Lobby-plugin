package net.kiligo.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.Main;
import org.bukkit.entity.Player;

import de.dytanic.cloudnet.api.CloudAPI;
import net.kiligo.data.PermsData;
import net.kiligo.libary.Li;

public class RangCommand implements CommandExecutor {



	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player p = (Player) sender;
		
		/* 
		 * 5 - /rang create <Name> <Color> <Prefix> <Permlevel>
		 * 2 - /rang delete <Name>
		 * 4 - /rang set <Spieler> <Name> <Dauer>
		 * 3 - /rang setcolor <Name> <Farbe>
		 * 3 - /rang setprefix <Name> <Prefix>
		 * 3 - /rang setpermlevel <Name> <Level>
		 * 1 - /rang list
		 * 
		 */
		
		if(PermsData.getPermLevel(p) >= 8 || p.getName().equals("Kiligo_")) {
			
			if(args.length == 5) {
				if(args[0].equalsIgnoreCase("create")) {
					String name = args[1];
					String color = args[2];
					String prefix = args[3];
					int permlevel = Integer.parseInt(args[4]);
					
					if(!PermsData.isRankExists(name)) {
						PermsData.createRank(name, color, prefix, permlevel);
						p.sendMessage(Li.getRankPr() + "§7Der Rang §b" + name + " §7wurde erfolgreich erstellt!");
						p.sendMessage(Li.getRankPr() + "§7Farbe/Prefix: " + ChatColor.translateAlternateColorCodes('&', color) + prefix);
						p.sendMessage(Li.getRankPr() + "§7Permissionlevel: §e" + permlevel);
					} else {
						p.sendMessage(Li.getRankPr() + "§cDieser Rang exestiert schon!");
						return true;
					}
				} else {
					sendHelpPage(p);
				}
			} else if(args.length == 2) {
				if(PermsData.getPermLevel(p) >= 11 || p.getName().equals("Kiligo_")) {
				if(args[0].equalsIgnoreCase("delete")) {
					String name = args[1];
					
					if(PermsData.isRankExists(name)) {
						PermsData.deleteRank(name);
						p.sendMessage(Li.getRankPr() + "§7Der Rang §b" + name + " §7wurde erfolgreich gelöscht!");
					} else {
						p.sendMessage(Li.getRankPr() + "§cDieser Rang exestiert nicht!");
						return true;
					}
				} else {
					sendHelpPage(p);
				}
				} else {
					p.sendMessage("§cInsufficient Permissions");
				}
			} else if(args.length == 4) {
				if(args[0].equalsIgnoreCase("set")) {
					String player = args[1];
					String rang = args[2];
					String dauer = args[3];
					
					if(PermsData.isUserExist(player)) {
						if(PermsData.isRankExists(rang)) {
							if(PermsData.getPermLevel(p) > PermsData.getRankPermLevel(rang) && PermsData.getPermLevel(p) > PermsData.getPermLevelOffline(player) || p.getName().equals("Kiligo_")) {
							rang = PermsData.getRankNameNoColor(rang);
							
							if(dauer.equalsIgnoreCase("permanent")) {
								PermsData.setRank(player, rang, "-1");
								p.sendMessage(Li.getRankPr() + "§7Du hast " + PermsData.getPlayerName(player) + " §7den Rang " + ChatColor.translateAlternateColorCodes('&', PermsData.getRankColor(rang)) + rang + " §4PERMANENT §7gesetzt!");
								
								CloudAPI.getInstance().getOnlinePlayer(CloudAPI.getInstance().getOfflinePlayer(player).getUniqueId()
										).getPlayerExecutor().sendMessage(CloudAPI.getInstance().getOnlinePlayer(CloudAPI.getInstance().getOfflinePlayer(player).getUniqueId()), Li.getRankPr() + "&7Du hast gerade den Rang " + rang + " &7für §a" + "Permanent" + " §7erhalten");
								
							} else {
								long tage;
								try {
									tage = Integer.parseInt(dauer);
								} catch(NumberFormatException e) {
									p.sendMessage(Li.getRankPr() + "§cBitte gebe eine Dauer in Tagen ein!");
									return true;
								}
								dauer = String.valueOf(System.currentTimeMillis() + (tage*1000*60*60*24));
								
								PermsData.setRank(player, rang, dauer);
								p.sendMessage(Li.getRankPr() + "§7Du hast " + PermsData.getPlayerName(player) + " §7den Rang " + ChatColor.translateAlternateColorCodes('&', PermsData.getRankColor(rang)) + rang + " §7für §c" + tage + " §cTage §7gesetzt!");
								
								CloudAPI.getInstance().getOnlinePlayer(CloudAPI.getInstance().getOfflinePlayer(player).getUniqueId())
								.getPlayerExecutor().sendMessage(CloudAPI.getInstance().getOnlinePlayer(CloudAPI.getInstance().getOfflinePlayer(player).getUniqueId())
									, Li.getRankPr() + "&7Du hast gerade den Rang " + rang + " &7für §a" + dauer + " §7erhalten");
								}
							
							} else {
								p.sendMessage(Li.getRankPr() + "§cDieser Rang hat einen höheren Permission-Level als du!");
								return true;
							}
						} else {
							p.sendMessage(Li.getRankPr() + "§cDieser Rang exestiert nicht!");
							return true;
						}
					} else {
						if(PermsData.isRankExists(rang)) {
							if(PermsData.getPermLevel(p) > PermsData.getRankPermLevel(rang) || p.getName().equals("Kiligo_")) {
							if(dauer.equalsIgnoreCase("permanent")) {
								PermsData.createUserRegisterOffline(player);
								p.sendMessage(Li.getRankPr() + "§7Du hast " + PermsData.getPlayerName(player) + " §7den Rang " + ChatColor.translateAlternateColorCodes('&', PermsData.getRankColor(rang)) + rang + " §4PERMANENT §7gesetzt!");
								p.sendMessage(Li.getRankPr() + "§cAchtung: §7Dieser Spieler war noch nie online! Er bekommt seinen Rang, wenn er das erste mal online kommt!");
							} else {
								p.sendMessage(Li.getRankPr() + "§cDieser Spieler ist nicht regestriert! §7Du kannst ihm nur permanente Ränge setzten!");
								return true;
							}
							} else {
								p.sendMessage(Li.getRankPr() + "§cDieser Rang hat einen höheren Permission-Level als du!");
								return true;
							}
						}
					}
					
				} else {
					sendHelpPage(p);
				}
			} else if(args.length == 3) {
				if(PermsData.getPermLevel(p) >= 11 || p.getName().equals("Kiligo_")) {
				if(args[0].equalsIgnoreCase("setcolor")) {
					String rang = args[1];
					String color = args[2];
					
					if(PermsData.isRankExists(rang)) {
						PermsData.setRankColor(rang, color);
						p.sendMessage(Li.getRankPr() + "§7Du hast die Farbe vom Rang " + PermsData.getRankName(rang) + " §7geändert!");
					} else {
						p.sendMessage(Li.getRankPr() + "§cDieser Rang exestiert nicht!");
						return true;
					}
				} else if(args[0].equalsIgnoreCase("setprefix")) {
					String rang = args[1];
					String prefix = args[2];
					
					if(PermsData.isRankExists(rang)) {
						PermsData.setRankPrefix(rang, prefix);
						p.sendMessage(Li.getRankPr() + "§7Du hast die Prefix vom Rang " + PermsData.getRankPrefix(rang) + " §7geändert!");
					} else {
						p.sendMessage(Li.getRankPr() + "§cDieser Rang exestiert nicht!");
						return true;
					}
				} else if(args[0].equalsIgnoreCase("setpermlevel")) {
					String rang = args[1];
					int level = Integer.parseInt(args[2]);
					
					if(PermsData.isRankExists(rang)) {
						PermsData.setRankPermLevel(rang, level);
						p.sendMessage(Li.getRankPr() + "§7Du hast den Permission-Level vom Rang " + PermsData.getRankName(rang) + " §7zu §e" + level + " §7geändert!");
					} else {
						p.sendMessage(Li.getRankPr() + "§cDieser Rang exestiert nicht!");
						return true;
					}
				} else {
					sendHelpPage(p);
				}
				} else {
					p.sendMessage("§cInsufficient Permissions");
				}
			} else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("list")) {
					p.sendMessage(Li.getRankPr() + "§7Verfügbare Ränge:");
					
					for(String s : PermsData.getAllRanks()) {
						p.sendMessage("§8- " + ChatColor.translateAlternateColorCodes('&', s) + " §7(§e" + PermsData.getRankPermLevel((s.substring(2, s.length()))) + "§7)");
					}
					p.sendMessage("");
				}
			} else {
				sendHelpPage(p);
			}
			
			
		} else {
			p.sendMessage("§cInsufficient Permissions");
		}
		
		return false;
	}

	@SuppressWarnings("deprecation")
	public void sendHelpPage(Player p) {	
		p.sendMessage("");
		p.sendMessage(Li.getRankPr() + "§cRangSystem Verwaltung:");
		p.sendMessage(Li.getRankPr() + "§7/rang create <Name> <Farbe> <Prefix> <PermissionLevel>");
		p.sendMessage(Li.getRankPr() + "§7/rang delete <Name>");
		p.sendMessage(Li.getRankPr() + "§7/rang set <Spieler> <Name> <Dauer>");
		p.sendMessage(Li.getRankPr() + "§7/rang setcolor <Name> <Farbe>");
		p.sendMessage(Li.getRankPr() + "§7/rang setprefix <Name> <Prefix>");
		p.sendMessage(Li.getRankPr() + "§7/rang setpermlevel <Name> <PermissionLevel>");
		p.sendMessage(Li.getRankPr() + "§7/rang list");
		p.sendMessage("");
		
	}
	
}
