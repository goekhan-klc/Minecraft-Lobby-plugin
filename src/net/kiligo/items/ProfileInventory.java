package net.kiligo.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.kiligo.data.PermsData;
import net.kiligo.libary.ItemHandler;
import net.kiligo.libary.Li;



public class ProfileInventory {
	
	public static HashMap<Inventory, String> currentInv = new HashMap<>();
	public static HashMap<Player, Integer> friendSite = new HashMap<>();
	public static HashMap<String, ItemStack> headBase = new HashMap<>();
	
	public static void openProfile(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9*6, "§4Dein Profil");
		setHeader(p, inv);
		
		p.openInventory(inv);
		
		inv.setItem(22, ItemHandler.getHead(p.getName(), "§aAllgemeine Informationen", Li.getLoreTr() + "\n§fRang: " + PermsData.getRankName(PermsData.getPlayerRank(p.getName())) + "\n"
				+ "§fDauer deines Ranges: §e" + getRankDauer(p) + "\n" + Li.getLoreTr() + "\n§7Klicke für einen Link zum Shop", 1));
	
		inv.setItem(23, ItemHandler.getMaterial(Material.PAPER, "§6Statistiken", Li.getLoreTr() + "\n§fKlicke um deine Statistiken anzusehen\n" + Li.getLoreTr()));
			
		//inv.setItem(30, ItemHandler.getMaterial(Material.GOLD_NUGGET, "§bLevel", Li.getLoreTr() + "\n§fDurch vieles spielen und erforschen bekommst\n§fdu mit der Zeit §eXP§f.\n§fWenn du §e100 XP §fhast steigst du"
		//	+ " §eein\n§fLevel auf - und das §eimmer§f. \n\n"
		//	+ "§bGlobales Level " + getMySQLLevel.getLevelColored(p.getName()) + " " + levelbar(p) + "\n\n"
		//			+ "§fMomentane XP: §b" + getMySQLLevel.getXP(p.getName()) + "/100\n" + Li.getLoreTr()));
		
		inv.setItem(31, ItemHandler.getMaterial(Material.REDSTONE_COMPARATOR, "§cEinstellungen",  "\n§fKlicke um deine Einstellungen für das\n§fgesamte Netzwerk einzustellen\n"));
		
		inv.setItem(40, ItemHandler.getMaterial(Material.POTION, "§bXP & Coin Booster",  "\n§fKlicke um deine Booster anzusehen und\n§fzu verwalten\n"));
		
