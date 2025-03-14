package dev.estaki.myFinancialApp.workers

import android.content.Context
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit

object WorkersEngine {
    private fun workerBuild(
        duration: Long,
        timeUnit: TimeUnit,
        smsSender: String,
        smsMessage: String
    ): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<SmsReminderWorker>()
            .setInitialDelay(duration, timeUnit)
            .setInputData(workDataOf("smsMessage" to smsMessage, "smsSender" to smsSender))
            .build()
    }

    fun scheduleSmsRemindersWithWorkManager(
        duration: Long,
        timeUnit: TimeUnit,
        context: Context,
        smsSender: String,
        smsMessage: String
    ) {
        WorkManager.getInstance(context).enqueue(workerBuild(duration,timeUnit, smsSender, smsMessage))
    }

}