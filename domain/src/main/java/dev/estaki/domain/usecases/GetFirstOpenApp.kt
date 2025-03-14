package dev.estaki.domain.usecases

import dev.estaki.domain.sharedPrefrence.FirstOpenApp
import dev.estaki.domain.sharedPrefrence.PreferenceHelper

class GetFirstOpenApp (private val preferenceHelper: PreferenceHelper) {
    operator fun invoke():Long{
        return preferenceHelper.getLong(FirstOpenApp)
    }
}