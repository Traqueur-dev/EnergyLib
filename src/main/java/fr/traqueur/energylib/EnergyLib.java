package fr.traqueur.energylib;

import com.tcoded.folialib.FoliaLib;
import com.tcoded.folialib.impl.PlatformScheduler;
import fr.traqueur.energylib.api.EnergyAPI;
import fr.traqueur.energylib.api.EnergyManager;
import fr.traqueur.energylib.api.components.EnergyNetwork;
import fr.traqueur.energylib.tests.EnergyTest;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class EnergyLib extends JavaPlugin implements EnergyAPI {

    private PlatformScheduler scheduler;
    private EnergyManager manager;

    @Override
    public void onEnable() {
        this.scheduler = new FoliaLib(this).getScheduler();
        this.manager = new EnergyManagerImpl(this);

        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new EnergyListener(this), this);

        this.registerProvider(this.manager, EnergyManager.class);
        this.registerProvider(this, EnergyAPI.class);

        new EnergyTest(this);

        this.manager.startNetworkUpdater();
    }

    @Override
    public void onDisable() {
        this.manager.stopNetworkUpdater();
    }

    @Override
    public EnergyManager getManager() {
        return this.manager;
    }

    @Override
    public PlatformScheduler getScheduler() {
        return this.scheduler;
    }

    private <T> void registerProvider(T instance, Class<T> clazz) {
        ServicesManager servicesManager = this.getServer().getServicesManager();
        servicesManager.register(clazz, instance, this, ServicePriority.Normal);
    }
}
