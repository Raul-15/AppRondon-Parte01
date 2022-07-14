package sebastiani.app_rondon.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


// START
@Parcelize
data class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val image: String = "",
    val mobile: Long = 0,
    val dni: String ="",
    //val gender: String = "",
    val address: String=""
    //val profileCompleted: Int = 0
) : Parcelable
// END