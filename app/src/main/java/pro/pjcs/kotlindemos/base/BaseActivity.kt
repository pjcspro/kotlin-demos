package pro.pjcs.kotlindemos.base

import android.app.Activity

//Kotlin classes are final by default. If you want to allow the class inheritance, mark the class with the open modifier.
open class BaseActivity : Activity() {

    protected val TAG = this::class.java.simpleName

}