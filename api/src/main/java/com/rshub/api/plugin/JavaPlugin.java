package com.rshub.api.plugin;

import kraken.plugin.api.Plugin;
import kraken.plugin.api.PluginContext;

public abstract class JavaPlugin extends Plugin {

    private final String name;
    private PluginContext context;

    public JavaPlugin(String name) {
        this.name = name;
    }

    protected abstract void onLoad();

    @Override
    public final boolean onLoaded(PluginContext pluginContext) {
        pluginContext.setName(this.name);
        this.context = pluginContext;
        this.onLoad();
        return super.onLoaded(pluginContext);
    }

    public String getName() {
        return name;
    }

    public PluginContext getContext() {
        return context;
    }
}
