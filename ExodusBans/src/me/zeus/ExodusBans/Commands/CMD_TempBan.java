
package me.zeus.ExodusBans.Commands;


import java.util.concurrent.TimeUnit;

import me.zeus.ExodusBans.ExodusBans;
import me.zeus.ExodusBans.Objects.Ban;
import me.zeus.ExodusBans.Utils.MathHelper;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;



public class CMD_TempBan implements CommandExecutor
{
    
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!sender.hasPermission("ExodusBans.TempBan"))
        {
            sender.sendMessage("§8[§4ExodusBans§8]§f You don't have permission to tempban!");
            return false;
        }
        if (args.length < 3)
        {
            sender.sendMessage("§8[§4ExodusBans§8]§f Invalid syntax. Proper: /extempban <user> <amount> <unit> [reason]");
            return false;
        }
        
        if (args.length >= 3)
        {
            OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(args[0]);
            int amount = Integer.parseInt(args[1]);
            TimeUnit unit = null;
            
            if (args[2].contains("s"))
                unit = TimeUnit.SECONDS;
            if (args[2].contains("m"))
                unit = TimeUnit.MINUTES;
            if (args[2].contains("h"))
                unit = TimeUnit.HOURS;
            if (args[2].contains("d"))
                unit = TimeUnit.DAYS;
            
            String reason = "";
            
            if (args.length > 3)
            {
                StringBuilder sb = new StringBuilder();
                for (int i = 3; i < args.length; i++)
                    sb.append(args[i]).append(" ");
                reason = sb.toString().replace("&", "§");
            }
            else
                reason = "N/A";
            if (p.isOnline())
            {
                // kick player with style
                String format = ExodusBans.getInstance().getKickFormat();
                format = format.replace("{reason}", reason);
                format = format.replace("{player}", p.getName());
                format = format.replace("{time}", amount + " " + unit);
                p.getPlayer().kickPlayer(format.replace("&", "§"));
            }
            
            // brodcast message with style
            String bf = ExodusBans.getInstance().getBroadcastFormat();
            bf = bf.replace("{reason}", reason);
            bf = bf.replace("{player}", p.getName());
            bf = bf.replace("{time}", amount + " " + unit);
            Bukkit.getServer().broadcastMessage("§8[§4ExodusBans§8]§f " + bf);
            
            // stuff
            Ban ban = new Ban(p.getName(), System.currentTimeMillis() + MathHelper.calculateMillis(unit, amount), reason);
            ban.save();
            ExodusBans.getInstance().getBans().put(p.getName(), ban);
        }
        return false;
    }
}
