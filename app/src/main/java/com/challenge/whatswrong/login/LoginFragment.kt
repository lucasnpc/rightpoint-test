package com.challenge.whatswrong.login

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import com.challenge.whatswrong.R
import com.challenge.whatswrong.core.launchWhenViewCreated
import com.challenge.whatswrong.core.viewBinding
import com.challenge.whatswrong.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel by viewModels<LoginViewModel>()

    init {
        launchWhenViewCreated {
            with(binding) {
                loginButton.setOnClickListener {
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
                // TODO
            }
            is State.LoginFailed -> {
                // TODO
            }
            is State.LoginSucceeded -> {
                val directions = LoginFragmentDirections.actionToHome(
                    firstName = state.session.firstName,
                    lastName = state.session.lastName
                )
                findNavController().navigate(directions)
            }
        }
    }

}
