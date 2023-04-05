package me.ositlar.application.repo

import me.ositlar.application.common.Item
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class ListRepo<E> : Repo<E> {
    private val list = ConcurrentHashMap<String, E>()

    @Suppress("KotlinConstantConditions")
    override fun create(element: E): Boolean =
        Item(element, UUID.randomUUID().toString())
            .let {
                list[it.id] = it.elem
                true
            }

    override fun read(): List<Item<E>> =
        list.map {
            Item(it.value, it.key)
        }

    override fun read(id: String): Item<E>? =
        list[id]?.let {
            Item(it, id)
        }

    override fun read(ids: List<String>): List<Item<E>> =
        ids.mapNotNull { id ->
            list[id]?.let {
                Item(it, id)
            }
        }

    override fun update(id: String, value: E): Boolean =
        if (list.containsKey(id)) {
            list[id] = value
            true
        } else
            false

    override fun delete(id: String): Boolean =
        list.remove(id) != null
}