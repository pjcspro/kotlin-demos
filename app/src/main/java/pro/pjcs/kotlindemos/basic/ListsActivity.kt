package pro.pjcs.kotlindemos.basic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pro.pjcs.kotlindemos.base.BaseActivity
import pro.pjcs.kotlindemos.models.Filter
import pro.pjcs.kotlindemos.models.User


class ListsActivity : BaseActivity() {

    private lateinit var filter         : Filter<Any>
    private lateinit var data           : List<Any>
    private lateinit var filteredData   : List<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        filter  = intent.getSerializableExtra(INTENT_FILTER) as Filter<Any>

        val dataJson = intent.getStringExtra(INTENT_DATA) as String
        val listType = object : TypeToken<List<User>>() { }.type
        data = Gson().fromJson<List<User>>(dataJson, listType)

        performFilter()
    }


    private fun performFilter(){

        if(!::data.isInitialized){
            return
        }

        if(!::filter.isInitialized ) {
            filteredData = data
            return
        }

        filteredData = data.filter { filter.validates(it) }

        Log.v(TAG, "Filtered data: "+Gson().toJson(filteredData))

    }


}

//- Creates a new Intent to be passed to StartActivity
private const val INTENT_FILTER = "INTENT_FILTER"
private const val INTENT_DATA   = "INTENT_DATA"
fun Context.listActivityIntent(filter: Filter<out Any>, data: ArrayList<Any>) : Intent {
    return Intent(this, ListsActivity::class.java).apply {
        putExtra(INTENT_FILTER, filter)
        putExtra(INTENT_DATA, Gson().toJson(data))
    }
}

