package imjfg.cooldown;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import imjfg.main.main;
import imjfg.utils.GameManager;
import imjfg.utils.LocationAPI;
import imjfg.utils.state;

public class nextGameCooldown extends BukkitRunnable {

	int timer = 30;
	public void cage(Location f) {
		Location lc = f.clone().add(0,0,0);
      Location upper = lc.clone().add(0,2,0);//the block above the Player
      lc.clone().add(1,0,0).getBlock().setType(Material.BARRIER);
      lc.clone().add(0,0,1).getBlock().setType(Material.BARRIER);
      lc.clone().add(-1,0,0).getBlock().setType(Material.BARRIER);
      lc.clone().add(0,0,-1).getBlock().setType(Material.BARRIER);
      upper.getBlock().setType(Material.BARRIER);
       
      


       
	}
	@Override
	public void run() {

		for (Player pls : Bukkit.getOnlinePlayers()) {
			pls.setLevel(timer);
		}
		if(timer == 30) {
			GameManager gm = new GameManager();
			Bukkit.broadcastMessage("§6§m--------------------------");
			Bukkit.broadcastMessage("§e§lCurrent Players §b"+ gm.getPlayersSize()+"§b/24");
			Bukkit.broadcastMessage("§6§m--------------------------");
		}

		if (timer == 25 || timer == 20 || timer == 10 || timer == 5) {
			Bukkit.getConsoleSender().sendMessage(main.Prefix + "§eNext Game In §b" + timer);
			for (Player pls : Bukkit.getOnlinePlayers()) {
				pls.playSound(pls.getLocation(), Sound.CLICK, 4.0f, 6.0f);
			}
		}
		if (timer == 0) {
	
			GameManager gm = new GameManager();
			Bukkit.broadcastMessage	( main.Prefix + "§eGame Number:§b" + gm.getGameNumber() + " §eIn §b" + timer);
			if (gm.isLastState(state.run)) {
				// tp to parkour
				gm.setState(state.parkour);
				for (int i = 0; i < gm.getPlayersSize(); i++) {
					Player p = gm.getPlayerList().get(i);
					p.teleport(LocationAPI.getLocation("parkour"));
					p.getInventory().clear();
					p.setGameMode(GameMode.SURVIVAL);

				}
				for (Player spectaters : gm.getSpectateList()) {
					spectaters.teleport(LocationAPI.getLocation("parkour"));
				}
			} else if (gm.isLastState(state.parkour)) {
				// tp to parkour
				gm.setState(state.shooting);
				for (int i = 0; i < gm.getPlayersSize(); i++) {
					Player p = gm.getPlayerList().get(i);
					p.teleport(LocationAPI.getLocation("shooting" + i));
					p.getInventory().clear();
					p.getInventory().setItem(0, bow());
					p.getInventory().setItem(17, new ItemStack(Material.ARROW, 1));
					p.setGameMode(GameMode.SURVIVAL);
					cage(p.getLocation());
				}
				for (Player spectaters : gm.getSpectateList()) {
					spectaters.teleport(LocationAPI.getLocation("sp1"));
				}
			} else if (gm.isLastState(state.shooting)) {
				for (int i = 0; i < gm.getPlayersSize(); i++) {
					Player p = gm.getPlayerList().get(i);
					p.getInventory().clear();
					double x = new Random().nextInt(1000);
					double z = new Random().nextInt(1000);
					p.teleport(new Location(Bukkit.getWorld("deathrun"), x, 70, z));
					p.setGameMode(GameMode.SURVIVAL);

				}
				gm.setState(state.deathrun);
				for (Player spectaters : gm.getSpectateList()) {
					double x = new Random().nextInt(1000);
					double z = new Random().nextInt(1000);
					spectaters.teleport(new Location(Bukkit.getWorld("deathrun"), x, 70, z));


				}
			} else if (gm.isLastState(state.deathrun)) {
				// tp to parkour
				gm.setState(state.fastbridge);
				for (int i = 0; i < gm.getPlayersSize(); i++) {
					Player p = gm.getPlayerList().get(i);
					p.teleport(LocationAPI.getLocation("speedbridge"));
					p.getInventory().clear();
					p.getInventory().setItem(0, new ItemStack(Material.WOOD, 64));
					p.getInventory().setItem(1, new ItemStack(Material.DIAMOND_AXE, 1));
					p.getInventory().setItem(2, new ItemStack(Material.WOOD, 64));
					p.getInventory().setItem(3, new ItemStack(Material.WOOD, 64));
					p.getInventory().setItem(4, new ItemStack(Material.WOOD, 64));
					p.getInventory().setItem(5, new ItemStack(Material.WOOD, 64));
					p.getInventory().setItem(6, new ItemStack(Material.WOOD, 64));
					p.getInventory().setItem(7, new ItemStack(Material.WOOD, 64));
					p.getInventory().setItem(8, new ItemStack(Material.WOOD, 64));
					p.setGameMode(GameMode.SURVIVAL);

				}
				for (Player spectaters : gm.getSpectateList()) {
					spectaters.teleport(LocationAPI.getLocation("sp2"));
				}
			} else if (gm.isLastState(state.fastbridge)) {
				gm.setState(state.lavapath);
				for (int i = 0; i < gm.getPlayersSize(); i++) {
					Player p = gm.getPlayerList().get(i);
					p.teleport(LocationAPI.getLocation("lavapath" + i));
					main.Pl.get(p).setspawnNumber(i);
					p.getInventory().clear();
					p.setGameMode(GameMode.SURVIVAL);

					for (int x = 0; x < p.getInventory().getSize(); x++) {
						p.getInventory().setItem(x, new ItemStack(Material.BOAT));

					}
				}
				for (Player spectaters : gm.getSpectateList()) {
					spectaters.teleport(LocationAPI.getLocation("sp3"));
				}
			}
			else if(gm.isLastState(state.lavapath)) {
				gm.setState(state.sandwalljump);
				for (int i = 0; i < gm.getPlayersSize(); i++) {
					Player p = gm.getPlayerList().get(i);
					p.teleport(LocationAPI.getLocation("sandbridge"));
					p.getInventory().clear();
					p.setGameMode(GameMode.SURVIVAL);

					for (int x = 0; x < p.getInventory().getSize(); x++) {
						p.getInventory().setItem(x, new ItemStack(Material.SAND,64));
						
					}
				}
				for (Player spectaters : gm.getSpectateList()) {
					spectaters.teleport(LocationAPI.getLocation("sp4"));
				}
			}
			else if(gm.isLastState(state.sandwalljump)) {
				gm.setState(state.boatmlg);
				for (int i = 0; i < gm.getPlayersSize(); i++) {
					Player p = gm.getPlayerList().get(i);
					p.teleport(LocationAPI.getLocation("boatmlg"));
					p.getInventory().clear();
					p.setGameMode(GameMode.SURVIVAL);

					for (int x = 0; x < p.getInventory().getSize(); x++) {
						p.getInventory().setItem(x, new ItemStack(Material.BOAT));
					}
				}	
				for (Player spectaters : gm.getSpectateList()) {
					spectaters.teleport(LocationAPI.getLocation("boatmlg"));
				}
				BoatWait bw = new BoatWait();
				bw.runTaskTimer(main.getinstance(), 0, 20);
			}
			else if(gm.isLastState(state.boatmlg)) {
				gm.setState(state.doublejump);
				for (int i = 0; i < gm.getPlayersSize(); i++) {
					Player p = gm.getPlayerList().get(i);
					p.teleport(LocationAPI.getLocation("doublejump"));
					p.getInventory().clear();
					p.setGameMode(GameMode.SURVIVAL);

				}	
				for (Player spectaters : gm.getSpectateList()) {
					spectaters.teleport(LocationAPI.getLocation("doublejump"));
				}
			}
			else if(gm.isLastState(state.doublejump)) {
				gm.setState(state.Airshooting);
				for (int i = 0; i < gm.getPlayersSize(); i++) {
					Player p = gm.getPlayerList().get(i);
					p.teleport(LocationAPI.getLocation("airshooting"+i));
					p.getInventory().clear();
					p.getInventory().setItem(0, bow());
					p.setGameMode(GameMode.SURVIVAL);

					p.getInventory().setItem(17, new ItemStack(Material.ARROW, 1));
					main.Pl.get(p).setspawnNumber(i);
				}	
				for (Player spectaters : gm.getSpectateList()) {
					spectaters.teleport(LocationAPI.getLocation("sp5"));
				}
			}
			else if(gm.isLastState(state.Airshooting)) {
				gm.setState(state.pickaxe);
				for (int i = 0; i < gm.getPlayersSize(); i++) {
					Player p = gm.getPlayerList().get(i);
					p.getInventory().clear();
					p.setGameMode(GameMode.SURVIVAL);
					p.getInventory().setItem(0, bow());
					p.getInventory().setItem(17, new ItemStack(Material.ARROW, 1));
					double x = new Random().nextInt(1000);
					double z = new Random().nextInt(1000);
					p.setGameMode(GameMode.SURVIVAL);

					p.teleport(new Location(Bukkit.getWorld("pickaxe"), x, 70, z));
				}	
				for (Player spectaters : gm.getSpectateList()) {
					double x = new Random().nextInt(1000);
					double z = new Random().nextInt(1000);
					spectaters.teleport(new Location(Bukkit.getWorld("pickaxe"), x, 70, z));
				}
			}
			else if(gm.isLastState(state.pickaxe)) {
				gm.setState(state.portalopen);
				for (int i = 0; i < gm.getPlayersSize(); i++) {
					Player p = gm.getPlayerList().get(i);
					p.getInventory().clear();
					p.teleport(LocationAPI.getLocation("portal"+i));
					p.setGameMode(GameMode.SURVIVAL);
					p.getInventory().addItem(new ItemStack(Material.DIAMOND_PICKAXE));
					p.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
					p.getInventory().addItem(new ItemStack(Material.LAVA_BUCKET));



				}	
				for (Player spectaters : gm.getSpectateList()) {
					spectaters.teleport(LocationAPI.getLocation("sp6"));
				}
			}else {
				
			}
			cancel();

			

		}
		timer--;
	}

	static ItemStack bow() {
		ItemStack item = new ItemStack(Material.BOW);
		item.addEnchantment(Enchantment.ARROW_INFINITE, 4);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§eShooting Bow");
		item.setItemMeta(meta);
		return item;
	}

}
