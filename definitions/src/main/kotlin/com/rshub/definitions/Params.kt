// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright © 2021 Trenton Kress
//  This file is part of project: FreeDev
//
package com.rshub.definitions

import com.rshub.buffer.exts.getMedium
import com.rshub.buffer.exts.string
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.nio.ByteBuffer

class Params {
    var map: MutableMap<Int, Any>? = HashMap()
    fun parse(buffer: ByteBuffer) {
        val size: Int = buffer.get().toInt()
        for (index in 0 until size) {
            val bool = buffer.get().toInt() == 1
            val key: Int = buffer.getMedium()
            val value: Any = if (bool) buffer.string else buffer.int
            map!![key] = value
        }
    }

    fun toJsonObject(): JsonObject {
        return buildJsonObject {
            map?.forEach { (key, value) ->
                if (value is Int) {
                    put("$key", value)
                } else if (value is String) {
                    put("$key", value)
                }
            }
        }
    }

    /*fun valToType(id: Int, o: Any): Any {
        if (ParamDef.get(id).type === ScriptVarType.COMPONENT) {
            if (o is String) return o
            val interfaceId = (o as Int shr 16).toLong()
            val componentId = o - (interfaceId shl 16)
            return "IComponent($interfaceId, $componentId)"
        } else if (ParamDef.get(id).type === ScriptVarType.WORLDTILE) {
            return o as? String ?: WorldTile(o as Int)
        } else if (ParamDef.get(id).type === ScriptVarType.STAT) {
            if (o is String) return o
            val idx = o as Int
            return if (idx >= Constants.SKILL_NAME.length) o else idx.toString() + "(" + Constants.SKILL_NAME.get(o) + ")"
        } else if (ParamDef.get(id).type === ScriptVarType.ITEM) {
            return o as? String
                ?: (o as Int).toString() + if (o != -1) " (" + ItemDef.get(o).name.toString() + ")" else ""
        } else if (ParamDef.get(id).type === ScriptVarType.NPC) {
            return o as? String
                ?: (o as Int).toString() + if (o != -1) " (" + NPCDef.get(o).name.toString() + ")" else ""
        } else if (ParamDef.get(id).type === ScriptVarType.STRUCT) {
            return o *//* + ": " + StructDef.get((int) o)*//*
        }
        return o
    }*/

    /*override fun toString(): String {
        if (map == null) return "null"
        val s = StringBuilder()
        s.append("{ ")
        for (l in map!!.keys) {
            s.append(l.toString() + " (" + ParamDef.get(l).type + ")")
            s.append(" = ")
            s.append(valToType(l, map!![l]!!).toString() + ", ")
        }
        s.append(" }")
        return s.toString()
    }*/

    operator fun get(id: Int): Any? {
        return map!![id]
    }

    fun getString(opcode: Int, defaultVal: String): String {
        if (map != null) {
            val value = map!![opcode]
            if (value != null && value is String) return value
        }
        return defaultVal
    }

    fun getString(opcode: Int): String {
        if (map != null) {
            val value = map!![opcode]
            if (value != null && value is String) return value
        }
        return "null"
    }

    fun getInt(opcode: Int, defaultVal: Int): Int {
        if (map != null) {
            val value = map!![opcode]
            if (value != null && value is Int) return value
        }
        return defaultVal
    }

    fun getInt(opcode: Int): Int {
        if (map != null) {
            val value = map!![opcode]
            if (value != null && value is Int) return value
        }
        return 0
    }
}