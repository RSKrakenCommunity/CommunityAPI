package com.rshub.api.widgets.events

import com.rshub.api.containers.Container
import com.rshub.api.entities.items.ContainerItem
import com.rshub.api.widgets.Widget
import com.rshub.api.widgets.WidgetEvent

class ContainerChangedEvent(
    override val source: Widget,
    val con: Container,
    val prev: ContainerItem,
    val next: ContainerItem
) : WidgetEvent<Widget>