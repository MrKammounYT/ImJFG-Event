package imjfg.cooldown;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import imjfg.main.main;

public class BoatWait extends BukkitRunnable {

	int timer = 20;
	public static boolean canmove = false;
	@Override
	public void run() {
		
		if(timer == 10 || timer == 15 || timer == 5 || timer == 1 || timer == 2 || timer ==3) {
			Bukkit.broadcastMessage(main.Prefix + " §eYou can move in §c"+timer);
		}
		
		if(timer ==0) {
			Bukkit.broadcastMessage(main.Prefix + " §cGo!");
			canmove = true;
			cancel();
		}
		
		timer--;
	}

}
