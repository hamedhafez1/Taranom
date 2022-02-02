package ir.roela.taranom.utils

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        GanjoorRetroHelper().getRandomPoet().enqueue(object : Callback<Poetry> {
//            override fun onResponse(call: Call<Poetry>, response: Response<Poetry>) {
//                if (response.body() !== null) {
//                    val poetry = response.body() as Poetry
//                    val text =
//                        "${poetry.bit_1}\n${poetry.bit_2}\n\n#${Utils.poetHashtag(poetry.poet)}"
//                    NotificationUtils.createNotification(
//                        this@MyFirebaseMessagingService,
//                        text,
//                        2022
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<Poetry>, t: Throwable) {
//
//            }
//        })
    }

    override fun onNewToken(s: String) {}
}