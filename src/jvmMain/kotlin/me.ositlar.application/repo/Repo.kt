package me.ositlar.application.repo

import me.ositlar.application.common.Item

interface Repo<E> {
    fun create(element: E): Boolean
    fun read(): List<Item<E>>
    fun read(id: String): Item<E>?
    fun read(ids: List<String>): List<Item<E>>
    fun update(item: Item<E>): Boolean
    fun delete(id: String): Boolean
}