package com.rshub.api.containers

import kraken.plugin.api.Item

data class ContainerChangeEvent(val slot: Int, val prev: Item, val next: Item)