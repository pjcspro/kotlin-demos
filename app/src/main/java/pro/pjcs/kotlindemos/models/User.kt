package pro.pjcs.kotlindemos.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
    @Parcelize requires all serialized properties to be declared in the primary constructor.
    For more complex details validates: https://kotlinlang.org/docs/tutorials/android-plugin.html#parcelable-implementations-generator
 */


@Parcelize
class User(val userID : String, val firstName : String, val lastName : String, val age : Int?) : Parcelable {



}