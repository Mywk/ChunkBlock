package net.mywk.ChunkBlock.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;

import net.mywk.ChunkBlock.ChunkBlock;
import net.mywk.ChunkBlock.KnownDupes;
import net.mywk.ChunkBlock.config.Config;
import net.mywk.util.BukkitUtils;

public class BlockPlaceLogging extends LoggingListener
{
	
	  public BlockPlaceLogging(ChunkBlock cb)
	  {
	    super(cb);
	  }

	  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	  	public void onBlockPlace(BlockPlaceEvent event) {

	  			//final BlockState before = event.getBlockReplacedState();
	  			//final BlockState after = event.getBlockPlaced().getState();
	  			final Location loc = event.getPlayer().getLocation();
	  			final String playerName = event.getPlayer().getName();

	  			KnownDupes k = new KnownDupes(event);
	  			
	  			// Router + Conveyour belts dupe blocker
	  			boolean dupe = k.blockPlaceEvent(3131, 3121, BlockFace.UP) ||
	  			k.blockPlaceEvent(3121, 3131, BlockFace.DOWN);
	  			
	  			// Was trying to dispose the class and Java apparently already does it lol
	  			
	  			if(dupe)
	  			{
	  				event.getPlayer().sendMessage(ChatColor.DARK_RED + "You are trying to perform an illegal action!");
	  				
  				    if(Config.eatBlockOnDupe)
  				    {
  				    	event.getPlayer().sendMessage(ChatColor.YELLOW + "Here, have a potato instead.");
  				    	event.getPlayer().getInventory().setItemInHand(new ItemStack(Material.POTATO_ITEM, 1));
  				    }
  				    
	  				    String msg = "Player " + playerName + " attempted to perform a dupe at " + (int)loc.getX() + ", " + (int)loc.getY() + ", " + (int)loc.getZ() + "!";
	  				    Bukkit.broadcast(ChatColor.RED + msg, "chunkblock.notify");
	  				    
	  				    ChunkBlock.getInstance().Log(msg);
	  				    
	  				    event.setCancelled(true);
	  				    
	  			}
	  }
	}
