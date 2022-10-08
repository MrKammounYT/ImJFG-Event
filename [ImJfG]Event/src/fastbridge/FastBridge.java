package fastbridge;

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

import imjfg.cooldown.nextGameCooldown;
import imjfg.main.main;
import imjfg.utils.GameManager;
import imjfg.utils.LocationAPI;
import imjfg.utils.state;

public class FastBridge extends GameManager implements Listener {


	ArrayList<Player> finished = new ArrayList<Player>();
	boolean max = false;

	@EventHandler
	public void onFINISH(PlayerMoveEvent e) {
		if (isState(state.fastbridge)) {
			if (!finished.contains(e.getPlayer())) {
				if (e.getTo().getBlock().getType() == Material.DIAMOND_BLOCK) {
					if(isSpectate(e.getPlayer()))return;
					if (max == true)
						return;
					Player p = e.getPlayer();
					p.sendMessage(main.Prefix + "§aCongrats You Have been qualified to the next round :)");
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 4.0f, 5.0f);
					Bukkit.broadcastMessage("§c" + p.getDisplayName() + " §aHas Been Qualified!");
					p.setGameMode(GameMode.SPECTATOR);

					finished.add(p);
					if (finished.size() == 20) {
						setState(state.waitingNextForGame);

						max = true;
						for (int i = 0; i < getPlayersSize(); i++) {
							Player f = getPlayerList().get(i);
							if (!finished.contains(f))
								removePlayer(f);

						}
						
					}
				}
			}
		}
	}

	@EventHandler
	public void ondeath(EntityDamageEvent e) {
			if (isState(state.fastbridge)) {
				if (e.getEntity() instanceof Player) {
					Player p = (Player) e.getEntity();
						p.setHealth(20);
						p.teleport(LocationAPI.getLocation("speedbridge"));
						p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 4.0f, 5.0f);

					
				
			}
		}
	}
}
