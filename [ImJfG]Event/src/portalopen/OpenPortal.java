package portalopen;

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
import org.bukkit.event.player.PlayerPortalEvent;

import imjfg.cooldown.nextGameCooldown;
import imjfg.main.main;
import imjfg.utils.GameManager;
import imjfg.utils.LocationAPI;
import imjfg.utils.state;

public class OpenPortal extends GameManager implements Listener {

	
	ArrayList<Player> finished = new ArrayList<>();
	boolean max=false;
	@EventHandler
	public void portalenter(PlayerPortalEvent e) {
		if (isState(state.portalopen)) {
			if (e.getTo().getBlock().getType() == Material.PORTAL) {
				e.setCancelled(true);
				Player p = e.getPlayer();
				if(isSpectate(e.getPlayer()))return;

				if (!finished.contains(p)) {
					if (max == true)
						return;
					p.sendMessage(main.Prefix + "§aCongrats You Have been qualified to the next round :)");
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 4.0f, 5.0f);
					Bukkit.broadcastMessage("§c" + p.getDisplayName() + " §aHas Been Qualified!");
					p.setGameMode(GameMode.SPECTATOR);

					finished.add(p);
					if (finished.size() == 1) {
						max = true;
						for (int i = 0; i < getPlayersSize(); i++) {
							Player f = getPlayerList().get(i);
							if (!finished.contains(f))
								removePlayer(f);

						}
						for(Player pls:Bukkit.getOnlinePlayers()) {
							pls.teleport(LocationAPI.getLocation("lobby"));
							pls.playSound(pls.getLocation(),Sound.LEVEL_UP, 5.0f, 4.0f);

						}
						for(int i=0;i<getPlayersSize();i++) {
							Player winner = getPlayerList().get(i);
							Bukkit.broadcastMessage("§6§m--------------------------");
							Bukkit.broadcastMessage("§a§lWinner: §6"+winner.getName());
							Bukkit.broadcastMessage("§6§m--------------------------");
							winner.playSound(winner.getLocation(),Sound.LEVEL_UP, 5.0f, 4.0f);
						}
						
					}
					
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBoatPlace(BlockPlaceEvent e) {
		if(e.getBlock().getType() == Material.BOAT) {
			e.setCancelled(false);
			
		}
	}
	
	@EventHandler
	public void ondamage(EntityDamageEvent e) {
		if(isState(state.portalopen)) {
			e.setCancelled(true);
		}
		
		
		
	}
	
}
