package net.extrillius.skywars;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
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
                this.mapsAccessor.getConfig().set("maps." + args[0], "");
                sender.sendMessage(ChatColor.GREEN + "Map " + ChatColor.DARK_AQUA + args[0] +
                        ChatColor.GREEN + " has been added.");
                sender.sendMessage(ChatColor.GREEN + "Now define a selection and type " +
                        ChatColor.AQUA + "/island <map> <value>");
            }
            if (args.length > 1) {
                sender.sendMessage(ChatColor.RED + "Too many arguments!");
                sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.AQUA + "/create <map>");
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GRAY + "Usage:" + ChatColor.GREEN + "/create <map>");
            }
        }

        if (cmd.getName().equalsIgnoreCase("island")) {
            s = getWorldEdit().getSelection(p);
            if (s == null) {
                sender.sendMessage(ChatColor.RED + "Define a WorldEdit selection before running the /island command.");
            }
            // save the blocks to file

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
