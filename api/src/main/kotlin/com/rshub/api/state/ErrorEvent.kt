package com.rshub.api.state

interface ErrorEvent<S : Any> {

    val source: S

}