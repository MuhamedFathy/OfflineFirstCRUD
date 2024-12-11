package com.github.offlinefirstcrud.domain.typealiases

typealias Action = () -> Unit
typealias Consumer<T> = (param: T) -> Unit
typealias BiConsumer<T1, T2> = (param1: T1, param2: T2) -> Unit
