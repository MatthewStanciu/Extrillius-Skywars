package net.extrillius.skywars;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by mattbstanciu on 3/28/16.
 */
public class Skywars extends JavaPlugin implements Listener {

    WorldGuardPlugin worldGuardPlugin = new WorldGuardPlugin();
    WorldEditPlugin worldEditPlugin = new WorldEditPlugin();
    private ConfigAccessor mapsAccessor;

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);

        this.mapsAccessor = new ConfigAccessor(this, "maps.yml");
        this.mapsAccessor.getConfig().addDefault("maps", new ArrayList<String>()); // This is wrong!
    }

    public WorldEditPlugin getWorldEdit() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");

        if (plugin instanceof WorldEditPlugin) return (WorldEditPlugin) plugin;
        else return null;
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin instanceof WorldGuardPlugin) return (WorldGuardPlugin) plugin;
        else return null;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        Selection s = getWorldEdit().getSelection(p);

        if (cmd.getName().equalsIgnoreCase("create")) {
            if (args.length == 1) {
                if (!(sender.hasPermission("skywars.create"))) {
                    sender.sendMessage(ChatColor.RED + "Creating maps is an admin-only command.");
                }
                //this.mapsAccessor.getConfig().set("maps" + args[0])
            }
            if (args.length > 1) {
                sender.sendMessage(ChatColor.RED + "Too many arguments!");
                return false;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GRAY + "Usage:" + ChatColor.GREEN + "/create <map>");
            }
        }

        if (cmd.getName().equalsIgnoreCase("delete")) {
            if (!(sender.hasPermission("skywars.delete"))) {
                sender.sendMessage(ChatColor.RED + "Deleting maps is an admin-only command.");
            }
            // the actual command
        }

        return true;
    }
}
