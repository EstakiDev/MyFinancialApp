package dev.estaki.myFinancialApp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import dev.estaki.myFinancialApp.presentation.main.MainActivity
import java.security.Permission

object NotificationHandler {
    private const val CHANNEL_ID = "bank_sms_channel"

    fun showSmsNotification(
        context: Context,
        title: String,
        message: String,
        smsSender: String,
        smsBody: String

    ){
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ){
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "Bank SMS Notifications",
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannels(listOf(channel))
            }

            var body = "فرستنده اش $smsSender هست\n "
            body = body.plus("\n")
            body = body.plus("\n")
            body = body.plus(smsBody)
            val notification = NotificationCompat.Builder(context,CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.monify)
                .setContentText(message)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true) // بستن اعلان هنگام کلیک
                .build()

            // نمایش نوتیفیکیشن
            notificationManager.notify(1, notification)
        }
    }
}