package imjfg.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

import imjfg.main.main;
import imjfg.utils.GameManager;
import imjfg.utils.LocationAPI;
import imjfg.utils.state;

public class Join extends GameManager implements Listener {
	
	

	@EventHandler
	public void quitevent(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		Player p = e.getPlayer();
		main.getinstance().players.remove(p);
		if((main.getinstance().hasStarted)) {
			Bukkit.broadcastMessage("§e"+p.getDisplayName() + " §cHas Been Disqualified!");
			main.getinstance().players.remove(p);
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		Player p = e.getPlayer();


		
		if((hasStarted() == false )) {
			if(getPlayersSize() >= 24) {
				p.kickPlayer("§cEvent is full");
			}
			if(p.isOp()) return;
			p.teleport(LocationAPI.getLocation("lobby"));
			addPlayer(p);
			p.setFoodLevel(20);
			p.getInventory().clear();
			p.setLevel(1);
			p.setHealth(20);
			p.sendMessage("§6§m-----------------------------");
			p.sendMessage("     §bWelcome To The Event      ");
			p.sendMessage("      §eGoodLuck§7,§cEnjoy       ");
			p.sendMessage("§6§m-----------------------------");
			p.getWorld().spawn(p.getLocation(), Firework.class);
			for(PotionEffect effect:p.getActivePotionEffects()){
				p.removePotionEffect(effect.getType());
				}
			p.setHealth(20);
			p.setGameMode(GameMode.SURVIVAL);
			p.getWorld().spawn(p.getLocation(), Firework.class);
			p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.0f, 4.0f);
			Bukkit.broadcastMessage("§7"+p.getName() + " §ehas joined (§b"+ getPlayersSize()+"§e/§b24§e)");
		}else {
			if(!isState(state.waiting)) {
			addspectater(p);
			p.sendMessage(main.Prefix + " §cYou Joined as a spectater Game Has already started");
			}else {
				p.kickPlayer("§cPlease wait till the game starts");
			}
			}
		
	}
	

}
