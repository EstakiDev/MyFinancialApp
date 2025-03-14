package dev.estaki.myFinancialApp.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import dev.estaki.myFinancialApp.NotificationHandler

class SmsReminderWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val smsSender = inputData.getString("smsSender") ?: return Result.failure()
        val smsMessage = inputData.getString("smsMessage") ?: return Result.failure()
        NotificationHandler.showSmsNotification(
            context,
            title = "یادآوری تراکنش بانکی",
            message = "اگه اشتباه نکنم چندساعت پیش تراکنشی داشتی که بهتره یادداشت کنی که برای چی بوده!",
            smsSender,
            smsMessage
        )
        return Result.success()
    }
}