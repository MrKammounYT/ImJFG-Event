package airshooting;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import imjfg.cooldown.nextGameCooldown;
import imjfg.main.main;
import imjfg.utils.GameManager;
import imjfg.utils.LocationAPI;
import imjfg.utils.state;

public class aevent extends GameManager implements Listener {

	ArrayList<Player> finished = new ArrayList<>();
	 boolean max =false;
	@EventHandler
	public void onDamageentity(EntityDamageByEntityEvent e) {
		if(!isState(state.Airshooting))return;
		if(e.getDamager() instanceof Arrow && e.getEntity() instanceof Villager) {
			e.setCancelled(false);
			Arrow a = (Arrow) e.getDamager();
			if(a.getShooter() instanceof Player) {
				if(!finished.contains(a.getShooter())) {
					Player p = (Player) a.getShooter();
					p.getWorld().createExplosion(e.getEntity().getLocation(), 10, false);
					if(max == true)return;
					p.sendMessage(main.Prefix + "§aCongrats You Have been qualified to the next round :)");
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 4.0f, 5.0f);
					Bukkit.broadcastMessage("§c" + p.getDisplayName() + " §aHas Been Qualified!");
					p.setGameMode(GameMode.SPECTATOR);

					finished.add(p);
					p.setGameMode(GameMode.SPECTATOR);
					if (finished.size() == 4) {
						max = true;		
						setState(state.waitingNextForGame);
						for (int i = 0; i < getPlayersSize(); i++) {
							Player f = getPlayerList().get(i);
							if (!finished.contains(f))
								removePlayer(f);

						}
						nextGameCooldown nt = new nextGameCooldown();
						nt.runTaskTimer(main.getinstance(), 0,20);
					}
					
				}
			}
		}
	}
	
	@EventHandler
	public void ondeath(EntityDamageEvent e) {
		if (e.getCause() == DamageCause.FALL) {
			if (isState(state.Airshooting)) {
				if (e.getEntity() instanceof Player) {
					Player p = (Player) e.getEntity();
					if(isSpectate(p))return;
						p.setHealth(20);
						p.teleport(LocationAPI.getLocation("airshooting"+main.Pl.get(p).getspawnNumber()));
						p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 4.0f, 5.0f);
						p.setFireTicks(0);
						p.setFallDistance(0);
					
				}
			}
		}
	}

	
	
	
	
}
