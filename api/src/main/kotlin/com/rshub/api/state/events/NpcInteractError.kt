package com.rshub.api.state.events

import com.rshub.api.entities.spirits.npc.WorldNpc
import com.rshub.api.state.ErrorEvent

class NpcInteractError(override val source: WorldNpc) : ErrorEvent<WorldNpc>