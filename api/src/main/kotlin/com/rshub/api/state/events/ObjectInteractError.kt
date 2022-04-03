package com.rshub.api.state.events

import com.rshub.api.entities.objects.WorldObject
import com.rshub.api.state.ErrorEvent

class ObjectInteractError(override val source: WorldObject) : ErrorEvent<WorldObject>