package com.rshub.api.listeners;

import com.rshub.api.containers.Container;
import com.rshub.api.widgets.Widget;
import kotlin.Unit;

public final class BindingHelper {
    private BindingHelper() {
    }

    public static void bind(Widget widget, Container container) {
        widget.addContainer(container);
        container.addListener((slot, prev, next) -> {
            Widget.Companion.containerChanged(container, widget, slot, prev, next);
            return Unit.INSTANCE;
        });
    }
}
