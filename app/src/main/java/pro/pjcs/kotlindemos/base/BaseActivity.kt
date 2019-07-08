package pro.pjcs.kotlindemos.base

import androidx.appcompat.app.AppCompatActivity

//Kotlin classes are final by default. If you want to allow the class inheritance, mark the class with the open modifier.
open class BaseActivity : AppCompatActivity() {

    protected val TAG = this::class.java.simpleName

}