
package me.zeus.ExodusBans;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

import me.zeus.ExodusBans.Commands.CMD_Check;
import me.zeus.ExodusBans.Commands.CMD_TempBan;
import me.zeus.ExodusBans.Commands.CMD_Unban;
import me.zeus.ExodusBans.Events.EVT_Join;
import me.zeus.ExodusBans.Objects.Ban;
import me.zeus.ExodusBans.Utils.MathHelper;



public class ExodusBans extends JavaPlugin
{
    
    
    private static ExodusBans instance;
    Map<String, Ban> bans;
    File rootDir;
    File bansDir;
    File config;
    boolean firstTime;
    String loginFormat;
    String kickFormat;
    String broadcastFormat;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
     */
    public void onEnable()
    {
        // declare instance
        instance = this;
        
        // create temp data
        bans = new HashMap<String, Ban>();
        
        // create files
        init_files();
        
        // init config
        init_config();
        
        // If not plugin first time, then load data
        if (!firstTime)
        {
            init_loadBans();
        }
        
        getCommand("extempban").setExecutor(new CMD_TempBan());
        getCommand("exunban").setExecutor(new CMD_Unban());
        getCommand("excheck").setExecutor(new CMD_Check());
        getServer().getPluginManager().registerEvents(new EVT_Join(), this);
        
        checkBans();
    }
    
    
    
    /*
     * (non-Javadoc)
     * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
     */
    public void onDisable()
    {
        instance = null;
        init_saveBans();
    }
    
    
    
    /*
     * Create necessary files
     */
    private void init_files()
    {
        rootDir = new File(getDataFolder() + "");
        if (!rootDir.exists())
        {
            rootDir.mkdir();
            firstTime = true;
        }
        bansDir = new File(getDataFolder() + "/bans/");
        if (!bansDir.exists())
        {
            bansDir.mkdir();
        }
    }
    
    
    
    /*
     * Load all bans
     */
    private void init_loadBans()
    {
        for (int i = 0; i < bansDir.listFiles().length; i++)
        {
            try
            {
                File f = bansDir.listFiles()[i];
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                Ban ban = (Ban) ois.readObject();
                ois.close();
                bans.put(ban.getName(), ban);
                getLogger().info("Loaded ban data for: " + ban.getName());
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
                getLogger().severe("Error loading ban data!");
            }
        }
    }
    
    
    
    /*
     * Save all bans
     */
    private void init_saveBans()
    {
        for (Ban b : bans.values())
        {
            File f = new File(getDataFolder() + "/bans/" + b.getName());
            try
            {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
                oos.writeObject(b);
                oos.close();
                getLogger().info("Saved ban data for: " + b.getName());
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                getLogger().info("Error saving ban data for: " + b.getName());
            }
        }
    }
    
    
    
    /*
     * Creates / saves / grabs config stuff
     */
    private void init_config()
    {
        config = new File(getDataFolder() + "/config.yml");
        if (!config.exists())
        {
            saveDefaultConfig();
        }
        loginFormat = getConfig().getString("login-format").replace("&", "§");
        kickFormat = getConfig().getString("kick-format").replace("&", "§");
        broadcastFormat = getConfig().getString("broadcast-format").replace("&", "§");
    }
    
    
    
    private void checkBans()
    {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
        {
            
            
            @Override
            public void run()
            {
                for (Ban b : bans.values())
                {
                    if (MathHelper.getSecondsRemaining(b.getUntil()) <= 2)
                    {
                        bans.remove(b.getName());
                        File f = new File(bansDir + "/" + b.getName());
                        f.delete();
                        System.out.println("Lifting ban for: " + b.getName());
                    }
                }
            }
        }, 0L, 10L);
    }
    
    
    
    /* = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =*/
    
    /*
     * Grab main plugin instance
     */
    public static ExodusBans getInstance()
    {
        return instance;
    }
    
    
    
    /*
     * Grabs a Map of all temp bans with username key, ban object value
     */
    public Map<String, Ban> getBans()
    {
        return bans;
    }
    
    
    
    /*
     * Retuns the login message format
     */
    public String getLoginFormat()
    {
        return loginFormat;
    }
    
    
    
    /*
     * Returns the broadcast message format
     */
    public String getBroadcastFormat()
    {
        return broadcastFormat;
    }
    
    
    
    /*
     * Returns the kick message format
     */
    public String getKickFormat()
    {
        return kickFormat;
    }
    
}
