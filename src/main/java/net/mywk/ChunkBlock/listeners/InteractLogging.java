package net.mywk.ChunkBlock.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.mywk.ChunkBlock.ChunkBlock;
import net.mywk.ChunkBlock.KnownDupes;
import net.mywk.ChunkBlock.config.Config;
import net.mywk.util.BukkitUtils;

public class InteractLogging extends LoggingListener
{
	public InteractLogging(ChunkBlock cb) {
		super(cb);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		 	
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
				final Block clicked = event.getClickedBlock();
				final KnownDupes k = new KnownDupes(event, clicked);
				
				if(k.playerInteractEvent(event.getClickedBlock()))
					k.checkDupe("event");
				
				final String playerName = event.getPlayer().getName();
				
				// Force wrench place method
					if(event.getPlayer().getItemInHand().getTypeId() == Config.forceWrenchPlaceId)
					{
						
						ChunkBlock.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(ChunkBlock.getInstance(), new Runnable() {
		                    public void run() {
	
		                			if(k.playerInteractEvent(clicked))
		                				k.checkDupe("forcePlace");
		                        	
		                    }
		                  }, 1L);
					}				
		}	
				
	}
}
