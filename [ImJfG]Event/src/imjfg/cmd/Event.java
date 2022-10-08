package imjfg.cmd;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import imjfg.cooldown.nextGameCooldown;
import imjfg.cooldown.starting;
import imjfg.main.main;
import imjfg.utils.GameManager;
import imjfg.utils.LocationAPI;

public class Event extends GameManager implements CommandExecutor {



	

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player p = (Player) sender;
		if (p.hasPermission("event.host.admin")) {
			if (args.length == 1) {
				String cmd = args[0];

				if (cmd.equalsIgnoreCase("setlobby")) {
					LocationAPI.setLocation(p.getLocation(), "lobby");
				
					p.sendMessage(main.Prefix + "§aLobby Spawn Has Been Set!");
				}
				else if(cmd.equalsIgnoreCase("nextGame")) {
					if(main.getinstance().hasStarted == true) {
					nextGameCooldown nt = new nextGameCooldown();
					nt.runTaskTimer(main.getinstance(), 0,20);
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 4.0f, 3.0f);
					}
				}
				else if (cmd.equalsIgnoreCase("admin")) {
					getPlayerList().remove(p);
					p.sendMessage(main.Prefix + "§cAdmin Mode §ais on");
					p.getInventory().clear();
					getMangerList().add(p);
					p.setPlayerListName("§7[§cAdmin§7] §c" + p.getName());
					p.getInventory().setItem(0, tp());
					p.getInventory().setItem(4, eliminate());
					p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100000000, 1));
					p.setGameMode(GameMode.CREATIVE);
				} else if (cmd.equalsIgnoreCase("start")) {
					if (getPlayersSize() == 24) {
						main.getinstance().hasStarted = true;
						starting st = new starting();
						st.runTaskTimer(main.getinstance(), 0, 20);
						Bukkit.broadcastMessage(main.Prefix + "§eEvent Cooldown Started!");
					} else {
						p.sendMessage(main.Prefix + "§cYou Cant start the event current Players §e" + getPlayersSize()
								+ "/24");
					}
				} else if (cmd.equalsIgnoreCase("help")) {
					p.sendMessage("§e§m--------------------");
					p.sendMessage("§e/e §cstart");
					p.sendMessage("§6/e admin");
					p.sendMessage("§6/e setlobby");
					p.sendMessage("§6/e setrun");
					p.sendMessage("§6/e setspectate [n/6]");
					p.sendMessage("§6/e setboatmlg");
					p.sendMessage("§6/e setparkour");
					p.sendMessage("§6/e setspeedbridge");
					p.sendMessage("§6/e setsandbridge");
					p.sendMessage("§6/e setdoublejump");
					p.sendMessage("§6/e setshooting [n/20]");
					p.sendMessage("§6/e setlavapath [n/15]");
					p.sendMessage("§6/e setairshooting [n/6]");
					p.sendMessage("§6/e setportal [n/2]");
					p.sendMessage("§e§m--------------------");

				} else if (cmd.equalsIgnoreCase("setrun")) {
					LocationAPI.setLocation(p.getLocation(), "run");
					p.sendMessage(main.Prefix + "§aRun Spawn Has Been Set!");
				} else if (cmd.equalsIgnoreCase("setparkour")) {
					LocationAPI.setLocation(p.getLocation(), "parkour");
					p.sendMessage(main.Prefix + "§aParkour Spawn Has Been Set!");
				} else if (cmd.equalsIgnoreCase("setboatmlg")) {
					LocationAPI.setLocation(p.getLocation(), "boatmlg");
					p.sendMessage(main.Prefix + "§aBoatmlg Spawn Has Been Set!");
				} else if (cmd.equalsIgnoreCase("setspeedbridge")) {
					LocationAPI.setLocation(p.getLocation(), "speedbridge");
					p.sendMessage(main.Prefix + "§aSpeedBridge Spawn Has Been Set!");
				} else if (cmd.equalsIgnoreCase("setsandbridge")) {
					LocationAPI.setLocation(p.getLocation(), "sandbridge");
					p.sendMessage(main.Prefix + "§aSandBridge Spawn Has Been Set!");
				} else if (cmd.equalsIgnoreCase("setdoublejump")) {
					LocationAPI.setLocation(p.getLocation(), "doublejump");
					p.sendMessage(main.Prefix + "§aDoubleJump Spawn Has Been Set!");
				}

			} else if (args.length == 2) {
				String cmd = args[0];
				if (cmd.equalsIgnoreCase("setshooting")) {
					String spawnNumber = args[1];
					if (Integer.parseInt(spawnNumber) <= 20) {
						LocationAPI.setLocation(p.getLocation(), "shooting" + spawnNumber);
						p.sendMessage(main.Prefix + "§aShooting Spawn §e" + spawnNumber + " §a Has Been Set!");
					} else {
						p.sendMessage(main.Prefix + "§cMax Spawns Are 20");
					}

				} else if (cmd.equalsIgnoreCase("setlavapath")) {
					String spawnNumber = args[1];
					if (Integer.parseInt(spawnNumber) <= 15) {
						LocationAPI.setLocation(p.getLocation(), "lavapath" + spawnNumber);
						p.sendMessage(main.Prefix + "§aLavaPath Spawn §e" + spawnNumber + " §a Has Been Set!");
					} else {
						p.sendMessage(main.Prefix + "§cMax Spawns Are 15");
					}

				} else if (cmd.equalsIgnoreCase("setairshooting")) {
					String spawnNumber = args[1];
					if (Integer.parseInt(spawnNumber) <= 6) {
						LocationAPI.setLocation(p.getLocation(), "airshooting" + spawnNumber);
						p.sendMessage(main.Prefix + "§aAir Shooting Spawn §e" + spawnNumber + " §a Has Been Set!");
					} else {
						p.sendMessage(main.Prefix + "§cMax Spawns Are 6");
					}

				} else if (cmd.equalsIgnoreCase("setportal")) {
					String spawnNumber = args[1];
					if (Integer.parseInt(spawnNumber) <= 2) {
						LocationAPI.setLocation(p.getLocation(), "portal" + spawnNumber);
						p.sendMessage(main.Prefix + "§aPortal Spawn §e" + spawnNumber + " §a Has Been Set!");
					} else {
						p.sendMessage(main.Prefix + "§cMax Spawns Are 2");
					}

				}
				else if (cmd.equalsIgnoreCase("setspectate")) {
					String spawnNumber = args[1];
					if (Integer.parseInt(spawnNumber) <= 6) {
						LocationAPI.setLocation(p.getLocation(), "sp" + spawnNumber);
						p.sendMessage(main.Prefix + "§aSpectate Spawn §e" + spawnNumber + " §a Has Been Set!");
					} else {
						p.sendMessage(main.Prefix + "§cMax Spawns Are 6");
					}

				}

			}
		}

		return false;
	}

	static ItemStack tp() {
		ItemStack item = new ItemStack(Material.COMPASS);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§eSpectate Menu");
		item.setItemMeta(meta);
		return item;
	}

	static ItemStack eliminate() {
		ItemStack item = new ItemStack(Material.WATCH);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§cEliminate Menu");
		item.setItemMeta(meta);
		return item;
	}

}
