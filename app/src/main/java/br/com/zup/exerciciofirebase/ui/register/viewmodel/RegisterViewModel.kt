package br.com.zup.exerciciofirebase.ui.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.exerciciofirebase.domain.model.User
import br.com.zup.exerciciofirebase.domain.repository.AuthenticationRepository
import br.com.zup.exerciciofirebase.utils.CREATE_USER_ERROR_MESSAGE
import br.com.zup.exerciciofirebase.utils.EMAIL_ERROR_MESSAGE
import br.com.zup.exerciciofirebase.utils.NAME_ERROR_MESSAGE
import br.com.zup.exerciciofirebase.utils.PASSWORD_ERROR_MESSAGE

class RegisterViewModel() : ViewModel() {
    private var repository = AuthenticationRepository()

    private var _registerState = MutableLiveData<User>()
    val registerState: LiveData<User> = _registerState

    private var _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    fun validateDataUser(user: User) {
        when {
            user.name.isEmpty() -> {
                _errorState.value = NAME_ERROR_MESSAGE
            }
            user.email.isEmpty() -> {
                _errorState.value = EMAIL_ERROR_MESSAGE
            }
            user.password.isEmpty() -> {
                _errorState.value = PASSWORD_ERROR_MESSAGE
            }
            else -> {
                registerUser(user)
            }
        }
    }

    private fun registerUser(user: User) {
        try {
            repository.registerUser(
                user.email,
                user.password
            ).addOnSuccessListener {

                repository.updateUserProfile(user.name)?.addOnSuccessListener {
                    _registerState.value = user
                }

            }.addOnFailureListener {
                _errorState.value = CREATE_USER_ERROR_MESSAGE + it.message
            }
        } catch (ex: Exception) {
            _errorState.value = ex.message
        }
    }
}
