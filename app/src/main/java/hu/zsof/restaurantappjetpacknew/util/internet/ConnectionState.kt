package hu.zsof.restaurantappjetpacknew.util.internet

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}
