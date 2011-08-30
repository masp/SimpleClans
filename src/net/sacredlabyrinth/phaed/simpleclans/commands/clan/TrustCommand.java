package net.sacredlabyrinth.phaed.simpleclans.commands.clan;

import net.sacredlabyrinth.phaed.simpleclans.ChatBlock;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.Helper;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author phaed
 */
public class TrustCommand
{
    public TrustCommand()
    {
    }

    /**
     * Execute the command
     * @param player
     * @param arg
     */
    public void execute(Player player, String[] arg)
    {
        SimpleClans plugin = SimpleClans.getInstance();

        if (plugin.getPermissionsManager().has(player, "simpleclans.leader.settrust"))
        {
            ClanPlayer cp = plugin.getClanManager().getClanPlayer(player);

            if (cp != null)
            {
                Clan clan = cp.getClan();

                if (clan.isLeader(player))
                {
                    if (arg.length == 1)
                    {
                        Player trusted = Helper.matchOnePlayer(arg[0]);

                        if (trusted != null)
                        {
                            if (!trusted.getName().equals(player.getName()))
                            {
                                if (clan.isMember(trusted))
                                {
                                    if (!clan.isLeader(trusted))
                                    {
                                        ClanPlayer tcp = plugin.getClanManager().getCreateClanPlayer(trusted.getName());

                                        if (!tcp.isTrusted())
                                        {
                                            clan.addBb(player.getName(), ChatColor.AQUA + Helper.capitalize(trusted.getName()) + " has been given trusted status by " + player.getName());
                                            tcp.setTrusted(true);
                                            plugin.getStorageManager().updateClanPlayer(tcp);
                                        }
                                        else
                                        {
                                            ChatBlock.sendMessage(player, ChatColor.RED + "This player is already trusted");
                                        }
                                    }
                                    else
                                    {
                                        ChatBlock.sendMessage(player, ChatColor.RED + "Leaders are already trusted");
                                    }
                                }
                                else
                                {
                                    ChatBlock.sendMessage(player, ChatColor.RED + "The player is not a member of your clan");
                                }
                            }
                            else
                            {
                                ChatBlock.sendMessage(player, ChatColor.RED + "You cannot trust yourself");
                            }
                        }
                        else
                        {
                            ChatBlock.sendMessage(player, ChatColor.RED + "No player matched");
                        }
                    }
                    else
                    {
                        ChatBlock.sendMessage(player, ChatColor.RED + "Usage: /clan trust [player]");
                    }
                }
                else
                {
                    ChatBlock.sendMessage(player, ChatColor.RED + "You do not have leader permissions");
                }
            }
            else
            {
                ChatBlock.sendMessage(player, ChatColor.RED + "You are not a member of any clan");
            }
        }
        else
        {
            ChatBlock.sendMessage(player, ChatColor.RED + "Insufficient permissions");
        }
    }
}