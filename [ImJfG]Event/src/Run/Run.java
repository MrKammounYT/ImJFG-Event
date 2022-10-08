package Run;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import imjfg.cooldown.nextGameCooldown;
import imjfg.main.main;
import imjfg.utils.GameManager;
import imjfg.utils.LocationAPI;
import imjfg.utils.state;

public class Run extends GameManager implements Listener {

	ArrayList<Player> finished = new ArrayList<Player>();
	boolean max = false;

	@EventHandler
	public void onFINISH(PlayerMoveEvent e) {
		if (isState(state.run)) {
			if (!finished.contains(e.getPlayer())) {
				if (e.getTo().getBlock().getType() == Material.IRON_PLATE) {
					if(isSpectate(e.getPlayer()))return;

					if (max == true)
						return;
					Player p = e.getPlayer();
					p.sendMessage(main.Prefix + "§aCongrats You Have been qualified to the next round :)");
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 4.0f, 5.0f);
					p.setGameMode(GameMode.SPECTATOR);
					Bukkit.broadcastMessage("§c" + p.getDisplayName() + " §aHas Been Qualified!");
					finished.add(p);
					checkPlayers(p);
				}
				if (e.getTo().getBlock().getType() == Material.SLIME_BLOCK) {
		            e.getPlayer().setVelocity(e.getPlayer().getVelocity().add(new Vector(e.getPlayer().getVelocity().getX(),e.getPlayer().getVelocity().getY()+10,e.getPlayer().getVelocity().getZ())));
		            e.getPlayer().setFallDistance(0);
					e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.WITHER_SHOOT, 4.0f, 5.0f);

				}

			}
		}
	}

	

	@EventHandler
	public void ondeath(EntityDamageEvent e) {

		if (isState(state.run)) {
			if (e.getEntity() instanceof Player) {
				if(e.getCause() == DamageCause.FALL) {
					e.setCancelled(true);
				}
				Player p = (Player) e.getEntity();
				if (e.getDamage() > 0) {
					e.setCancelled(true);
					p.setHealth(20);
					p.teleport(LocationAPI.getLocation("run"));
					p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 4.0f, 5.0f);
					p.setFireTicks(0);
					p.setFallDistance(0);
				}
			}
		}
	}

	

public void checkPlayers(Player p) {
	if (finished.size() == 22) {
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
