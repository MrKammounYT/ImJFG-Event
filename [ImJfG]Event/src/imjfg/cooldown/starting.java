package imjfg.cooldown;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import imjfg.main.main;
import imjfg.utils.GameManager;
import imjfg.utils.LocationAPI;
import imjfg.utils.state;

public class starting extends BukkitRunnable {

	int timer = 30;
	
	@Override
	public void run() {
		for(Player pls : Bukkit.getOnlinePlayers()) {
			pls.setLevel(timer);
		}
		if(timer ==10 || timer == 5 || timer ==3 || timer == 2 || timer == 1) {
			Bukkit.broadcastMessage(main.Prefix + "§eEvent Will start in §b"+timer);
			for(Player pls : Bukkit.getOnlinePlayers()) {
				pls.playSound(pls.getLocation(), Sound.CLICK, 4.0f, 6.0f);
			}
		}
		if(timer == 0) {
			GameManager gm = new GameManager();
			gm.setState(state.run);
			main.getinstance().hasStarted = true;
			for(int i=0;i<gm.getPlayersSize();i++) {
				Player p = gm.getPlayerList().get(i);
				p.teleport(LocationAPI.getLocation("run"));
				p.sendMessage("§eInfo: §7Try to reach the other side to be §cqualified!");
				p.getInventory().clear();
				p.setHealth(20);
				p.getActivePotionEffects().clear();
				p.getInventory().setHelmet(null);
				p.getInventory().setChestplate(null);
				p.getInventory().setLeggings(null);
				p.getInventory().setBoots(null);
				p.playSound(p.getLocation(), Sound.NOTE_BASS_DRUM, 4.0f, 6.0f);

			}
			for(Player pls : main.getinstance().spectate) {
				pls.teleport(LocationAPI.getLocation("run"));

			}
			cancel();
		}
		
		
		timer--;
	}

}
