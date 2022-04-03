package com.rshub.api.services;

import com.rshub.api.containers.Container;
import com.rshub.api.services.impl.ContainerStateService;
import com.rshub.api.state.ErrorEvent;
import com.rshub.api.state.ErrorStatus;
import com.rshub.api.state.EventStatus;
import com.rshub.api.state.SelfCorrectionState;
import com.rshub.api.widgets.Widget;
import kotlin.Unit;
import kotlinx.coroutines.Job;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class GameStateHelper {
    private GameStateHelper() {
    }

    public static final Set<Container> ITEM_CONTAINERS = new HashSet<>();
    public static final GameStateServiceManager GAME_STATE_SERVICE_MANAGER = new GameStateServiceManager();
    public static final SelfCorrectionState SELF_CORRECTION_STATE = new SelfCorrectionState();

    static {
        registerService(new ContainerStateService());
        GAME_STATE_SERVICE_MANAGER.start();
        SELF_CORRECTION_STATE.start();
    }

    public static Job reactToError(Class<?> clazz, BiConsumer<ErrorEvent<?>, EventStatus> func) {
        return SELF_CORRECTION_STATE.reactToError(clazz, (ee, es) -> {
            func.accept(ee, es);
            return Unit.INSTANCE;
        });
    }

    public static boolean sendErrorEvent(ErrorEvent<?> event) {
        return SELF_CORRECTION_STATE.getEvents().tryEmit(event);
    }

    public static void registerService(GameStateService service) {
        GAME_STATE_SERVICE_MANAGER.registerService(service);
    }
}
