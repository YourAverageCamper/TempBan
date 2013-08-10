
package me.zeus.ExodusBans.Objects;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import me.zeus.ExodusBans.ExodusBans;



public class Ban implements Serializable
{
    
    
    private static final long serialVersionUID = 1L;
    String name;
    String reason;
    long until;
    
    
    
    public Ban(String name, long until, String reason)
    {
        this.name = name;
        this.until = until;
        this.reason = reason;
    }
    
    
    
    public void save()
    {
        File f = new File(ExodusBans.getInstance().getDataFolder() + "/bans/" + name);
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(this);
            oos.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    
    
    
    /*
     * Grabs the name of the banned player
     */
    public String getName()
    {
        return name;
    }
    
    
    
    /*
     * Grabs the time when unbanned (in millis)
     */
    public long getUntil()
    {
        return until;
    }
    
    
    
    /*
     * Gets the reason banned
     */
    public String getReason()
    {
        return (reason != null && reason != "" ? reason : "N/A");
    }
    
    
    
    /*
     * Grab ban object
     */
    public static Ban getBan(String name)
    {
        return ExodusBans.getInstance().getBans().get(name);
    }
    
}
