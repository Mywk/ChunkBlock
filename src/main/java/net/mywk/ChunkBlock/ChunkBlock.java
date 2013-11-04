package net.mywk.ChunkBlock;

import net.mywk.ChunkBlock.config.Config;
import net.mywk.ChunkBlock.listeners.BlockPlaceLogging;
import net.mywk.util.BukkitUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;

import static net.mywk.ChunkBlock.config.Config.*;
import static org.bukkit.Bukkit.getPluginManager;

public class ChunkBlock extends JavaPlugin
{
        private static ChunkBlock chunkblock = null;
        private CommandsHandler cbCommandsHandler;
        private Consumer consumer = null;
        private Timer timer = null;
        private boolean errorLoading = false;
        private FlatLogger log = null;
        

        public static ChunkBlock getInstance() {
                return chunkblock;
        }

        public CommandsHandler getCommandsHandler() {
                return cbCommandsHandler;
        }
        
        public Consumer getConsumer() {
          return this.consumer;
        }
        
        public void Log(String message)
        {
        	log.logToFile(message);
        }

        @Override
        public void onLoad() {
                chunkblock = this;       
                try {
                    Config.load(this);
                   } catch (Exception ex) {
                    getLogger().severe("Error loading: " + ex.getMessage());
                    this.errorLoading = true;
                    return;
              }
              this.consumer = new Consumer(this);
        }

        @Override
        public void onEnable() {
                final PluginManager pm = getPluginManager();
                if (errorLoading) {
                        pm.disablePlugin(this);
                        return;
                }
                cbCommandsHandler = new CommandsHandler(this);
                getCommand("cb").setExecutor(cbCommandsHandler);

                pm.registerEvents(new BlockPlaceLogging(this), this);
                log = new FlatLogger();
        }

    	

        @Override
        public void onDisable() {
                if (timer != null)
                        timer.cancel();
                getServer().getScheduler().cancelTasks(this);
                
        }

        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
                return true;
        }

        public boolean hasPermission(CommandSender sender, String permission) {
                return sender.hasPermission(permission);
        }

}