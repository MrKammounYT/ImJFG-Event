package Shooting;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import imjfg.cooldown.nextGameCooldown;
import imjfg.main.main;
import imjfg.utils.GameManager;
import imjfg.utils.LocationAPI;
import imjfg.utils.state;

public class Shooting extends GameManager implements Listener {

	ArrayList<Player> finished = new ArrayList<Player>();
	boolean max = false;
	

	@EventHandler
	public void onDamageentity(EntityDamageByEntityEvent e) {
		if(!isState(state.shooting))return;
		if(e.getDamager() instanceof Arrow && e.getEntity() instanceof Villager) {
			e.setCancelled(false);
			Arrow a = (Arrow) e.getDamager();
			if(a.getShooter() instanceof Player) {
				if(!finished.contains(a.getShooter())) {
					Player p = (Player) a.getShooter();
					p.getWorld().createExplosion(e.getEntity().getLocation(), 10, false);
					if(isSpectate(p))return;
					if (max == true)
						return;
					p.sendMessage(main.Prefix + "§aCongrats You Have been qualified to the next round :)");
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 4.0f, 5.0f);
					Bukkit.broadcastMessage("§c" + p.getDisplayName() + " §aHas Been Qualified!");
					p.setGameMode(GameMode.SPECTATOR);

					finished.add(p);
					checkPlayers(p,18);
					
				}
			}
		}
	}

public void checkPlayers(Player p,int n) {
	if (finished.size() == n) {
		max = true;
		setState(state.waitingNextForGame);
		for (int i = 0; i < getPlayersSize(); i++) {
			Player f = getPlayerList().get(i);
			if (!finished.contains(f)) {
				removePlayer(f);
			}

		}
		for(Player pls:Bukkit.getOnlinePlayers()) {
			pls.teleport(LocationAPI.getLocation("lobby"));
		}
		
		
	}
	
	}

}
