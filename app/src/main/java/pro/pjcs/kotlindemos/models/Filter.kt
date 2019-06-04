package pro.pjcs.kotlindemos.models

import java.io.Serializable

//TODO: Migrate to parcelable
class Filter<T>(private val filters: List<FilterParams<T>>) : Serializable {

    var active = true

    fun validates(obj: T) : Boolean {

        filters.forEach { filter ->

            //val fieldValue = obj.getFieldWithName(filter.field)
             //   ?: throw Exception("Invalid field name on filter") //TODO: custom exception class

            return filter.compareOperator(obj)

        }

        return false;

    }

}

data class FilterParams<T>(val compareOperator: (T) -> Boolean) : Serializable