package net.kiligo.libary;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class ItemHandler {
	
	  @SuppressWarnings({ "unchecked", "rawtypes" })
	public static ItemStack getItem(Material material, String name, String lore, int damage, int amount, Boolean unbreakable)
	  {
	    ItemStack itemstack = new ItemStack(material, amount, (short)damage);
	    ItemMeta meta = itemstack.getItemMeta();
	    if (lore != null) {
	      if (lore.contains("\n"))
	      {
	        ArrayList<String> lorename = new ArrayList();
	        String[] loresplit = lore.split("\n");
	        for (String text : loresplit) {
	          lorename.add(text);
	        }
	        meta.setLore(lorename);
	      }
	      else
	      {
	        meta.setLore(Arrays.asList(new String[] { lore }));
	      }
	    }
	    meta.setDisplayName(name);
	    meta.spigot().setUnbreakable(unbreakable);
	    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
	    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    itemstack.setItemMeta(meta);
	    
	    return itemstack;
	  }
	  
		public static ItemStack getMaterial(Material material, String name, String lore)
		  {
		    ItemStack itemstack = new ItemStack(material);
		    ItemMeta meta = itemstack.getItemMeta();
		    if (lore != null) {
		      if (lore.contains("\n"))
		      {
		        ArrayList<String> lorename = new ArrayList<>();
		        String[] loresplit = lore.split("\n");
		        for (String text : loresplit) {
		          lorename.add(text);
		        }
		        meta.setLore(lorename);
		      }
		      else
		      {
		        meta.setLore(Arrays.asList(new String[] { lore }));
		      }
		    }
		    meta.setDisplayName(name);
		    itemstack.setItemMeta(meta);
		    
		    return itemstack;
		  }
		
		public static ItemStack getMaterialEnch(Material material, String name, String lore)
		  {
		    ItemStack itemstack = new ItemStack(material);
		    ItemMeta meta = itemstack.getItemMeta();
		    if (lore != null) {
		      if (lore.contains("\n"))
		      {
		        ArrayList<String> lorename = new ArrayList<>();
		        String[] loresplit = lore.split("\n");
		        for (String text : loresplit) {
		          lorename.add(text);
		        }
		        meta.setLore(lorename);
		      }
		      else
		      {
		        meta.setLore(Arrays.asList(new String[] { lore }));
		      }
		    }
		    meta.setDisplayName(name);
		    meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		    itemstack.setItemMeta(meta);
		    
		    return itemstack;
		  }


	  @SuppressWarnings({ "rawtypes", "unchecked" })
	public static ItemStack getHead(String PlayerName, String name, String lore, int amount)
	  {
	    ItemStack itemstack = new ItemStack(Material.SKULL_ITEM, amount, (short)3);
	    SkullMeta meta = (SkullMeta)itemstack.getItemMeta();
	    if (lore != null) {
	      if (lore.contains("\n"))
	      {
	        ArrayList<String> lorelist = new ArrayList();
	        String[] loresplit = lore.split("\n");
	        for (String text : loresplit) {
	          lorelist.add(text);
	        }
	        meta.setLore(lorelist);
	      }
	      else
	      {
	        meta.setLore(Arrays.asList(new String[] { lore }));
	      }
	    }
	    meta.setOwner(PlayerName);
	    meta.setDisplayName(name);
	    itemstack.setItemMeta(meta);
	    
	    return itemstack;
	  }
	  
	  
	  public ItemStack getSkull(String url) {
	        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
	        if(url.isEmpty())return head;
	       
	       
	        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
	        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
	        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
	        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
	        Field profileField = null;
	        try {
	            profileField = headMeta.getClass().getDeclaredField("profile");
	            profileField.setAccessible(true);
	            profileField.set(headMeta, profile);
	        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
	            e1.printStackTrace();
	        }
	        head.setItemMeta(headMeta);
	        return head;
	    }
	  
	  public static ItemStack getSkullByValue(String value, String name, String lore) {
	        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
	        if(value.isEmpty())return head;
	       
	        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
	        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
	        profile.getProperties().put("textures", new Property("textures", new String(value)));
	        Field profileField = null;
	        try {
	            profileField = headMeta.getClass().getDeclaredField("profile");
	            profileField.setAccessible(true);
	            profileField.set(headMeta, profile);
	        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
	            e1.printStackTrace();
	        }
	        
	        
		    if (lore != null) {
			      if (lore.contains("\n"))
			      {
			        ArrayList<String> lorelist = new ArrayList<String>();
			        String[] loresplit = lore.split("\n");
			        for (String text : loresplit) {
			          lorelist.add(text);
			        }
			        	headMeta.setLore(lorelist);
			      }
			      else
			      {
			    	  headMeta.setLore(Arrays.asList(new String[] { lore }));
			      }
			    }
	        
		    headMeta.setDisplayName(name);
	        head.setItemMeta(headMeta);
	        return head;
	    }
	  
	  
	  public static ItemStack getGlass(String name, int Color, int amount, String lore)
	  {
	    ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, amount, (byte)Color);
	    ItemMeta meta = is.getItemMeta();
	    meta.setDisplayName(name);
	    if (lore != null) {
		      if (lore.contains("\n"))
		      {
		        ArrayList<String> lorelist = new ArrayList<String>();
		        String[] loresplit = lore.split("\n");
		        for (String text : loresplit) {
		          lorelist.add(text);
		        }
		        meta.setLore(lorelist);
		      }
		      else
		      {
		        meta.setLore(Arrays.asList(new String[] { lore }));
		      }
		    }
	    is.setItemMeta(meta);
	    return is;
	  }
	  
	  public static ItemStack getDye(String name, DyeColor farbe, String lore, int amount) {
		      Dye dye = new Dye();
		      dye.setColor(farbe);
		      ItemStack dyeitem = dye.toItemStack(amount);
		      ItemMeta dyemeta = dyeitem.getItemMeta();
		      
			    if (lore != null) {
				      if (lore.contains("\n"))
				      {
				        ArrayList<String> lorename = new ArrayList<>();
				        String[] loresplit = lore.split("\n");
				        for (String text : loresplit) {
				          lorename.add(text);
				        }
				        dyemeta.setLore(lorename);
				      }
				      else
				      {
				    	 dyemeta.setLore(Arrays.asList(new String[] { lore }));
				      }
				    }
		      
		      dyemeta.setDisplayName(name);
		      dyemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		      dyeitem.setItemMeta(dyemeta);
		      
		      return dyeitem;
	  }
	  
	  public static ItemStack getDyeEnch(String name, DyeColor farbe, String lore, int amount) {
	      Dye dye = new Dye();
	      dye.setColor(farbe);
	      ItemStack dyeitem = dye.toItemStack(amount);
	      ItemMeta dyemeta = dyeitem.getItemMeta();
	      
		    if (lore != null) {
			      if (lore.contains("\n"))
			      {
			        ArrayList<String> lorename = new ArrayList<>();
			        String[] loresplit = lore.split("\n");
			        for (String text : loresplit) {
			          lorename.add(text);
			        }
			        dyemeta.setLore(lorename);
			      }
			      else
			      {
			    	 dyemeta.setLore(Arrays.asList(new String[] { lore }));
			      }
			    }
	      
	      dyemeta.setDisplayName(name);
	      dyemeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
	      dyemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	      dyeitem.setItemMeta(dyemeta);
	      
	      return dyeitem;
  }
	  
	  
		public static ItemStack getClay(String name, int Color, int amount, String lore)
		  {
		    ItemStack is = new ItemStack(Material.STAINED_CLAY, amount, (byte)Color);
		    ItemMeta meta = is.getItemMeta();
		    meta.setDisplayName(name);
		    if (lore != null) {
			      if (lore.contains("\n"))
			      {
			        ArrayList<String> lorelist = new ArrayList<String>();
			        String[] loresplit = lore.split("\n");
			        for (String text : loresplit) {
			          lorelist.add(text);
			        }
			        meta.setLore(lorelist);
			      }
			      else
			      {
			        meta.setLore(Arrays.asList(new String[] { lore }));
			      }
			    }
		    is.setItemMeta(meta);
		    return is;
		  }

	  
}
