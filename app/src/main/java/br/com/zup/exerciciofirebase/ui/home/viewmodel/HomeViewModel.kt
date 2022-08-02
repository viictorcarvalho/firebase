package br.com.zup.exerciciofirebase.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import br.com.zup.exerciciofirebase.domain.repository.AuthenticationRepository

class HomeViewModel : ViewModel() {
    private val repository = AuthenticationRepository()

    fun getNameUser() = repository.getNameUser()

    fun getEmailUser() = repository.getEmailUser()

    fun logoutUser() {
        repository.logoutOut()
    }
}