		p.updateInventory();
	}

	public static void openFreunde(Player p, int seite) {
		Inventory inv = Bukkit.createInventory(null, 9*6, "§aFreunde");
	setHeader(p, inv);
	
	p.openInventory(inv);
	//
	//if(seite == 1) {
	//	
	//	String msg = getMySQLFreunde.getMSG(p.getName());
	//	String jump = getMySQLFreunde.getJump(p.getName());
	//	String anfragen = getMySQLFreunde.getSettingAnfragen(p.getName());
	//	
	//	if(msg.equals("1")) {
	//		inv.setItem(50, net.kiligo.libary.ItemHandler.getClay("§bMSG's von allen zulassen", 13, 1, null));
	//	} else if(msg.equals("2")) {
	//	inv.setItem(50, ItemHandler.getClay("§bMSG's nur von Freunden zulassen", 1, 1, null));
	//} else {
	//		inv.setItem(50, ItemHandler.getClay("§bMSG's von niemanden zulassen", 14, 1, null));
	//}
			
	//		if(jump.equals("1")) {
	//		inv.setItem(49, ItemHandler.getClay("§bFreunde können dir nachspringen", 13, 1, null));
	//	} else {
	//		inv.setItem(49, ItemHandler.getClay("§bNiemand kann dir nachspringen", 14, 1, null));
	//	}
	//	
	//	if(anfragen.equals("1")) {
	//		inv.setItem(48, ItemHandler.getClay("§bMan kann dir Anfragen schicken", 13, 1, null));
	//	} else {
	//		inv.setItem(48, ItemHandler.getClay("§bMan kann dir keine Anfragen schicken", 14, 1, null));
	//	}
	//	
	//	if(PermsData.getPermLevel(p) >= 1) {
	//		inv.setItem(46, ItemHandler.getMaterial(Material.NAME_TAG, "§6Status", Li.getLoreTr() + "\n§fDein aktueller Status lautet:\n§b" + getMySQLFreunde.getStatus(p.getName()) + "\n" + Li.getLoreTr()));
	//	} else {
	//		inv.setItem(46, ItemHandler.getMaterial(Material.NAME_TAG, "§6Status", Li.getLoreTr() + "\n§cDiese Funktion wird ab §6Premium\n§cfreigeschaltet\n" + Li.getLoreTr()));
	//	}
	//	
	//	inv.setItem(45, ItemHandler.getItem(Material.EMPTY_MAP, "§aFreundschaftsanfragen", Li.getLoreTr() + "\n§fDu hast momentan §b" + getMySQLFreunde.getAnfragen(p.getName()).size() + " §fAnfragen\n" + Li.getLoreTr(), 0, getMySQLFreunde.getAnfragen(p.getName()).size(), false));
	//
	//	
	//	ArrayList<ItemStack> items = new ArrayList<>();
	//	ArrayList<String> friends = getMySQLFreunde.getFreunde(p.getName());
			
			
	//		for(int i = 0; i < friends.size(); i++) {
	//		if(getMySQLPlayerinfo.getOnline(friends.get(i)).equals("1")) {
	//			if(PermsData.getPermLevelOffline(friends.get(i)) >= 1) {
	//				items.add(ItemHandler.getHead(friends.get(i), PermsData.getPlayerName(friends.get(i)), "§aOnline §7auf §b" + getMySQLPlayerinfo.getLastServer(friends.get(i)) + "\n\n§7Status:\n§6§o" + getMySQLFreunde.getStatus(friends.get(i)), 1));
	//			} else {
	//				items.add(ItemHandler.getHead(friends.get(i), PermsData.getPlayerName(friends.get(i)), "§aOnline §7auf §b" + getMySQLPlayerinfo.getLastServer(friends.get(i)), 1));	
	//			}
	//		}
	//	}
			
	//	for(int i = 0; i < friends.size(); i++) {
	//		if(getMySQLPlayerinfo.getOnline(friends.get(i)).equals("0")) {
	//			if(PermsData.getPermLevelOffline(friends.get(i)) >= 1) {
	//				items.add(ItemHandler.getMaterial(Material.SKULL_ITEM, PermsData.getPlayerName(friends.get(i)), "§7Offline (" + getMySQLFreunde.getLastOnline(friends.get(i)) + ")\n\n§7Status:\n§6§o" + getMySQLFreunde.getStatus(friends.get(i))));
	//			} else {
	//				items.add(ItemHandler.getMaterial(Material.SKULL_ITEM, PermsData.getPlayerName(friends.get(i)), "§7Offline (" + getMySQLFreunde.getLastOnline(friends.get(i)) + ")"));	
	//			}
	//		} 
	//	}
			
	//	boolean canswitch = false;
	//	int page = seite;
	//	int entriesPerPage = 27;
	//	int startIndex = (page - 1) * entriesPerPage;
	//	int endIndex = startIndex + entriesPerPage;
			
	//if(endIndex > items.size()) {
				//	   endIndex = items.size();
	//	   canswitch = true;
	//	}
	//
	//	List<ItemStack> toset = items.subList(startIndex, endIndex);
	//	int toseti = 0;
	//	
	//	for(int i = 18; i < 45; i++) {
	//		inv.setItem(i, toset.get(toseti));
	//		toseti++;
	//	}
	//	
	//		if(!canswitch) {
	//		inv.setItem(53, ItemHandler.getMaterial(Material.REDSTONE_TORCH_ON, "§cNächste Seite", null));
	//	}
	//	if(page != 1) {
	//		inv.setItem(52, ItemHandler.getMaterial(Material.REDSTONE_TORCH_ON, "§cLetzte Seite", null));
	//	}
			
			p.updateInventory();
			//} else {

			//	ArrayList<ItemStack> items = new ArrayList<>();
			//ArrayList<String> friends = getMySQLFreunde.getFreunde(p.getName());
			
			
			//for(int i = 0; i < friends.size(); i++) {
			//if(getMySQLPlayerinfo.getOnline(friends.get(i)).equals("1")) {
			//	if(PermsData.getPermLevelOffline(friends.get(i)) >= 1) {
			//		items.add(ItemHandler.getHead(friends.get(i), PermsData.getPlayerName(friends.get(i)), "§aOnline §7auf §b" + getMySQLPlayerinfo.getLastServer(friends.get(i)) + "\n\n§7Status:\n§6§o" + getMySQLFreunde.getStatus(friends.get(i)), 1));
			//	} else {
			//		items.add(ItemHandler.getHead(friends.get(i), PermsData.getPlayerName(friends.get(i)), "§aOnline §7auf §b" + getMySQLPlayerinfo.getLastServer(friends.get(i)), 1));	
			//	}
			//}
			//}
			
			//for(int i = 0; i < friends.size(); i++) {
			//if(getMySQLPlayerinfo.getOnline(friends.get(i)).equals("0")) {
			//	if(PermsData.getPermLevelOffline(friends.get(i)) >= 1) {
			//		items.add(ItemHandler.getMaterial(Material.SKULL_ITEM, PermsData.getPlayerName(friends.get(i)), "§7Offline (" + getMySQLFreunde.getLastOnline(friends.get(i)) + ")\n\n§7Status:\n§6§o" + getMySQLFreunde.getStatus(friends.get(i))));
			//	} else {
			//		items.add(ItemHandler.getMaterial(Material.SKULL_ITEM, PermsData.getPlayerName(friends.get(i)), "§7Offline (" + getMySQLFreunde.getLastOnline(friends.get(i)) + ")"));	
			//	}
			//} 
			//}
			
			//boolean canswitch = false;
			//int page = seite;
			//int entriesPerPage = 27;
			//int startIndex = (page - 1) * entriesPerPage;
			//int endIndex = startIndex + entriesPerPage;
			
			//if(endIndex > items.size()) {
			//// endIndex = items.size();
			//canswitch = true;
			//}

			//List<ItemStack> toset = items.subList(startIndex, endIndex);
			//	int toseti = 0;
			//
			//for(int i = 18; i < 45; i++) {
			//inv.setItem(i, toset.get(toseti));
			//toseti++;
			//}
			
			//if(!canswitch) {
			//	inv.setItem(53, ItemHandler.getMaterial(Material.REDSTONE_TORCH_ON, "§cNächste Seite", null));
			//}
			//if(page != 1) {
			//	inv.setItem(52, ItemHandler.getMaterial(Material.REDSTONE_TORCH_ON, "§cLetzte Seite", null));
			//}
			
			p.updateInventory();
			//}
	
	}
	
	public static void openParty(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9*6, "§5Party");
		setHeader(p, inv);
		
		p.openInventory(inv);
		
		inv.setItem(31, ItemHandler.getMaterial(Material.BARRIER, "§cNicht verfügbar..", null));
		
		p.updateInventory();
	}
	
	public static void openClan(Player p, int seite) {
		Inventory inv = Bukkit.createInventory(null, 9*6, "§cClan");
		setHeader(p, inv);
		
		p.openInventory(inv);
		
		//if(getMySQLClans.isInClan(p.getName())) {
			//inv.setItem(49, ItemHandler.getMaterial(Material.PAPER, "§e" + getMySQLClans.getName(getMySQLClans.getPlayerClan(p.getName())) + " §7[§b" + getMySQLClans.getTag(getMySQLClans.getPlayerClan(p.getName())) + "§7]", 
				//	Li.getLoreTr() + "\n§fName: §e" + getMySQLClans.getName(getMySQLClans.getPlayerClan(p.getName())) + "\n§fTag: §b" + getMySQLClans.getTag(getMySQLClans.getPlayerClan(p.getName())) +
					// "\n§fMitglieder: §e" + getMySQLClans.getMembers(getMySQLClans.getPlayerClan(p.getName())).size() + "\n" + Li.getLoreTr()));

			//inv.setItem(45, ItemHandler.getMaterial(Material.REDSTONE, "§4Clan verlassen", Li.getLoreTr() + "\n§7Verlasse deinen Clan!\n§7(§eMit Bestätigung§7)\n" + Li.getLoreTr()));
			
			
		//} else {
			//inv.setItem(31, ItemHandler.getMaterial(Material.BARRIER, "§cDu bist in keinem Clan", Li.getLoreTr() + "\n§fErstelel deinen eigenen Clan \n§fmit §e/clan create <Name> <Tag>\n" + Li.getLoreTr()));
		//}
		inv.setItem(31, ItemHandler.getMaterial(Material.BARRIER, "§cNicht verfügbar..", null));
	
		ArrayList<ItemStack> items = new ArrayList<>();
		//ArrayList<String> members = getMySQLClans.getMembers(getMySQLClans.getPlayerClan(p.getName()));
		
		
		//for(int i = 0; i < members.size(); i++) {
		//	if(getMySQLPlayerinfo.getOnline(members.get(i)).equals("1")) {
			//	items.add(ItemHandler.getHead(members.get(i), PermsData.getPlayerName(members.get(i)), "§aOnline §fauf §b" + getMySQLPlayerinfo.getLastServer(members.get(i))
			//	 + "\n\n§fRang: " + getMySQLClans.getRoleColored(members.get(i)), 1));
		//	}
	//	}
		
	//	for(int i = 0; i < members.size(); i++) {
		//	if(getMySQLPlayerinfo.getOnline(members.get(i)).equals("0")) {
			//	items.add(ItemHandler.getMaterial(Material.SKULL_ITEM, PermsData.getPlayerName(members.get(i)), "§7Offline\n\n§fRang: " + getMySQLClans.getRoleColored(members.get(i))));
			//} 
		//}
		
		boolean canswitch = true;
		int page = seite;
		int entriesPerPage = 27;
		int startIndex = (page - 1) * entriesPerPage;
		int endIndex = startIndex + entriesPerPage;
		
		if(endIndex > items.size()) {
		   endIndex = items.size();
		   canswitch = true;
		}

		List<ItemStack> toset = items.subList(startIndex, endIndex);
		int toseti = 0;
		
		for(int i = 18; i < 45; i++) {
			inv.setItem(i, toset.get(toseti));
			toseti++;
		}
		
		if(!canswitch) {
			inv.setItem(53, ItemHandler.getMaterial(Material.REDSTONE_TORCH_ON, "§cNächste Seite", null));
		}
		if(page != 1) {
			inv.setItem(52, ItemHandler.getMaterial(Material.REDSTONE_TORCH_ON, "§cLetzte Seite", null));
		}
		
		p.updateInventory();
		
	}
	
	public static void openReplays(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9*6, "§3Replays");
		setHeader(p, inv);
		
		p.openInventory(inv);
		
		//if(PermsData.getPermLevel(p) >= 4) {
			
			inv.setItem(31, ItemHandler.getMaterial(Material.BARRIER, "§cDaten konnten nicht abgerufen werden...", null));
			
		//} else {
		//	inv.setItem(31, ItemHandler.getMaterial(Material.BARRIER, "§cDu hast keine Rechte zu diesem Bereich", Li.getLoreTr() + "\n§fMomentan ist dieser Bereich nur\n§ffür Teamler zugängig\n" + Li.getLoreTr()));
		//}
		
		p.updateInventory();
	}
	
	public static void setHeader(Player p, Inventory inv) {
		ProfileInventory.registerHeads();
		
		inv.setItem(2, ItemHandler.getHead(p.getName(), "§4Profil", "\n§fZeige die wichtigsten informationen\n§fzu dir an\n", 1));
		inv.setItem(3, headBase.get("Freunde"));
		inv.setItem(4, headBase.get("Party"));
		inv.setItem(5, headBase.get("Clan"));
		inv.setItem(6, headBase.get("Replay"));
		
		inv.setItem(9, ItemHandler.getGlass("§d", 8, 1, null));
		inv.setItem(10, ItemHandler.getGlass("§d", 8, 1, null));
		inv.setItem(11, ItemHandler.getGlass("§d", 8, 1, null));
		inv.setItem(12, ItemHandler.getGlass("§d", 8, 1, null));
		inv.setItem(13, ItemHandler.getGlass("§d", 8, 1, null));
		inv.setItem(14, ItemHandler.getGlass("§d", 8, 1, null));
		inv.setItem(15, ItemHandler.getGlass("§d", 8, 1, null));
		inv.setItem(16, ItemHandler.getGlass("§d", 8, 1, null));
		inv.setItem(17, ItemHandler.getGlass("§d", 8, 1, null));
	}

	
	
	public static String getDurationBreakdown(long millis) {

		long days = TimeUnit.MILLISECONDS.toDays(millis);
		millis -= TimeUnit.DAYS.toMillis(days);
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

		return String.format("%dd %dh %dmin %dsec", days, hours, minutes, seconds);

	}
	
	public static String getRankDauer(Player p) {

		if(PermsData.getPlayerRankDauer(p.getName()) == -1) {
			return "§4permanent";
	} else {
			
			return getDurationBreakdown(PermsData.getPlayerRankDauer(p.getName()) - System.currentTimeMillis());
			
		}
		
	}

	
	public static String levelbar(Player p) {
		int gesamt = 50;
		//int level = getMySQLLevel.getXP(p.getName());
		int level = 2;
		String bar = "";
		
		for(int i = 0; i < level/2; i++) {
			bar += "§a|";
		}
		
		gesamt = gesamt - (level/2);
		
		for(int i = 0; i < gesamt; i++) {
			bar += "§7|";
		}
		
		return bar;
	}


	public static void registerHeads() {
		headBase.put("Freunde", ItemHandler.getSkullByValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2U0MzM2NTZiNDQzNjY4ZWQwM2RhYzhjNDQyNzIyYTJhNDEyMjFiZThiYjQ4ZTIzYjM1YmQ4YzJlNTlmNjMifX19", "§aFreunde", "\n§fZeige deine Freunde auf\n§fExtact.de an und interargiere\n§fmit ihnen\n" ));
		headBase.put("Party", ItemHandler.getSkullByValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjlkZTYwMWRlZTNmZmVjYTRkNTQ1OTVmODQ0MjAxZDBlZDIwOTFhY2VjNDU0OGM2OTZiYjE2YThhMTU4ZjYifX19", "§5Party", "\n§fZeige deine Party an und\n§fverwalte sie\n"));
		headBase.put("Clan", ItemHandler.getSkullByValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDk0NTk5NmM4YWU5MWUzNzYxOTZkNGRjNjc2ZmVjMzFmZWFjNzkwYTJmMTk1YjI5ODFhNzAzY2ExZDE2Y2I2In19fQ==", "§cClan",  "\n§fZeige informationen zu deinem Clan\n§fan und verwalte ihn\n" ));
		headBase.put("Replay", ItemHandler.getSkullByValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjdhMTg4ODA1MTYyY2E1ZGQ0ZjQ2NDljNjYxZDNmNmQyM2M0MjY2MmFlZjAxNjQ1YjFhOTdmNzhiM2YxMzIxOSJ9fX0=", "§3Replays", "\n§fSchaue dir alle Spiele an die\n§fdu gespielt hast und schaue sie dir\n§flive an\n"
				+ "\n§7» §cZurzeit nur für Teammitglieder!\n"));
	}

}
/*
 * Profil:
 * - Allgemeine Infos (rang usw)
 * - Stats
 * - level
 * - Einstellungen
 * - Xp/coin booster?
 * 
 * 
 */


