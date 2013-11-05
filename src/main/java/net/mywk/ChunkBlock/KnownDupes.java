package net.mywk.ChunkBlock;


import net.mywk.ChunkBlock.config.Config;
import net.mywk.util.Block;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import static net.mywk.ChunkBlock.config.Config.*;
import static net.mywk.util.Utils.isInt;
import static net.mywk.util.Utils.listing;
import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class KnownDupes
{
	
	private PlayerInteractEvent event;
	private org.bukkit.block.Block block;
	
	public KnownDupes(PlayerInteractEvent event, org.bukkit.block.Block block) {
		this.event = event;
		this.block = block;
	}
	
	public boolean playerInteractEvent(org.bukkit.block.Block block)
	{
		final Material type = block.getType();

		// Router + Conveyour belts dupe blocker
		if(type.getId() == Config.routerId)
		{
			if(net.mywk.util.BukkitUtils.foundBlockOnSide(block, Config.conveyorBeltId, BlockFace.UP)  || (event.getPlayer().getItemInHand().getTypeId() == Config.conveyorBeltId && event.getBlockFace() == BlockFace.UP))
				return true;
		}
		else if(type.getId() == Config.conveyorBeltId)
		{
			if(net.mywk.util.BukkitUtils.foundBlockOnSide(block, Config.routerId, BlockFace.DOWN)  || (event.getPlayer().getItemInHand().getTypeId() == Config.routerId && event.getBlockFace() == BlockFace.DOWN))
				return true;
		}
		
		
		return false;
	}
	
	public void checkDupe(String method)
	{
			
			event.getPlayer().sendMessage(ChatColor.DARK_RED + "You are trying to perform an illegal action!");
				
		    if(Config.eatBlockOnDupe)
		    {
		    	event.getPlayer().sendMessage(ChatColor.YELLOW + "Here, have a potato instead.");
		    	event.getPlayer().getInventory().setItemInHand(new ItemStack(Material.POTATO_ITEM, 1));
		    }
	    
	    	Location loc = event.getClickedBlock().getLocation();
		    String msg = "Player " + event.getPlayer().getName() + " attempted to perform a dupe at " + (int)loc.getX() + ", " + (int)loc.getY() + ", " + (int)loc.getZ() + "!";
		    Bukkit.broadcast(ChatColor.RED + msg, "chunkblock.notify");
		    
		    if(method.equals("event"))
		    	event.setCancelled(true);
		    else if(method.equals("forcePlace"))
		    {
				final Location where = block.getRelative(event.getBlockFace()).getLocation();
            	if(where.getBlock().getType() != Material.AIR)
            	{
            		where.getBlock().setType(Material.AIR);
            	}

		    }
		    
		    ChunkBlock.getInstance().Log(msg);
		}

}
