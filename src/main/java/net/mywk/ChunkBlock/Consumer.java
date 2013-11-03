package net.mywk.ChunkBlock;

import net.mywk.ChunkBlock.ChunkBlock;
import net.mywk.ChunkBlock.config.Config;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

import static net.mywk.ChunkBlock.config.Config.*;
import static net.mywk.util.BukkitUtils.*;
import static org.bukkit.Bukkit.getLogger;

public class Consumer extends TimerTask
{
	private final ChunkBlock ChunkBlock;

	Consumer(ChunkBlock ChunkBlock) {
		this.ChunkBlock = ChunkBlock;
		try {
			Class.forName("PlayerLeaveRow");
		} catch (final ClassNotFoundException ex) {
		}
	}

	
	@Override
	public void run() {
		//logging
	}

	public void writeToFile() throws FileNotFoundException {
		/*final long time = System.currentTimeMillis();
		final Set<String> insertedPlayers = new HashSet<String>();
		int counter = 0;
		new File("plugins/ChunkBlock/import/").mkdirs();
		PrintWriter writer = new PrintWriter(new File("plugins/ChunkBlock/import/queue-" + time + "-0.sql"));
		while (!queue.isEmpty()) {
			final Row r = queue.poll();
			if (r == null)
				continue;
			for (final String player : r.getPlayers())
				if (!playerIds.containsKey(player) && !insertedPlayers.contains(player)) {
					writer.println("INSERT IGNORE INTO `lb-players` (playername) VALUES ('" + player + "');");
					insertedPlayers.add(player);
				}
			for (final String insert : r.getInserts())
				writer.println(insert);
			counter++;
			if (counter % 1000 == 0) {
				writer.close();
				writer = new PrintWriter(new File("plugins/ChunkBlock/import/queue-" + time + "-" + counter / 1000 + ".sql"));
			}
		}
		writer.close();*/
	}


}
