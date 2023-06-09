package net.kiligo.libary;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;

public class NavigatorItem {

	private static ArrayList<NavigatorItem> items = new ArrayList<>();
	
	private String name;
	private String displayname;
	private Material item;
	private int slot;
	private String lore;
	private Location loc;
	
	public NavigatorItem(String name, String displayname, Material item, int slot, String lore, Location loc) {
		this.name = name;
		this.displayname = displayname;
		this.item = item;
		this.slot = slot;
		this.lore = lore;
		this.setLoc(loc);
	}

	
	public static ArrayList<NavigatorItem> getItems() {
		return items;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public Material getItem() {
		return item;
	}

	public void setItem(Material item) {
		this.item = item;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}


	public String getLore() {
		return lore;
	}


	public void setLore(String lore) {
		this.lore = lore;
	}


	public Location getLoc() {
		return loc;
	}


	public void setLoc(Location loc) {
		this.loc = loc;
	}


}
