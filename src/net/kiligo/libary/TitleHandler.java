package net.kiligo.libary;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleHandler
{
  public static void sendActionBar(Player p, String nachricht) {
    CraftPlayer cp = (CraftPlayer)p;
    IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + nachricht + "\"}");
    PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
    cp.getHandle().playerConnection.sendPacket(ppoc);
  }
  
  public static void sendTabList(Player player, String header, String footer) {
    if (header == null) {
      header = "";
    }
    if (footer == null) {
      footer = "";
    }
    try {
      Object tabHeader = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + header + "\"}" });
      Object tabFooter = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + footer + "\"}" });
      Constructor<?> titleConstructor = getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(new Class[] { getNMSClass("IChatBaseComponent") });
      Object packet = titleConstructor.newInstance(new Object[] { tabHeader });
      Field field = packet.getClass().getDeclaredField("b");
      field.setAccessible(true);
      field.set(packet, tabFooter);
      sendPacket(player, packet);
    }
    catch (SecurityException|NoSuchMethodException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|InstantiationException|NoSuchFieldException ex)
    {
      ex.printStackTrace();
    }
  }
  
  public static void sendTitle(Player p, Integer einbelnden, Integer anzeigen, Integer ausblenden, String title, String subtitle) {
    PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
    
    PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, einbelnden.intValue(), anzeigen.intValue(), ausblenden.intValue());
    connection.sendPacket(packetPlayOutTimes);
    if (subtitle != null) {
      subtitle = subtitle.replaceAll("%p%", p.getDisplayName());
      subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
      IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
      PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
      connection.sendPacket(packetPlayOutSubTitle);
    }
    if (title != null) {
      title = title.replaceAll("%p%", p.getDisplayName());
      title = ChatColor.translateAlternateColorCodes('&', title);
      IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
      PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
      connection.sendPacket(packetPlayOutTitle);
    }
  }
  
  
  
  
  
  
  
  private static Class<?> getNMSClass(String name)
  {
    String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    try
    {
      return Class.forName("net.minecraft.server." + version + "." + name);
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  private static void sendPacket(Player player, Object packet)
  {
    try
    {
      Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
      Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
      playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(playerConnection, new Object[] { packet });
    }
    catch (NoSuchMethodException|SecurityException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchFieldException e)
    {
      e.printStackTrace();
    }
  }
}
