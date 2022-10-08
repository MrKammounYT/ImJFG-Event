package imjfg.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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

public class TpMenu extends GameManager implements Listener {

	

	@EventHandler
	public void onOpenTp(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR) {
			if(e.getItem() == null)return;
			if(!e.getItem().hasItemMeta())return;
			if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eSpectate Menu")) {
			Player p = e.getPlayer();
			Inventory inv = Bukkit.createInventory(null,36, "§eSpectate Menu");
			for(int i=0;i<getPlayersSize();i++) {
				inv.setItem(i, createSkull(main.getinstance().players.get(i)));
			}
			
			p.openInventory(inv);
			}
		}
	}
	@EventHandler
	public void onTpClick(InventoryClickEvent e) {
		if(e.getInventory()==null)return;
		if(e.getCurrentItem() == null)return;
		if(e.getInventory().getTitle().equals("§eSpectate Menu")) {
			e.setCancelled(true);
			if(e.getCurrentItem().hasItemMeta()) {
				if(e.getCurrentItem().getItemMeta().hasDisplayName()) {

				if(e.getCurrentItem().getType() == Material.SKULL_ITEM) {
					e.setCancelled(true);
					Player p = (Player) e.getWhoClicked();
					Player target = Bukkit.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName());
					p.teleport(target);
					p.sendMessage("§fNow spectating: §e"+target.getName());
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
