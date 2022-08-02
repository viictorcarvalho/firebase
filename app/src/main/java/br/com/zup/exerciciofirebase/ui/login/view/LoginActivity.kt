package br.com.zup.exerciciofirebase.ui.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.zup.exerciciofirebase.databinding.ActivityLoginBinding
import br.com.zup.exerciciofirebase.domain.model.User
import br.com.zup.exerciciofirebase.ui.home.view.HomeActivity
import br.com.zup.exerciciofirebase.ui.login.viewmodel.LoginViewModel
import br.com.zup.exerciciofirebase.ui.register.view.RegisterActivity
import br.com.zup.exerciciofirebase.utils.LOGIN_ERROR_MESSAGE
import br.com.zup.exerciciofirebase.utils.USER_KEY

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegisterNow.setOnClickListener {
            goToRegister()
        }

        binding.bvLogin.setOnClickListener {
            val user = getDataUser()
            viewModel.validateDataUser(user)
        }

        initObservers()
    }

    private fun getDataUser(): User {
        return User(
            email = binding.etEmail.text.toString(),
            password = binding.etPassword.text.toString()
        )
    }

    private fun goToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun initObservers() {
        viewModel.loginState.observe(this) {
            goToHome(it)
        }

        viewModel.errorState.observe(this) {
            Toast.makeText(this, LOGIN_ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToHome(user: User) {
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra(USER_KEY, user)
        }
        startActivity(intent)
    }
}
