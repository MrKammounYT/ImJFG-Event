package imjfg.events;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import imjfg.utils.GameManager;
import imjfg.utils.LocationAPI;
import imjfg.utils.state;

public class General extends GameManager implements Listener {

	

	@EventHandler(priority = EventPriority.MONITOR)
	public void ondamageothers(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			if(isSpectate((Player) e.getDamager())) {
				e.setCancelled(true);
			}
		}
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			
			e.setCancelled(true);
		}
	}

@EventHandler
public void onfood(FoodLevelChangeEvent e) {
	e.setFoodLevel(20);
	e.setCancelled(true);
}

@EventHandler
public void onchat(PlayerChatEvent e) {
	Player p = e.getPlayer();
		e.setFormat(p.getPlayerListName() + " §7» §f"+e.getMessage());
	
}
	
	
	@EventHandler
	public void onThrow(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}

	@EventHandler(priority =  EventPriority.MONITOR)
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
		if(e.getEntity().getWorld().getName().equalsIgnoreCase(LocationAPI.getLocation("lobby").getWorld().getName())) {
			if(e.getCause() == DamageCause.VOID) {
				e.getEntity().teleport(LocationAPI.getLocation("lobby"));
			}
			e.setCancelled(true);
		}
		if(isSpectate((Player) e.getEntity())) {
			e.setCancelled(true);
			return;
		}

		
		
		if (isState(state.portalopen) || isState(state.pickaxe) || isState(state.deathrun) || isState(state.waiting) || isState(state.finish))
			return;
		if (e.getEntity() instanceof Player) {
			e.setCancelled(true);
		}
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {

		if (e.getPlayer().hasPermission("event.host.admin"))
			return;
		
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase(LocationAPI.getLocation("lobby").getWorld().getName())) {e.setCancelled(true); return;}

		if (isState(state.portalopen) || isState(state.pickaxe))
			return;

	
		
		e.setCancelled(true);
	}

	@EventHandler
	public void onbreak(BlockBreakEvent e) {
		if (e.getPlayer().hasPermission("event.host.admin"))
			return;
		
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase(LocationAPI.getLocation("lobby").getWorld().getName())) {e.setCancelled(true); return;}

		
		if (isState(state.portalopen)  || isState(state.pickaxe))
			return;

		if(isState(state.fastbridge)) {
			if(e.getBlock().getType() == Material.WOOD) {
				e.setCancelled(false);
			}
		}
		e.setCancelled(true);
	}

}
