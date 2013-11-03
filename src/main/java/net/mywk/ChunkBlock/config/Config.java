package net.mywk.ChunkBlock.config;

import net.mywk.ChunkBlock.*;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.zip.DataFormatException;


public class Config
{

	public static SimpleDateFormat formatter;

	public static void load(ChunkBlock chunkblock) throws DataFormatException, IOException {
		final ConfigurationSection config = chunkblock.getConfig();
		final Map<String, Object> def = new HashMap<String, Object>();
		def.put("eatBlockOnDupe", "true");
		chunkblock.saveConfig();
		chunkblock.eatBlock = config.getBoolean("eatBlockOnDupe");
	}
}
