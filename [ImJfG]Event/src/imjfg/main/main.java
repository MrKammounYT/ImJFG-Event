package imjfg.main;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import BoatMLG.BoatMLG;
import DoubleJump.DoubleJump;
import Parkour.Parkour;
import Run.Run;
import Shooting.Shooting;
import airshooting.aevent;
import deathrun.DeathRun;
import fastbridge.FastBridge;
import imjfg.cmd.Event;
import imjfg.events.General;
import imjfg.events.Join;
import imjfg.events.TpMenu;
import imjfg.events.eliminate;
import imjfg.events.quit;
import imjfg.utils.GameManager;
import imjfg.utils.PlayerManger;
import imjfg.utils.state;
import lavapath.LavaPath;
import pickaxe.PickAxe;
import portalopen.OpenPortal;
import sandbridge.SandBridge;

public class main extends JavaPlugin {
private static main instance;
public static String Prefix = "§a§lImJFG §7» ";	
public static HashMap<Player, PlayerManger> Pl = new HashMap<>();
public ArrayList<Player> players  = new ArrayList<>();
public ArrayList<Player> manager  = new ArrayList<>();
public ArrayList<Player> spectate  = new ArrayList<>();
public state states;
public  boolean hasStarted = false;
public state lastState;

	@Override
	public void onEnable() {
	 instance = this;
	 GameManager gm= new GameManager();
	 hasStarted = false;
	 gm.setState(state.waiting);
	 registerEvents();
	 getCommand("e").setExecutor(new Event());
	 getCommand("event").setExecutor(new Event());
	 createworld("deathrun");
	 createworld("pickaxe");
	 for (World w : Bukkit.getWorlds()) {
         w.setAnimalSpawnLimit(0);
         w.setMonsterSpawnLimit(0);
         w.setWeatherDuration(0);
         w.setStorm(false);
         w.setThundering(false);
         w.setTime(100);
         w.setFullTime(100);
     }
	}
	
	public void createworld(String name) {
		WorldCreator wc = new WorldCreator(name);
		 wc.environment(World.Environment.NORMAL);
		 wc.createWorld();
	}
	

	public static main getinstance() {
		return instance;
	}
	
	public void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new Join(), instance);
		pm.registerEvents(new eliminate(), instance);
		pm.registerEvents(new quit(), this);
		pm.registerEvents(new TpMenu(), instance);
		pm.registerEvents(new General(), instance);
		
		pm.registerEvents(new aevent(), instance);
		pm.registerEvents(new FastBridge(), instance);
		pm.registerEvents(new PickAxe(), instance);
		pm.registerEvents(new OpenPortal(), instance);
		pm.registerEvents(new SandBridge(), instance);
		pm.registerEvents(new Run(), instance);
		pm.registerEvents(new Shooting(), instance);
		pm.registerEvents(new Parkour(), instance);
		pm.registerEvents(new LavaPath(), instance);
		pm.registerEvents(new BoatMLG(), instance);
		pm.registerEvents(new DoubleJump(), instance);
		pm.registerEvents(new DeathRun(), instance);





	}
	
	
}
