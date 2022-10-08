package BoatMLG;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;

import imjfg.cooldown.BoatWait;
import imjfg.main.main;
import imjfg.utils.GameManager;
import imjfg.utils.LocationAPI;
import imjfg.utils.state;

public class BoatMLG extends GameManager implements Listener {

	ArrayList<Player> finished = new ArrayList<>();
	boolean max = false;

	
	

	
	@EventHandler
	public void portalenter(PlayerPortalEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onPlayermoveEvent(PlayerMoveEvent e) {
		if (isState(state.boatmlg)) {
			if (BoatWait.canmove == false) {
				if (e.getFrom().getZ() != e.getTo().getZ() && e.getFrom().getX() != e.getTo().getX()) {
					e.getPlayer().teleport(e.getFrom());
				}
				return;
			}
			if(e.getTo().getBlock().getType() == Material.ENDER_PORTAL) {
					Player p = e.getPlayer();
					if (isSpectate(p))
						return;

					if (!finished.contains(p)) {
						if (max == true)
							return;
						p.sendMessage(main.Prefix + "§aCongrats You Have been qualified to the next round :)");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 4.0f, 5.0f);
						Bukkit.broadcastMessage("§c" + p.getDisplayName() + " §aHas Been Qualified!");
						p.setGameMode(GameMode.SPECTATOR);
						finished.add(p);
						e.setCancelled(true);
						if (finished.size() == 8) {
							setState(state.waitingNextForGame);

							max = true;
							for (int i = 0; i < getPlayersSize(); i++) {
								Player f = getPlayerList().get(i);
								if (!finished.contains(f))
									removePlayer(p);

							}
							for (Player pls : Bukkit.getOnlinePlayers()) {
								pls.teleport(LocationAPI.getLocation("lobby"));
							}

						}

					}
				}
			}
			}
		
	

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBoatPlace(BlockPlaceEvent e) {
		if (e.getBlock().getType() == Material.BOAT) {
			e.setCancelled(false);

		}
	}

	@EventHandler
	public void ondamage(EntityDamageEvent e) {
		if (isState(state.boatmlg)) {
			if (e.getEntity() instanceof Player) {
				if (e.getCause() == DamageCause.FALL) {
					e.setCancelled(true);
					Player p = (Player) e.getEntity();
					if (isSpectate(p))
						return;
					e.setCancelled(true);
					p.setHealth(20);
					p.teleport(LocationAPI.getLocation("boatmlg"));
					p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 4.0f, 5.0f);
				}
			}
		}

	}

}
