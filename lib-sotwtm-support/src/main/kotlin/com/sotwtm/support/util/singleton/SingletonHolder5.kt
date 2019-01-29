package com.sotwtm.support.util.singleton

/**
 * A singleton holder for object with three arguments in constructor.
 *
 * Usage, extent this class in companion object
 *
 * `companion object : SingletonHolder<Arg0Class, Arg1Class, Arg2Class, InstanceClass>(::InstanceClass)`
 *
 * Then, `InstanceClass.getInstance(arg0, arg1, arg2, arg3, arg5)`
 *
 * @author sheungon
 * */
open class SingletonHolder5<InstanceClass, Arg0Class, Arg1Class, Arg2Class, Arg3Class, Arg4Class>
    (_constructor: (Arg0Class, Arg1Class, Arg2Class, Arg3Class, Arg4Class) -> InstanceClass) {

    private val instanceConstructor: ((Arg0Class, Arg1Class, Arg2Class, Arg3Class, Arg4Class) -> InstanceClass) =
        _constructor

    @Volatile
    private var instance: InstanceClass? = null

    @Synchronized
    fun getInstance(
        arg0: Arg0Class,
        arg1: Arg1Class,
        arg2: Arg2Class,
        arg3: Arg3Class,
        arg4: Arg4Class
    ): InstanceClass =
        instance ?: {
            val newInstant = instanceConstructor(arg0, arg1, arg2, arg3, arg4)
            instance = newInstant
            newInstant
        }.invoke()

    @Synchronized
    fun getInstance(): InstanceClass =
        instance ?: throw Exception("This class not yet initialized.")

    /**Initialize an instance for simple [getInstance]*/
    @Synchronized
    fun init(arg0: Arg0Class, arg1: Arg1Class, arg2: Arg2Class, arg3: Arg3Class, arg4: Arg4Class): InstanceClass =
        instance ?: run {
            val newInstant = instanceConstructor(arg0, arg1, arg2, arg3, arg4)
            instance = newInstant
            newInstant
        }
}
