package pro.pjcs.kotlindemos.basic

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_initial.*
import pro.pjcs.kotlindemos.R
import pro.pjcs.kotlindemos.base.BaseActivity
import pro.pjcs.kotlindemos.models.Filter
import pro.pjcs.kotlindemos.models.FilterParams
import pro.pjcs.kotlindemos.models.FilterType
import pro.pjcs.kotlindemos.models.User


class InitialActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_initial)

        //synthetic properties
        welcome_message.text = "Welcome to Kotlin Demos"

        val data : ArrayList<Any> = arrayListOf(
            User("1", "Dona", "Odeti", 15),
            User("2", "John", "Second", 45)
        )


        val filter = Filter(listOf(FilterParams(User::firstName.name, FilterType.EQUALS, "Dona")))

        button_open_lists.setOnClickListener { startActivity( listActivityIntent(filter, data) ) }


    }



}
