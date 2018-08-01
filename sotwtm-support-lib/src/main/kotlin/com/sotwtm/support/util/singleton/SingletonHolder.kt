package com.sotwtm.support.util.singleton

/**
 * A singleton holder for object without arguments in constructor.
 *
 * Usage, extent this class in companion object
 *
 * `companion object : SingletonHolder<InstanceClass>(::InstanceClass)`
 *
 * Then, `InstanceClass.getInstance()`
 *
 * @author John
 * */
open class SingletonHolder<InstanceClass>(_constructor: () -> InstanceClass) {

    private val instanceConstructor: (() -> InstanceClass) = _constructor

    @Volatile
    private var instance: InstanceClass? = null

    fun getInstance(): InstanceClass =
            synchronized(this) {
                instance ?: {
                    val newInstant = instanceConstructor()
                    instance = newInstant
                    newInstant
                }.invoke()
            }
}
