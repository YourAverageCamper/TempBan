
package me.zeus.ExodusBans.Commands;


import java.io.File;

import me.zeus.ExodusBans.ExodusBans;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;



public class CMD_Unban implements CommandExecutor
{
    
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args)
    {
        if (!sender.hasPermission("ExodusBans.Unban"))
        {
            sender.sendMessage("§8[§4ExodusBans§8]§f §cYou do not have permission to unban");
            return false;
        }
        if (args.length != 1)
        {
            sender.sendMessage("§8[§4ExodusBans§8]§f §cInvalid unban syntax. Proper: /exunban <user>");
            return false;
        }
        OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(args[0]);
        if (!p.hasPlayedBefore())
        {
            sender.sendMessage("§8[§4ExodusBans§8]§f §cThat player is not valid, or has never even played before! ");
            return false;
        }
        if (!ExodusBans.getInstance().getBans().containsKey(p.getName()))
        {
            sender.sendMessage("§8[§4ExodusBans§8]§f §cThat player isn't even banned!");
            return false;
        }
        
        ExodusBans.getInstance().getBans().remove(p.getName());
        File f = new File(ExodusBans.getInstance().getDataFolder() + "/bans/" + p.getName());
        f.delete();
        sender.sendMessage("§8[§4ExodusBans§8]§f  §a" + p.getName() + " was unbanned!");
        
        return false;
    }
    
}
