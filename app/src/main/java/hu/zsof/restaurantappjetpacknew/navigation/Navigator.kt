package hu.zsof.restaurantappjetpacknew.navigation

import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class Navigator @Inject constructor() {

    val destination: MutableLiveData<ScreenModel.NavigationScreen> =
        MutableLiveData(ScreenModel.NavigationScreen.Login)

}