package eu.virtusdevelops.clickableholotest.API;

import eu.virtusdevelops.clickableholostest.ClickableHolosTest;
import eu.virtusdevelops.clickableholostest.hologram.HologramTemplate;
import eu.virtusdevelops.clickableholostest.placeholder.PlaceholderRegistry;
import eu.virtusdevelops.clickableholostest.API.PlaceholderReplacer;
import eu.virtusdevelops.clickableholotest.hologram.Hologram;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class SimpleHologramsAPI {


    /**
     * Get any hologram by its name, keep in mind if hologram doesn't exist its gonna return null!
     *
     * @param name Name of the hologram you created
     * @return Returns the Hologram.class
     */
    public static Hologram getHologram(String name){
        return ClickableHolosTest.getHologramRegistryAPI().getHologram(name);
    }

    /**
     *  Remove any hologram by its name.
     *
     * @param name Hologram name you wish to remove
     */
    public static void removeHologram(String name){
        ClickableHolosTest.getHologramRegistryAPI().removeHologram(name);
    }

    /**
     * Create hologram, usefull for any plugin (Crates, informations...)
     * If using this method you need to register viewers using seperate function
     *
     * @param name Name of hologram you're creating
     * @param range How far away can viewers be to see it (in blocks)
     * @param location Where should hologram be
     * @param lines Lines of hologram
     */
    public static void createHologram(String name, int range, Location location, List<String> lines){
        ClickableHolosTest.getHologramRegistryAPI().addHologram(new HologramTemplate(lines, name, range, "", location));
    }

    /**
     * Create hologram, usefull for any plugin (Crates, informations...)
     * No need to register viewers in seperate class.
     *
     * @param name Name of hologram you're creating
     * @param range How far away can viewers be to see it (in blocks)
     * @param location Where should hologram be
     * @param lines Lines of hologram
     * @param viewers List of players that can see hologram.
     */
    public static void createHologram(String name, int range, Location location, List<String> lines, List<Player> viewers){
        ClickableHolosTest.getHologramRegistryAPI().addHologram(new HologramTemplate(lines, name, range, "", location));
        registerViewers(name, viewers);
    }

    /**
     *  Allow specific players to see specific hologram
     *
     * @param hologramName Hologram you wish to add viewers to
     * @param viewers Viewers (players) to add to be able to see hologram
     */
    public static void registerViewers(String hologramName, List<Player> viewers){
        Hologram hologram = ClickableHolosTest.getHologramRegistryAPI().getHologram(hologramName);
        if(hologram != null) {
            for (Player viewer : viewers) {
                hologram.register(viewer);
            }
        }
    }

    /**
     * Allow specific players to see specific hologram
     *
     * @param hologramName Hologram you wish to add viewers to
     * @param viewers Viewers (players) to add to be able to see hologram
     */
    public static void registerViewers(String hologramName, Player...viewers){
        Hologram hologram = ClickableHolosTest.getHologramRegistryAPI().getHologram(hologramName);
        if(hologram != null) {
            for (Player viewer : viewers) {
                hologram.register(viewer);
            }
        }
    }

    /**
     * Add new placeholders to holograms (they change text upon what you choose them to be)
     *
     * @param plugin Plugin registering the placeholder
     * @param refreshrate How often is placeholder refreshed
     * @param replacer Replacer that replaces text placeholder represents
     * @param placeholder Placeholder text used on holograms
     */
    public static void registerPlaceholder(JavaPlugin plugin, double refreshrate, PlaceholderReplacer replacer, String placeholder){
        PlaceholderRegistry.Companion.registerPlaceholder(plugin, refreshrate, replacer, placeholder);
    }

    /**
     * Remove placeholders.
     *
     * @param plugin Plugin that registered the placeholder
     * @param placeholder Placeholder text used on holograms
     */
    public static void unregisterPlaceholder(JavaPlugin plugin, String placeholder){
        PlaceholderRegistry.Companion.unregister(plugin, placeholder);
    }
}
