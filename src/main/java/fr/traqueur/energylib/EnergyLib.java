package fr.traqueur.energylib;

import com.tcoded.folialib.FoliaLib;
import com.tcoded.folialib.impl.PlatformScheduler;
import fr.traqueur.energylib.api.EnergyAPI;
import fr.traqueur.energylib.api.EnergyManager;
import fr.traqueur.energylib.hooks.EnergyItemsAdderCompatibility;
import fr.traqueur.energylib.hooks.EnergyOraxenCompatibility;
import io.th0rgal.oraxen.compatibilities.CompatibilitiesManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public final class EnergyLib extends JavaPlugin implements EnergyAPI {

    private PlatformScheduler scheduler;
    private EnergyManager manager;
    private boolean debug;

    @Override
    public void onEnable() {
        this.scheduler = new FoliaLib(this).getScheduler();
        this.manager = new EnergyManagerImpl(this);
        this.debug = false;

        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new EnergyListener(this), this);

        this.registerProvider(this, EnergyAPI.class);
        this.registerProvider(this.manager, EnergyManager.class);

        this.getScheduler().runNextTick((t) -> {
            this.hooks();

            this.getServer().getWorlds().forEach(world -> {
                Arrays.stream(world.getLoadedChunks()).forEach(chunk -> this.manager.loadNetworks(chunk));
            });

            this.manager.startNetworkUpdater();

            this.getScheduler().runTimerAsync(this.manager::saveNetworks, 1, 1, TimeUnit.HOURS);
        });
    }

    @Override
    public void onDisable() {
        this.manager.stopNetworkUpdater();
        this.manager.saveNetworks();
    }

    private void hooks() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        if(pluginManager.isPluginEnabled("Oraxen"))
            CompatibilitiesManager.addCompatibility("EnergyLib", EnergyOraxenCompatibility.class);

        if(pluginManager.isPluginEnabled("ItemsAdder"))
            pluginManager.registerEvents(new EnergyItemsAdderCompatibility(this), this);
    }

    @Override
    public EnergyManager getManager() {
        return this.manager;
    }

    @Override
    public PlatformScheduler getScheduler() {
        return this.scheduler;
    }

    @Override
    public boolean isDebug() {
        return this.debug;
    }

    @Override
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    private <T> void registerProvider(T instance, Class<T> clazz) {
        ServicesManager servicesManager = this.getServer().getServicesManager();
        servicesManager.register(clazz, instance, this, ServicePriority.Normal);
    }
}
