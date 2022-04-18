package com.rshub.api.services;

import com.rshub.api.containers.Container;
import com.rshub.api.services.impl.ContainerStateService;
import com.rshub.api.state.ErrorEvent;
import com.rshub.api.state.EventStatus;
import com.rshub.api.state.SelfCorrectionState;
import kotlin.Unit;
import kotlinx.coroutines.Job;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public final class GameStateHelper {
    private GameStateHelper() {
    }

    public static final Set<Container> ITEM_CONTAINERS = new HashSet<>();

    public static Job reactToError(Class<?> clazz, BiConsumer<ErrorEvent<?>, EventStatus> func) {
        return SelfCorrectionState.INSTANCE.reactToError(clazz, (ee, es) -> {
            func.accept(ee, es);
            return Unit.INSTANCE;
        });
    }

    public static boolean sendErrorEvent(ErrorEvent<?> event) {
        return SelfCorrectionState.INSTANCE.getEvents().tryEmit(event);
    }

    public static void registerService(GameStateService service) {
        GameStateServiceManager.INSTANCE.registerService(service);
    }
}
