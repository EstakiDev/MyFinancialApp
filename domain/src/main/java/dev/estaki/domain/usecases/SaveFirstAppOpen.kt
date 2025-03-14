package dev.estaki.domain.usecases

import dev.estaki.domain.sharedPrefrence.FirstOpenApp
import dev.estaki.domain.sharedPrefrence.PreferenceHelper
import java.util.Date

class SaveFirstAppOpen (private val preferenceHelper: PreferenceHelper) {
    operator fun invoke(date: Date){
        preferenceHelper.saveLong(FirstOpenApp,date.time)
    }
}