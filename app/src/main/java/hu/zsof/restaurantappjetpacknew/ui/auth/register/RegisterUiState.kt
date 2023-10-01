package hu.zsof.restaurantappjetpacknew.ui.auth.register

data class RegisterUiState(
    val email: String = "",
    val isEmailError: Boolean = false,

    val userName: String = "",
    val isUserNameError: Boolean = false,

    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isPasswordError: Boolean = false,

    val isOwner: Boolean = false
)

