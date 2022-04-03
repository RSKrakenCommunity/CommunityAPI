package com.rshub.api.plugin;

import com.rshub.api.services.GameStateHelper;
import kraken.plugin.api.Client;
import kraken.plugin.api.Plugin;
import kraken.plugin.api.PluginContext;

public abstract class JavaPlugin extends Plugin {

    private final String name;
    private PluginContext context;

    public JavaPlugin(String name) {
        this.name = name;
    }

    protected abstract void onLoad();
    protected abstract void painOverlay();

    @Override
    public final boolean onLoaded(PluginContext pluginContext) {
        pluginContext.setName(this.name);
        this.context = pluginContext;
        this.onLoad();
        return super.onLoaded(pluginContext);
    }

    @Override
    public final void onPaintOverlay() {
        if(Client.getState() == Client.IN_GAME) {
            painOverlay();
        }
    }

    public String getName() {
        return name;
    }

    public PluginContext getContext() {
        return context;
    }
}
