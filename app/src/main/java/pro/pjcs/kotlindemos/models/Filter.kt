package pro.pjcs.kotlindemos.models

import pro.pjcs.kotlindemos.base.getFieldWithName
import java.io.Serializable

//TODO: Migrate to parcelable
class Filter(val filters: List<FilterParams>) : Serializable {

    var active = true

    fun validates(obj: Any) : Boolean {

        filters.forEach { filter ->

            val fieldValue = obj.getFieldWithName(filter.field)
                ?: throw Exception("Invalid field name on filter") //TODO: custom exception class

            when( filter.compareOperator ) {
                FilterType.EQUALS -> return fieldValue == filter.againstValue
                //TODO: Other Filter types
            }

        }

        return false;

    }

}

enum class FilterType { EQUALS, GREATER_THAN, LESS_THAN }
data class FilterParams(val field: String, val compareOperator: FilterType, val againstValue: String? = null) : Serializable