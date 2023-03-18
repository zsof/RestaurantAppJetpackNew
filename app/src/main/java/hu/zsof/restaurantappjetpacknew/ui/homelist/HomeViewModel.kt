package hu.zsof.restaurantappjetpacknew.ui.homelist

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    val places = mutableListOf<Place>()

    init {
        loadData()
    }

    fun loadData() {
        places.add(Place(id = 1, name = "Stefánia étterem", address = "1146 Budapest Stefánia út", rate = 4.0f, price = Price.LOW))
        places.add(Place(id = 2, name = "Frei Café", address = "Budapest Örs vezér tere 25", rate = 4.5f, price = Price.HIGH))
        places.add(Place(id = 3, name = "Vapiano", address = "Budapest Vörösmarty tér 3", rate = 3.0f, price = Price.MIDDLE))
        places.add(Place(id = 1, name = "Stefánia étterem", address = "1146 Budapest Stefánia út", rate = 4.0f, price = Price.LOW))
        places.add(Place(id = 2, name = "Frei Café", address = "Budapest Örs vezér tere 25", rate = 4.5f, price = Price.HIGH))
        places.add(Place(id = 1, name = "Stefánia étterem", address = "1146 Budapest Stefánia út", rate = 4.0f, price = Price.LOW))
        places.add(Place(id = 3, name = "Vapiano", address = "Budapest Vörösmarty tér 3", rate = 3.0f, price = Price.MIDDLE))
        places.add(Place(id = 2, name = "Frei Café", address = "Budapest Örs vezér tere 25", rate = 4.5f, price = Price.HIGH))
        places.add(Place(id = 3, name = "Vapiano", address = "Budapest Vörösmarty tér 3", rate = 3.0f, price = Price.MIDDLE))
        places.add(Place(id = 1, name = "Stefánia étterem", address = "1146 Budapest Stefánia út", rate = 4.0f, price = Price.LOW))
        places.add(Place(id = 2, name = "Frei Café", address = "Budapest Örs vezér tere 25", rate = 4.5f, price = Price.HIGH))
        places.add(Place(id = 3, name = "Vapiano", address = "Budapest Vörösmarty tér 3", rate = 3.0f, price = Price.MIDDLE))
    }
}
