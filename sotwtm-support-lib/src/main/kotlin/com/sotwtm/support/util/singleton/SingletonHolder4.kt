package com.sotwtm.support.util.singleton

/**
 * A singleton holder for object with three arguments in constructor.
 *
 * Usage, extent this class in companion object
 *
 * `companion object : SingletonHolder<Arg0Class, Arg1Class, Arg2Class, InstanceClass>(::InstanceClass)`
 *
 * Then, `InstanceClass.getInstance(arg0, arg1, arg2, arg3)`
 *
 * @author John
 * */
open class SingletonHolder4<Arg0Class, Arg1Class, Arg2Class, Arg3Class, InstanceClass>
(_constructor: (Arg0Class, Arg1Class, Arg2Class, Arg3Class) -> InstanceClass) {

    private val instanceConstructor: ((Arg0Class, Arg1Class, Arg2Class, Arg3Class) -> InstanceClass) = _constructor

    @Volatile
    private var instance: InstanceClass? = null

    fun getInstance(arg0: Arg0Class, arg1: Arg1Class, arg2: Arg2Class, arg3: Arg3Class): InstanceClass =
            synchronized(this) {
                instance ?: {
                    val newInstant = instanceConstructor(arg0, arg1, arg2, arg3)
                    instance = newInstant
                    newInstant
                }.invoke()
            }

    @Synchronized
    fun getInstance(): InstanceClass =
            instance ?: throw Exception("This class not yet initialized.")

    /**Initialize an instance for simple [getInstance]*/
    @Synchronized
    fun init(arg0: Arg0Class, arg1: Arg1Class, arg2: Arg2Class, arg3: Arg3Class) {
        if (instance != null) return
        instance = instanceConstructor(arg0, arg1, arg2, arg3)
    }
}
