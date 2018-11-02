package org.koin.core.module

import org.koin.core.Koin
import org.koin.core.bean.BeanDefinition
import org.koin.core.bean.Definition
import org.koin.core.error.AlreadyExistingDefinition

class Module() {
    internal val definitions = hashSetOf<BeanDefinition<*>>()
    lateinit var koin: Koin

    inline fun <reified T> single(name: String? = null, noinline definition: Definition<T>) {
        declareDefinition(BeanDefinition.createSingle(name, definition))
    }

    inline fun <reified T> factory(name: String? = null, noinline definition: Definition<T>) {
        declareDefinition(BeanDefinition.createFactory(name, definition))
    }

    fun <T> declareDefinition(definition: BeanDefinition<T>) {
        val added = definitions.add(definition)
        if (!added) {
            throw AlreadyExistingDefinition("Already existing definition $definition")
        }
    }

    inline fun <reified T> get(): T {
        return koin.get()
    }
}