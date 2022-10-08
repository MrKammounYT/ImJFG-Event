package pickaxe;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import imjfg.cooldown.nextGameCooldown;
import imjfg.main.main;
import imjfg.utils.GameManager;
import imjfg.utils.state;

public class PickAxe extends GameManager implements Listener {

	ArrayList<Player> finished = new ArrayList<>();
	 boolean max =false;
	@EventHandler
	public void  craftpickaxe(CraftItemEvent e) {
		if(isState(state.pickaxe)){
			if(e.getRecipe().getResult().getType() == Material.STONE_PICKAXE) {
				Player p = (Player) e.getWhoClicked();
				if(isSpectate(p))return;

				if(!finished.contains(p)) {
					if(max == true)return;
					p.sendMessage(main.Prefix + "§aCongrats You Have been qualified to the next round :)");
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 4.0f, 5.0f);
					p.setGameMode(GameMode.SPECTATOR);

					Bukkit.broadcastMessage("§c" + p.getDisplayName() + " §aHas Been Qualified!");
					finished.add(p);
					p.setGameMode(GameMode.SPECTATOR);
					if (finished.size() == 2) {
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
	
	
	
}
