package dev.estaki.myFinancialApp

import dev.estaki.domain.models.SmsRawModel
import dev.estaki.domain.processor.SmsProcessor
import dev.estaki.domain.validator.SmsValidator

fun main(args: Array<String>) {
    val listOfSender = listOf<String>(
        "TejaratBank", "B.QMEHRIRAN"
    )
    val list = listOf<String>(
        "10.5767872.1\n" +
                "-7,833,683 \n" +
                "06/04_21:01\n" +
                "مانده: 100,816",





    ).mapIndexed { index: Int, item: String ->
        SmsRawModel(
            _id = index.toString(),
            senderName = listOfSender.random(),
            description = item,
            "", ""
        )
    }


    SmsProcessor(list.toMutableList()).execute()


}