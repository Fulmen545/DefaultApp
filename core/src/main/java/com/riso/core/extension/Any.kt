package com.riso.core.extension

val Any.TAG: String
    get() = this::class.simpleName ?: "UNRESOLVED_TAG"
