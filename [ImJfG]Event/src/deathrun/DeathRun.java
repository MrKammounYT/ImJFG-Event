package deathrun;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import imjfg.cooldown.nextGameCooldown;
import imjfg.main.main;
import imjfg.utils.GameManager;
import imjfg.utils.LocationAPI;
import imjfg.utils.state;

public class DeathRun extends GameManager implements Listener {

	

	ArrayList<Player> finished = new ArrayList<Player>();
	boolean max = false;
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		if(isState(state.deathrun)) {
			if(!finished.contains(e.getEntity())) {
				if (max == true)
					return;
				Player p = e.getEntity();
				p.sendMessage(main.Prefix + "§aCongrats You Have been qualified to the next round :)");
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 4.0f, 5.0f);
				Bukkit.broadcastMessage("§c" + p.getDisplayName() + " §aHas Been Qualified!");
				finished.add(p);
				p.setGameMode(GameMode.SPECTATOR);

				if (finished.size() == 16) {
					setState(state.waitingNextForGame);

					max = true;
					for (int i = 0; i < getPlayersSize(); i++) {
						Player f = getPlayerList().get(i);
						if (!finished.contains(f))
							removePlayer(f);

					}
					for(Player pls:Bukkit.getOnlinePlayers()) {
						pls.teleport(LocationAPI.getLocation("lobby"));
					}
					
				}
			}
		}
	}
	
	@EventHandler
	public void onrespawn(PlayerRespawnEvent e) {
		e.setRespawnLocation(LocationAPI.getLocation("lobby"));
		
	}
	
}
