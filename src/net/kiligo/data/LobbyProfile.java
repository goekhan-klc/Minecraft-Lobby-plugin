package net.kiligo.data;

import java.util.HashMap;

public class LobbyProfile {
	
	private static HashMap<String,LobbyProfile> playerProfiles = new HashMap<>();
	
	private String playername;
	private String playeruuid;
	private int playerhiderstate;
	private int permissionlevel; // 0=player;1=special;2=vip;(3=admin)
	private int nickstate;
	private int coins; //To-DO
	private String rank;//To-DO
	private String clan;//To-DO
	private String friends;//To-DO
	private String playtime;//To-DO
	
	
	public LobbyProfile(String playername, String playeruuid) {
		this.playername = playername;
		this.playeruuid = playeruuid;
		playerProfiles.put(playeruuid, this);
	}
	
	public void getDataStacks() {
		playerhiderstate = LobbyItemData.getHiderState(playeruuid);
		nickstate = LobbyItemData.getNickState(playeruuid);
		playerProfiles.put(playeruuid, this);
	}

	public void saveData() {
		LobbyItemData.setHiderState(playername, playeruuid, playerhiderstate);
		LobbyItemData.setNickState(playername, playeruuid, nickstate);
	}
	
	public static HashMap<String, LobbyProfile> getPlayerProfiles() {
		return playerProfiles;
	}


	public int getPlayerhiderstate() {
		return playerhiderstate;
	}

	public void setPlayerhiderstate(int playerhiderstate) {
		this.playerhiderstate = playerhiderstate;
		playerProfiles.put(playeruuid, this);
	}

	public int getPermissionlevel() {
		return permissionlevel;
	}

	public void setPermissionlevel(int permissionlevel) {
		this.permissionlevel = permissionlevel;
	}

	public int getNickstate() {
		return nickstate;
	}

	public void setNickstate(int nickstate) {
		this.nickstate = nickstate;
		playerProfiles.put(playeruuid, this);
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
		playerProfiles.put(playeruuid, this);
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getClan() {
		return clan;
	}

	public void setClan(String clan) {
		this.clan = clan;
	}

	public String getFriends() {
		return friends;
	}

	public void setFriends(String friends) {
		this.friends = friends;
	}

	public String getPlaytime() {
		return playtime;
	}

	public void setPlaytime(String playtime) {
		this.playtime = playtime;
	}

	public String getPlayername() {
		return playername;
	}

	public void setPlayername(String playername) {
		this.playername = playername;
	}

	public String getPlayeruuid() {
		return playeruuid;
	}

	public void setPlayeruuid(String playeruuid) {
		this.playeruuid = playeruuid;
	}
	

}
