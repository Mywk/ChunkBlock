package net.mywk.ChunkBlock;


import net.mywk.util.Block;

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

	BlockPlaceEvent event = null;
	
	public KnownDupes(BlockPlaceEvent e) {
		event = e;
	}
	
	public boolean blockPlaceEvent(int id1, int id2, BlockFace face)
	{
			final org.bukkit.block.Block block = event.getBlock();
			final Material type = event.getBlock().getType();

		if(type.getId() == 3131)
		{
			if(net.mywk.util.BukkitUtils.foundBlockOnSide(event.getBlock(), 3121, BlockFace.UP))
				return true;
		}
		else if(type.getId() == 3121)
		{
			if(net.mywk.util.BukkitUtils.foundBlockOnSide(event.getBlock(), 3131, BlockFace.DOWN))
				return true;
		}
		
		
		return false;
	}

}
