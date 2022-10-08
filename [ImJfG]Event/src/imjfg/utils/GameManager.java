package imjfg.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import imjfg.main.main;

public class GameManager {

	



	public boolean isSpectate(Player p) {
		if(main.getinstance().spectate.contains(p) || main.getinstance().manager.contains(p)) {
			return true;
		}
		return false;
	}
	public boolean isState(state state) {
		if(main.getinstance().states == state)return true;
		else return false;
	}
	public void setState(state state) {
		main.getinstance().states = state;

		if(state  == imjfg.utils.state.waitingNextForGame)return;
	
		main.getinstance().lastState = state;
	}
	public boolean isLastState(state state) {
		if(main.getinstance().lastState == state)return true;
		else return false;
	}
	
	public void addspectate(Player p) {
		main.getinstance().spectate.add(p);
	}
	public void addspectater(Player p) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000000, 2));
		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100000000, 2));
		p.setHealth(20);
		p.getInventory().clear();
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		p.setGameMode(GameMode.SPECTATOR);
		for(Player pls : getPlayerList()) {
			pls.hidePlayer(p);
		}
		p.setPlayerListName("§7[§cSpectate§7] §8" + p.getDisplayName());
		addspectate(p);
	}
	public void removespectate(Player p ) {
		main.getinstance().spectate.remove(p);
	}
	public ArrayList<Player> getMangerList(){
		return main.getinstance().manager;
	}
	public ArrayList<Player> getSpectateList() {
		return main.getinstance().spectate;
	}
	public ArrayList<Player> getPlayerList(){
		return main.getinstance().players;
	}
	public int getPlayersSize() {
		return main.getinstance().players.size();
	}
	public void addPlayer(Player p) {
		main.Pl.put(p, new PlayerManger(0));
		main.getinstance().players.add(p);
		p.setPlayerListName("§8[§a"+(main.getinstance().players.size())+"§8] §7"+p.getName());
		p.setLevel(main.getinstance().players.size());
	}

	public void removePlayer(Player p) {
	Firework fw =	p.getWorld().spawn(p.getLocation(), Firework.class);
	fw.setPassenger(p);
	fw.setFireTicks(20);
	fw.setTicksLived(100);
	p.sendMessage(main.Prefix + "&cNice try, Good Luck next time");
	p.playSound(p.getLocation(), Sound.VILLAGER_NO, 4.0f, 5.0f);
	Bukkit.broadcastMessage("§c"+p.getDisplayName() + " §4Has Been Eliminated!");
	Titles.sendTitle(p, "§cGG!", "§eGood Luck next time", 60);
	main.getinstance().players.remove(p);
	for(Player pls : getPlayerList()) {
		pls.hidePlayer(p);
	}
	p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000000, 2));
	p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100000000, 2));
	p.setHealth(20);
	p.getInventory().clear();
	p.getInventory().setHelmet(null);
	p.getInventory().setChestplate(null);
	p.getInventory().setLeggings(null);
	p.getInventory().setBoots(null);
	p.setGameMode(GameMode.SPECTATOR);
	p.setPlayerListName("§7[§cSpectate§7] §8" + p.getDisplayName());
	addspectate(p);
	
	}
	public boolean hasStarted() {
		return main.getinstance().hasStarted;
	}
	public state getNextGameState() {
		if(getGameNumber() == 0)return state.waiting;
		else if(getGameNumber() == 1)return state.run;
		else if(getGameNumber() == 2)return state.parkour;
		else if(getGameNumber() == 3)return state.shooting;
		else if(getGameNumber() == 4)return state.deathrun;
		else if(getGameNumber() == 5)return state.fastbridge;
		else if(getGameNumber() == 6)return state.lavapath;
		else if(getGameNumber() == 7)return state.sandwalljump;
		else if(getGameNumber() == 8)return state.boatmlg;
		else if(getGameNumber() == 9)return state.doublejump;
		else if(getGameNumber() == 10)return state.Airshooting;
		else if(getGameNumber() == 11)return state.pickaxe;
		else if(getGameNumber() == 12)return state.portalopen;
		else return state.finish;
	}
	
	public  int getGameNumber() {
		if(isState(state.waiting))return 0;
		else if(isState(state.run))return 1;
		else if(isState(state.parkour))return 2;
		else if(isState(state.shooting))return 3;
		else if(isState(state.deathrun))return 4;
		else if(isState(state.fastbridge))return 5;
		else if(isState(state.lavapath))return 6;
		else if(isState(state.sandwalljump))return 7;
		else if(isState(state.boatmlg))return 8;
		else if(isState(state.doublejump))return 9;
		else if(isState(state.Airshooting))return 10;
		else if(isState(state.pickaxe))return 11;
		else if(isState(state.portalopen))return 12;
		else return 0;
	}
	
	
}
