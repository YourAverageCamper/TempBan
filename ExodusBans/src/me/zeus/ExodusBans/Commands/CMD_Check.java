
package me.zeus.ExodusBans.Commands;


import me.zeus.ExodusBans.Objects.Ban;
import me.zeus.ExodusBans.Utils.MathHelper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class CMD_Check implements CommandExecutor
{
    
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args)
    {
        if (sender instanceof Player)
        {
            if (!sender.hasPermission("ExodusBans.Check"))
            {
                sender.sendMessage("§8[§4ExodusBans§8]§f §cYou don't have permission to check ban statuses.");
                return false;
            }
            if (args.length != 1)
            {
                sender.sendMessage("§8[§4ExodusBans§8]§f §cInvalid check syntax. Proper: /excheck <user>");
                return false;
            }
            if (Ban.getBan(args[0]) == null)
            {
                sender.sendMessage("§8[§4ExodusBans§8]§f §cThat player is either not banned or has not played before!");
                return false;
            }
            
            sender.sendMessage("§8[§4ExodusBans§8]§f §ePlayer: " + args[0]);
            sender.sendMessage("§8[§4ExodusBans§8]§f §eReason: " + Ban.getBan(args[0]).getReason());
            sender.sendMessage("§8[§4ExodusBans§8]§f §eSeconds left: " + MathHelper.getSecondsRemaining(Ban.getBan(args[0]).getUntil()));
            
        }
        return false;
    }
}
