package br.com.zup.exerciciofirebase.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.exerciciofirebase.domain.model.User
import br.com.zup.exerciciofirebase.domain.repository.AuthenticationRepository
import br.com.zup.exerciciofirebase.utils.ENTER_EMAIL
import br.com.zup.exerciciofirebase.utils.ENTER_PASSWORD
import br.com.zup.exerciciofirebase.utils.LOGIN_ERROR_MESSAGE

class LoginViewModel : ViewModel() {
    private val repository = AuthenticationRepository()

    private var _loginState = MutableLiveData<User>()
    val loginState: LiveData<User> = _loginState

    private var _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    fun validateDataUser(user: User) {
        when {
            user.email.isEmpty() -> {
                _errorState.value = ENTER_EMAIL
            }
            user.password.isEmpty() -> {
                _errorState.value = ENTER_PASSWORD
            }
            else -> {
                loginUser(user)
            }
        }
    }

    private fun loginUser(user: User) {
        try {
            repository.loginUser(
                user.email,
                user.password
            ).addOnSuccessListener {
                _loginState.value = user
            }.addOnFailureListener {
                _errorState.value = LOGIN_ERROR_MESSAGE + it.message
            }
        } catch (ex: Exception) {
            _errorState.value = ex.message
        }
    }
}