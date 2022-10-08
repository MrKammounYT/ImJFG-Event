package sandbridge;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

import imjfg.cooldown.nextGameCooldown;
import imjfg.main.main;
import imjfg.utils.GameManager;
import imjfg.utils.LocationAPI;
import imjfg.utils.state;

public class SandBridge extends GameManager implements Listener {

	ArrayList<Player> finished = new ArrayList<Player>();
	boolean max = false;

	@EventHandler
	public void onFINISH(PlayerMoveEvent e) {
		if (isState(state.sandwalljump)) {
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
					checkPlayers(p, 10);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onSandPlace(BlockPlaceEvent e) {
		if (isState(state.sandwalljump)) {
			if(e.getBlock().getType() == Material.SAND) {
				if(isSpectate(e.getPlayer()))return;

				e.setCancelled(false);
			}
			
		}

	}

	@EventHandler
	public void ondeath(EntityDamageEvent e) {

		if (isState(state.sandwalljump)) {
			if (e.getCause() == DamageCause.VOID) {
				if (e.getEntity() instanceof Player) {
					Player p = (Player) e.getEntity();
					e.setCancelled(true);
					if(isSpectate(p))return;
					p.setHealth(20);
					p.teleport(LocationAPI.getLocation("doublejump"));
					p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 4.0f, 5.0f);
					p.setFallDistance(0);
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
