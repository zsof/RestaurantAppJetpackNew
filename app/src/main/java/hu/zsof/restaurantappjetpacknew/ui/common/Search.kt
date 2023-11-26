package hu.zsof.restaurantappjetpacknew.ui.common

import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.ui.homelist.HomeViewModel

fun search(
    globalFilteredPlaces: List<Place>?,
    userFilteredPlaces: MutableList<Place>,
    viewModel: HomeViewModel,
    searchItems: MutableList<Place>,
) {
    if (viewModel.searchText.value.isNotEmpty()) {
        AppState.searchedPlaces.value = mutableListOf()
        viewModel.isSearchListNotMatchWithPlaces.value = false

        searchItems.clear()

        if (!globalFilteredPlaces.isNullOrEmpty()) {
            globalFilteredPlaces.forEach { place ->

                if (place.name.contains(
                        viewModel.searchText.value,
                        ignoreCase = true,
                    )
                ) {
                    searchItems.add(place)
                }
            }
        } else {
            userFilteredPlaces.forEach { place ->

                if (place.name.contains(
                        viewModel.searchText.value,
                        ignoreCase = true,
                    )
                ) {
                    searchItems.add(place)
                }
            }
        }
        if (searchItems.isNotEmpty())
            AppState.searchedPlaces.value = searchItems
        else {
            /**
             * Azért mert ha üres a találat, akkor a placeList-nél nem is menne ezeknek a megjelenítésébe (ami ugye üres lista lenne), hanem megjeleníti az összeset
             */
            viewModel.isSearchListNotMatchWithPlaces.value = true
        }
    } else {
        /**
         * Ha nincs keresendő szöveg
         */
        AppState.searchedPlaces.value = mutableListOf()
        viewModel.isSearchListNotMatchWithPlaces.value = false
    }
}
