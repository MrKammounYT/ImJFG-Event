package lavapath;

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
import org.bukkit.inventory.ItemStack;

import imjfg.cooldown.nextGameCooldown;
import imjfg.main.main;
import imjfg.utils.GameManager;
import imjfg.utils.LocationAPI;
import imjfg.utils.state;

public class LavaPath extends GameManager implements Listener {


	ArrayList<Player> finished = new ArrayList<Player>();
	boolean max = false;

	@EventHandler
	public void onFINISH(PlayerMoveEvent e) {
		if (isState(state.lavapath)) {
			if (!finished.contains(e.getPlayer())) {
				if (e.getTo().getBlock().getType() == Material.GOLD_BLOCK) {
					if(isSpectate(e.getPlayer()))return;
					if (max == true)
						return;
					Player p = e.getPlayer();
					p.sendMessage(main.Prefix + "§aCongrats You Have been qualified to the next round :)");
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 4.0f, 5.0f);
					p.setGameMode(GameMode.SPECTATOR);

					Bukkit.broadcastMessage("§c" + p.getDisplayName() + " §aHas Been Qualified!");
					finished.add(p);
					if (finished.size() == 12) {
						max = true;
						setState(state.waitingNextForGame);

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
		if (e.getCause() == DamageCause.LAVA) {
			if (isState(state.lavapath)) {
				if (e.getEntity() instanceof Player) {
					Player p = (Player) e.getEntity();
					if(isSpectate(p))return;
					e.setCancelled(true);
						p.setHealth(20);
						p.teleport(LocationAPI.getLocation("lavapath"+main.Pl
								.get(p).getspawnNumber()));
						p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 4.0f, 5.0f);
						for(int x=0;x<p.getInventory().getSize();x++) {
							p.getInventory().setItem(x, new ItemStack(Material.BOAT));
						}
				}
			}
		}
	}
	
	
	
}
