package net.kiligo.libary;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class MessageHandler {
	
	public static String getAusführText(String text, String courser, String ausführen){
		String umgewandelt = "{\"text\":\""+text+"\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\""+ausführen+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""+courser+"\"}]}}}";
		return umgewandelt;
	}
	
	public static String getVorschreibText(String text, String courser, String vorschreib){
		String umgewandelt = "{\"text\":\""+text+"\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\""+vorschreib+"\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""+courser+"\"}]}}}";
		return umgewandelt;
	}
	
	public static String getNrmlText(String text){
		return "{\"text\":\""+text+"\"}";
	}
	
	public static void sendMessage(Player p, String msg){
		IChatBaseComponent chat = ChatSerializer.a("[\"\","+msg+"]");
		PacketPlayOutChat packet = new PacketPlayOutChat(chat);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
	}
	
}