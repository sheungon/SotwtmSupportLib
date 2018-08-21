package com.sotwtm.support.util.singleton

/**
 * A singleton holder for object with one argument in constructor.
 *
 * Usage, extent this class in companion object
 *
 * `companion object : SingletonHolder<Arg0Class, InstanceClass>(::InstanceClass)`
 *
 * Then, `InstanceClass.getInstance(arg0)`
 *
 * @author John
 * */
open class SingletonHolder1<Arg0Class, InstanceClass>(_constructor: (Arg0Class) -> InstanceClass) {

    private val instanceConstructor: ((Arg0Class) -> InstanceClass) = _constructor

    @Volatile
    private var instance: InstanceClass? = null

    @Synchronized
    fun getInstance(arg0: Arg0Class): InstanceClass =
            instance ?: {
                val newInstant = instanceConstructor(arg0)
                instance = newInstant
                newInstant
            }.invoke()

    @Synchronized
    fun getInstance(): InstanceClass =
            instance ?: throw Exception("This class not yet initialized.")

    /**Initialize an instance for simple [getInstance]*/
    @Synchronized
    fun init(arg0: Arg0Class): InstanceClass =
            instance ?: {
                val newInstance = instanceConstructor(arg0)
                instance = newInstance
                newInstance
            }.invoke()
}
