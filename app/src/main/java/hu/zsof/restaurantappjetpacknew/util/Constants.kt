package hu.zsof.restaurantappjetpacknew.util

object Constants {

    // raspberry
    //const val BASE_URL = "http://wildfire.ddns.net:8092"

    // local
    //  const val BASE_URL = "http://192.168.2.48:8080"

    //remote
    const val BASE_URL = "https://dolphin-casual-deer.ngrok-free.app/restaurant/"

    // azure
    // const val BASE_URL = "http://restaurant.ceeeaufagderbhhg.germanywestcentral.azurecontainer.io:8080"

    // Length: 6-24 char, at least 1 Uppercase, 1 Number
    const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{6,24}$"
    const val EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"

    /**
     * Shared preferences
     */
    object Prefs {
        const val SHARED_PREFERENCES = "restaurant_app_shared_prefs"
        const val AUTH_SHARED_PREFERENCES = "auth_shared_pref"

        const val DARK_MODE = "dark_mode"
        const val USER_RATED = "user_rated"
        const val USER_LOGGED = "user_logged"
        const val USER_TYPE = "user_type"
    }

    /**
     * Navigation
     */
    const val ROOT_GRAPH_ROUTE = "root"
    const val LOGIN_START = "login_start"
    const val HOME_START = "home_start"
    const val FAV_START = "fav_start"

    /**
     * User tpye
     */
    const val ROLE_ADMIN = "ROLE_ADMIN"
    const val ROLE_OWNER = "ROLE_OWNER"
    const val ROLE_USER = "ROLE_USER"

    /**
     * Image handle - type
     */
    const val IMAGE_PLACE_TYPE = "place"
    const val IMAGE_PLACE_IN_REVIEW_TYPE = "place-in-review"
    const val IMAGE_USER_TYPE = "user"
}
