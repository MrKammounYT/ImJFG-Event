package imjfg.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import imjfg.main.main;
import imjfg.utils.GameManager;
import imjfg.utils.state;

public class eliminate extends GameManager implements Listener {

	
	@EventHandler
	public void onOpenEliminate(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR) {
			if(e.getItem() == null)return;
			if(!e.getItem().hasItemMeta())return;
			if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cEliminate Menu")) {
			Player p = e.getPlayer();
			Inventory inv = Bukkit.createInventory(null,36, "§cEliminate Menu");
			for(int i=0;i<getPlayersSize();i++) {
				inv.setItem(i, createSkull(main.getinstance().players.get(i)));
			}
			
			p.openInventory(inv);
			}
		}
	}
	@EventHandler
	public void onEliminateClick(InventoryClickEvent e) {
		if(e.getInventory()==null)return;
		if(e.getInventory().getTitle().equals("§cEliminate Menu")) {
			if(e.getCurrentItem() == null)return;
			if(e.getCurrentItem().hasItemMeta()) {
				if(e.getCurrentItem().getType() == Material.SKULL_ITEM) {
					e.setCancelled(true);
					if(!isState(state.waiting)) {
					Player p = (Player) e.getWhoClicked();
					Player target = Bukkit.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName());
					p.teleport(target);
					target.sendMessage(main.Prefix + "&cNice try, Good Luck next time");
					target.playSound(p.getLocation(), Sound.VILLAGER_NO, 4.0f, 5.0f);
					Bukkit.broadcastMessage("§c"+p.getDisplayName() + " §4Has Been Disqualified!");
					removePlayer(p);
					p.kickPlayer("gg,GoodLuck Next Time");
					}else {
						e.getWhoClicked().sendMessage("§cyou cant use this before the game starts!");
					}
				}
			}
		}
	}
	
	public ItemStack createSkull(Player p) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM,1,(short)3);
		SkullMeta meta =(SkullMeta) item.getItemMeta();
		meta.setOwner(p.getName());
		meta.setDisplayName(p.getName());
		item.setItemMeta(meta);
		return item;
	}
}
