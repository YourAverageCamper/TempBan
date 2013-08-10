
package me.zeus.ExodusBans.Events;


import java.io.File;

import me.zeus.ExodusBans.ExodusBans;
import me.zeus.ExodusBans.Objects.Ban;
import me.zeus.ExodusBans.Utils.MathHelper;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;



public class EVT_Join implements Listener
{
    
    
    @EventHandler
    public void onJoin(PlayerLoginEvent e)
    {
        Player p = e.getPlayer();
        
        if (!ExodusBans.getInstance().getBans().containsKey(p.getName()))
            return;
        
        if (MathHelper.getSecondsRemaining(Ban.getBan(p.getName()).getUntil()) < 1)
        {
            ExodusBans.getInstance().getBans().remove(p.getName());
            File f = new File(ExodusBans.getInstance().getDataFolder() + "/bans/" + p.getName());
            f.delete();
            return;
        }
        e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        
        Ban ban = Ban.getBan(p.getName());
        
        String km = ExodusBans.getInstance().getLoginFormat();
        km = km.replace("{reason}", ban.getReason());
        km = km.replace("{player}", p.getName());
        
        long time = ban.getUntil();
        
        boolean m = false;
        boolean h = false;
        boolean d = false;
        
        // calculate message in seconds
        if (MathHelper.getSecondsRemaining(time) < 60)
            km = km.replace("{time}", MathHelper.getSecondsRemaining(time) + " seconds");
        else
            m = true;
        
        // calculate message in minutes
        if (m)
            if (MathHelper.getMinutesRemaining(time) < 60)
                km = km.replace("{time}", MathHelper.getMinutesRemaining(time) + " minutes");
            else
                h = true;
        
        // calculate message in hours
        if (h)
            if (MathHelper.getHoursRemaining(time) < 25)
                km = km.replace("{time}", MathHelper.getHoursRemaining(time) + " hours");
            else
                d = true;
        
        // calculate msesage in days
        if (d)
            km = km.replace("{time}", MathHelper.getDaysRemaining(time) + " days");
        
        e.setKickMessage(km);
    }
}
