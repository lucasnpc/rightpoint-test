package com.challenge.whatswrong.login

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import com.challenge.whatswrong.R
import com.challenge.whatswrong.core.launchWhenViewCreated
import com.challenge.whatswrong.core.viewBinding
import com.challenge.whatswrong.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel by viewModels<LoginViewModel>()

    init {
        launchWhenViewCreated {
            with(binding) {
                usernameField.addCommonTextWatcher()
                passwordField.addCommonTextWatcher()
                loginButton.setOnClickListener {
                    usernameField.text?.let {
                        if (it.length < 3) {
                            usernameField.error = getString(R.string.username_error)
                            return@setOnClickListener
                        }
                    }
                    passwordField.text?.let {
                        if (it.length < 4) {
                            passwordField.error = getString(R.string.password_error)
                            return@setOnClickListener
                        }
                    }
                    viewModel.login(userName = usernameField.text, password = passwordField.text)
                }
            }

            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { state -> render(state) }
        }
    }

    private fun render(state: State) {
        when (state) {
            State.Idle -> {
                // TODO
            }
            State.Loading -> {
                showProgress()
            }
            is State.LoginFailed -> {
                hideProgress()
                Snackbar.make(requireView(), R.string.something_went_wrong, Snackbar.LENGTH_SHORT)
                    .show()
            }
            is State.LoginSucceeded -> {
                hideProgress()
                val directions = LoginFragmentDirections.actionToHome(
                    firstName = state.session.firstName,
                    lastName = state.session.lastName
                )
                findNavController().navigate(directions)
            }
        }
    }

    private fun showProgress() {
        binding.loginProgress.visibility = View.VISIBLE
        binding.loginButton.isEnabled = false
    }

    private fun hideProgress() {
        binding.loginProgress.visibility = View.GONE
        binding.loginButton.isEnabled = true
    }

    private companion object {
    }
}

fun TextInputEditText.addCommonTextWatcher() {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            TODO("Not yet implemented")
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            this@addCommonTextWatcher.error = null
        }

        override fun afterTextChanged(p0: Editable?) {
//            TODO("Not yet implemented")
        }

    })
}
