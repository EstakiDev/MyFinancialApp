package dev.estaki.myFinancialApp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import dev.estaki.domain.models.SmsRawModel
import dev.estaki.domain.processor.SmsProcessor
import dev.estaki.domain.usecases.UpsertSms
import dev.estaki.domain.validator.SmsValidator
import dev.estaki.myFinancialApp.workers.WorkersEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SmsReceiver : BroadcastReceiver() {

    private val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private val TAG = "SMSBroadcastReceiver";

    override fun onReceive(context: Context, intent: Intent) {
        Timber.tag(TAG).d("onReceive: SmsReceiver")
        if (intent.action == SMS_RECEIVED) {

            val bundle: Bundle? = intent.extras
            if (bundle != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    val pdus = bundle.get("pdus") as? Array<*>
                    val format = bundle.getString("format")
                    val strBuildMessageBody = StringBuilder()
                    var smsSender = ""

                    if (pdus != null) {
                        pdus.forEach { pdu ->
                            val smsMessage: SmsMessage =
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    SmsMessage.createFromPdu(pdu as ByteArray, format)
                                } else {
                                    SmsMessage.createFromPdu(pdu as ByteArray)
                                }

                            val sender = smsMessage.originatingAddress
                            val message = smsMessage.messageBody
                            strBuildMessageBody.append(message)
                            smsSender = sender.toString()
                            // پردازش پیام دریافتی
                            Timber.tag("SmsReceiver")
                                .d("پیام از شماره: $sender محتوای پیام: $message")
                        }

                    }
                    Timber.tag(TAG)
                        .d("onReceive: \"SmsReceiver Final: ${smsSender} -> ${strBuildMessageBody}\"")

                    delay(5_000)
                    if (SmsValidator.isBankSms(
                            SmsRawModel(
                                "1",
                                senderName = smsSender,
                                description = strBuildMessageBody.toString(),
                                "",
                                ""
                            )
                        )
                    ) {
                        NotificationHandler.showSmsNotification(
                            context,
                            title = "مثل اینکه تراکنش جدید داری 👇",
                            message = "بهتره که بخونیش و براش تصمیم بگیری",
                            smsSender = smsSender,
                            smsBody = strBuildMessageBody.toString()
                        )
                        WorkersEngine.scheduleSmsRemindersWithWorkManager(
                            duration = 4,
                            timeUnit = TimeUnit.HOURS,
                            context = context,
                            smsSender = smsSender,
                            smsMessage = strBuildMessageBody.toString()
                        )
                        WorkersEngine.scheduleSmsRemindersWithWorkManager(
                            duration = 12,
                            timeUnit = TimeUnit.HOURS,
                            context = context,
                            smsSender = smsSender,
                            smsMessage = strBuildMessageBody.toString()
                        )

                    } else {
                        Timber.tag(TAG).d("onReceive: sms not bank sms")
                    }
                }

            }

        }
    }
}