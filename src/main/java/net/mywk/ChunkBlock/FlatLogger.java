package net.mywk.ChunkBlock;

import net.mywk.ChunkBlock.*;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.zip.DataFormatException;


public class FlatLogger
{
	
	private File dataFolder = null;
	private File saveTo = null;
	public FlatLogger()
   {
		enable();
   }
	

	public void enable()
	{
		try
		{
			
			dataFolder = ChunkBlock.getInstance().getDataFolder();
	        if(!dataFolder.exists())
	        {
	            dataFolder.mkdir();
	        }
	        
	        if(!dataFolder.exists())
	        {
	            dataFolder.mkdir();
	        }
	
	        saveTo = new File(dataFolder, "logs.txt");
	        if (!saveTo.exists())
	        {
	            saveTo.createNewFile();
	        }
	        
		}
		catch (IOException e)
        {
 
			System.out.println("Error enabling log file!");
            e.printStackTrace();
 
        }
	}
	
public void logToFile(String message)
{
 
        try
        {
 
            FileWriter fw = new FileWriter(saveTo, true);
 
            PrintWriter pw = new PrintWriter(fw);
 
            pw.println(message);
 
            pw.flush();
 
            pw.close();
            
 
        } catch (IOException e)
        {
 
            e.printStackTrace();
 
        }
 
    }
}