package net.mywk.ChunkBlock;


import net.mywk.util.Block;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import static net.mywk.ChunkBlock.config.Config.*;
import static net.mywk.util.Utils.isInt;
import static net.mywk.util.Utils.listing;
import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class CommandsHandler implements CommandExecutor
{
	private final ChunkBlock chunkblock;
	private final BukkitScheduler scheduler;

	CommandsHandler(ChunkBlock chunkblock) {
		this.chunkblock = chunkblock;
		scheduler = chunkblock.getServer().getScheduler();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.AQUA + "ChunkBlock v" + chunkblock.getDescription().getVersion() + " by Mywk");
				if(sender.hasPermission("chunkblock.list"))
					sender.sendMessage(ChatColor.AQUA + "/cb list - Shows dupe attempts in log file");
			} else {
				final String command = args[0].toLowerCase();
				if (command.equals("list") && sender.hasPermission("chunkblock.list")) {
					FileInputStream fstream = new FileInputStream(ChunkBlock.getInstance().getDataFolder().getAbsolutePath()+"\\logs.txt");
					 Scanner s = new Scanner(fstream);
				        while(s.hasNextLine()) {
				          sender.sendMessage(s.nextLine());
				        }
				}
			}
		} catch (final Exception ex) {
			sender.sendMessage(ChatColor.RED + "Error, check server.log");
			getLogger().log(Level.WARNING, "Exception: ", ex);
		}
		return true;
	}

}
