package com.rshub.api.listeners;

import com.rshub.api.containers.Container;
import com.rshub.api.widgets.Widget;
import kotlin.Unit;

public class BindingHelper {
    private BindingHelper() {
    }

    public static void bind(Widget widget, Container container) {
        widget.addContainer(container);
        container.addListener((slot, prev, next) -> {
            widget.containerChanged(container, widget.asWidgetItem(slot, prev), widget.asWidgetItem(slot, next));
            return Unit.INSTANCE;
        });
    }
}
