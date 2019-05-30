package pro.pjcs.kotlindemos.basic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import pro.pjcs.kotlindemos.base.BaseActivity
import pro.pjcs.kotlindemos.models.DemoInfo

class ListsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val complexity = intent.getIntExtra(INTENT_COMPLEXITY, 0);
        Log.v(TAG, "Complexity is $complexity")

    }


}

//- Creates a new Intent to be passed to StartActivity
private const val INTENT_COMPLEXITY = "INTENT_COMPLEXITY"
fun Context.ListActivityIntent(info: DemoInfo) : Intent {
    return Intent(this, ListsActivity::class.java).apply {
        putExtra(INTENT_COMPLEXITY, info.complexity)
    }
}

