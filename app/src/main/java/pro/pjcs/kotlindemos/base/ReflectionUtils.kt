package pro.pjcs.kotlindemos.base

import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible


fun Any.getFieldWithName(name: String): Any? {

    this::class.memberProperties.forEach {
        if(it.name == name){
            it.isAccessible = true;
            return it.getter.call(this)
        }
    }

    return null
}


class ReflectionUtils {



}