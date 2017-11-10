package org.koin.test.ext.junit

import org.junit.Assert
import org.koin.KoinContext
import org.koin.standalone.StandAloneContext
import org.koin.test.KoinTest
import org.koin.test.ext.koin.*
import kotlin.reflect.KClass

private val koinTestContext = StandAloneContext.koinContext as KoinContext

/**
 * Assert context definition definitionCount
 * @param definitionCount - number of definitions
 */
fun KoinTest.assertDefinitions(definitionCount: Int) {
    Assert.assertEquals("applicationContext must have $definitionCount definition", definitionCount, koinTestContext.AllDefinitions().size)
}

/**
 * Assert definitionClazz is defined in given scope
 * @param definitionClazz - bean definition class
 * @param scopeName - scope name
 */
fun KoinTest.assertDefinedInScope(definitionClazz: KClass<*>, scopeName: String) {
    val definition = koinTestContext.definition(definitionClazz)
    Assert.assertEquals("$definitionClazz must be in scope '$scopeName'", koinTestContext.beanRegistry.getScope(scopeName), koinTestContext.beanRegistry.definitions[definition])
}

/**
 * Assert context has definition instanceCount
 * @param scopeName - scope name
 * @param instanceCount - number of instances
 */
fun KoinTest.assertContextInstances(scopeName: String, instanceCount: Int) {
    val scope = koinTestContext.getScope(scopeName)
    val definitions = koinTestContext.AllDefinitions().filter { it.value == scope }.map { it.key }.toSet()
    val instances = koinTestContext.allInstances().filter { it.first in definitions }
    Assert.assertEquals("scope $scopeName must have $instanceCount instances", instanceCount, instances.size)
}

/**
 * Assert scope has given parent scope
 * @param scopeName - target scope name
 * @param scopeParent - parent scope name
 */
fun KoinTest.assertScopeParent(scopeName: String, scopeParent: String) {
    Assert.assertEquals("Scope '$scopeName' must have parent '$scopeName'", scopeParent, koinTestContext.beanRegistry.getScope(scopeName).parent?.name)
}

/**
 * Assert Koin has reminaing instances
 * @param instanceCount - instances count
 */
fun KoinTest.assertRemainingInstances(instanceCount: Int) {
    Assert.assertEquals("context must have $instanceCount instances", instanceCount, koinTestContext.allInstances().size)
}

/**
 * Assert Koin has properties count
 * @param propertyCount - properties count
 */
fun KoinTest.assertProperties(propertyCount: Int) {
    Assert.assertEquals("context must have $propertyCount properties", propertyCount, koinTestContext.allProperties().size)
}

/**
 * Assert Koin has contextCount contexts
 * @param contextCount - context count
 */
fun KoinTest.assertContexts(contextCount: Int) {
    Assert.assertEquals("context must have $contextCount contexts", contextCount, koinTestContext.allContext().size)
}