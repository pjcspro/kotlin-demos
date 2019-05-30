package pro.pjcs.kotlindemos.basic

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_initial.*
import pro.pjcs.kotlindemos.R
import pro.pjcs.kotlindemos.base.BaseActivity
import pro.pjcs.kotlindemos.models.DemoInfo

class InitialActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_initial)

        //synthetic properties
        welcome_message.text = "Welcome to Kotlin Demos"

        var demoInfo = DemoInfo("My First Demo").apply {
            complexity = 1
        }
        button_open_lists.setOnClickListener { startActivity( ListActivityIntent(demoInfo) ) }


    }

}