package com.rshub.api.di

import java.lang.IllegalStateException

class DependencyInjection {

    val classes = mutableMapOf<Class<*>, Any>()

    fun register(clazz: Class<*>, value: Any) {
        classes[clazz] = value
    }

    fun <T> get(clazz: Class<*>) : T {
        if(!classes.containsKey(clazz)) {
            throw IllegalStateException("No instance found for ${clazz.simpleName}")
        }
        return classes[clazz]!! as T
    }

    inline fun <reified C> get() : C {
        return classes[C::class.java] as C
    }

}