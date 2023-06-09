package net.kiligo.libary;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.kiligo.lobby.Lobby;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldServer;

public class FakePlayer {
	
	private static ArrayList<FakePlayer> npcs = new ArrayList<>();
	private static HashMap<Player, ArrayList<FakePlayer>> npcPlayer = new HashMap<>();
	private static ArrayList<FakePlayer> npcsToSpawn = new ArrayList<>(); 
	
	private Location loc;
	private String name;
	private String displayname;
	private EntityPlayer npc;
	private boolean isspawned;
	
	public FakePlayer(Location loc, String name, String displayname) {
			this.loc = loc;
			this.name = name;
			this.displayname = displayname;
	}
	
	
	@SuppressWarnings("deprecation")
	public void spawn(Player p) {
		isspawned = true;
		
		MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
       
        CraftPlayer player = (CraftPlayer) p;
        
        UUID uuid = UUID.randomUUID();
        GameProfile profile = new GameProfile(uuid, displayname);
        
        ArrayList<Property> skinprop = new ArrayList<>(player.getProfile().getProperties().get("textures"));
        
        profile.getProperties().put("textures", skinprop.get(0));
        
        EntityPlayer npc = new EntityPlayer(server, world, profile, new PlayerInteractManager(world));
        
        npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        IChatBaseComponent s = new ChatMessage("§7NPC §8| §7" + Methods.randomAlphaNumericString(10));
        npc.listName = s;
        
        this.npc = npc;
        
        PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
       
        npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        npcs.add(this);
        
        ArrayList<FakePlayer> tempnpcs = npcPlayer.get(p);
        tempnpcs.add(this);
        npcPlayer.put(p, tempnpcs);

        
        npc.yaw = loc.getYaw();
        npc.pitch = loc.getPitch();
        
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Lobby.getLobby(), new Runnable() {
		
        	@Override
        	public void run() {
                npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
        	}
        }, 7);
	}
	
	@SuppressWarnings("deprecation")
	public void spawnButNotHide(Player p) {
		isspawned = true;
		
		MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
       
        CraftPlayer player = (CraftPlayer) p;
        
        UUID uuid = UUID.randomUUID();
        GameProfile profile = new GameProfile(uuid, displayname);
        
        ArrayList<Property> skinprop = new ArrayList<>(player.getProfile().getProperties().get("textures"));
        
        profile.getProperties().put("textures", skinprop.get(0));
        
        EntityPlayer npc = new EntityPlayer(server, world, profile, new PlayerInteractManager(world));
        
        npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        IChatBaseComponent s = new ChatMessage("§7NPC §8| §7" + Methods.randomAlphaNumericString(10));
        npc.listName = s;
        
        this.npc = npc;
        
        PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
       
        npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        npcs.add(this);
        
        ArrayList<FakePlayer> tempnpcs = npcPlayer.get(p);
        tempnpcs.add(this);
        npcPlayer.put(p, tempnpcs);

        
        npc.yaw = loc.getYaw();
        npc.pitch = loc.getPitch();
        
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Lobby.getLobby(), new Runnable() {
		
        	@Override
        	public void run() {
                npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        	}
        }, 7);
	}
	
	public void despawn(Player p) {
		isspawned = false;
		
		CraftPlayer player = (CraftPlayer) p;
		
		npcs.remove(this);
		
		
		player.getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
		player.getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
		
	}
	
	
	public void delete(Player p) {
		despawn(p);
		npcPlayer.get(p).remove(this);
	}
	
	
	public static void loadNPCs() {
		if(Li.getConfigcfg().contains("NPCNames")) {
			for(String s : Li.getConfigcfg().getStringList("NPCNames")) {
				FakePlayer fp = new FakePlayer(Methods.getConfigOnlyLocation("FakePlayers", s), s, Li.getConfigcfg().getString("FakePlayers." + s + ".Displayname")
						.replaceAll("&", "§").replaceAll("_", " "));
				npcsToSpawn.add(fp);
			}
			
		}
	}
	
	
	
	
	
	
	
	public boolean isSpawned() {
		return isspawned;
	}
	
	
	public void setHeadLoc(Location loc) {
        PacketPlayOutEntityLook packet = new PacketPlayOutEntityLook(npc.getId(), getFixRotation(loc.getYaw()),getFixRotation(loc.getPitch()) , true);
        PacketPlayOutEntityHeadRotation packetHead = new PacketPlayOutEntityHeadRotation();
        setValue(packetHead, "a", npc.getId());
        setValue(packetHead, "b", getFixRotation(loc.getYaw()));
       

        Reflections.sendPacket(packet);
        Reflections.sendPacket(packetHead);
	}
	
	public static String getNPCByID(int id) {
		for(FakePlayer fp : npcs) {
			if(fp.npc.getId() == id) {
				return fp.getName();
			}
		}
		return null;
	}
	
    public void setValue(Object obj,String name,Object value){
        try{
        Field field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(obj, value);
        }catch(Exception e){}
}

    public Object getValue(Object obj,String name){
        try{
        Field field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(obj);
        }catch(Exception e){}
        return null;
}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getName() {
		return name;
	}
	
    public int getFixLocation(double pos){
        return (int)MathHelper.floor(pos * 32.0D);
}

    public byte getFixRotation(float yawpitch){
        return (byte) ((int) (yawpitch * 256.0F / 360.0F));
    }

	public void setName(String name) {
		this.name = name;
	}

	public static ArrayList<FakePlayer> getNpcs() {
		return npcs;
	}

	public static void setNpcs(ArrayList<FakePlayer> npcs) {
		FakePlayer.npcs = npcs;
	}

	public static HashMap<Player, ArrayList<FakePlayer>> getNpcPlayer() {
		return npcPlayer;
	}

	public static void setNpcPlayer(HashMap<Player, ArrayList<FakePlayer>> npcPlayer) {
		FakePlayer.npcPlayer = npcPlayer;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
        npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        npc.yaw = loc.getYaw();
        npc.pitch = loc.getPitch();
        npc.setOnFire(5);
	}

	public EntityPlayer getNpc() {
		return npc;
	}

	public void setNpc(EntityPlayer npc) {
		this.npc = npc;
	}

	public boolean isIsspawned() {
		return isspawned;
	}

	public void setIsspawned(boolean isspawned) {
		this.isspawned = isspawned;
	}
	
	public static boolean isFakePlayer(int id) {
		for(FakePlayer fp : npcs) {
			if(fp.npc.getId() == id) {
				return true;
			}
		}
		return false;
	}


	public static ArrayList<FakePlayer> getNpcsToSpawn() {
		return npcsToSpawn;
	}


	public static void setNpcsToSpawn(ArrayList<FakePlayer> npcsToSpawn) {
		FakePlayer.npcsToSpawn = npcsToSpawn;
	}
}
