package com.rshub.api.services;

import com.rshub.api.containers.Container;
import com.rshub.api.services.impl.ContainerStateService;
import com.rshub.api.widgets.Widget;

import java.util.HashSet;
import java.util.Set;

public class GameStateHelper {
    private GameStateHelper() {
    }

    public static final Set<Container> ITEM_CONTAINERS = new HashSet<>();
    public static final GameStateServiceManager GAME_STATE_SERVICE_MANAGER = new GameStateServiceManager();

    static {
        registerService(new ContainerStateService());
    }

    public static void registerService(GameStateService service) {
        GAME_STATE_SERVICE_MANAGER.registerService(service);
    }
}
