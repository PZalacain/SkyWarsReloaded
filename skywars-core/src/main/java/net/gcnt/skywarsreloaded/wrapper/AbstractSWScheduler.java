package net.gcnt.skywarsreloaded.wrapper;

import net.gcnt.skywarsreloaded.SkyWarsReloaded;

public abstract class AbstractSWScheduler implements SWScheduler {

    public final SkyWarsReloaded plugin;

    public AbstractSWScheduler(SkyWarsReloaded plugin) {
        this.plugin = plugin;
    }

}
