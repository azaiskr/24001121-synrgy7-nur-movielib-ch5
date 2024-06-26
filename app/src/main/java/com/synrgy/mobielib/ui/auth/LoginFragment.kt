package com.synrgy.mobielib.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgy.mobielib.MainActivity
import com.synrgy.mobielib.R
import com.synrgy.mobielib.databinding.FragmentLoginBinding
import com.synrgy.mobielib.utils.Response
import com.synrgy.mobielib.utils.ViewModelFactory

class LoginFragment : Fragment() {

    private var _fragmentLoginBinding: FragmentLoginBinding? = null
    private val fragmentLoginBinding get() = _fragmentLoginBinding!!

    private val viewModel by viewModels<AuthViewModel> {
        ViewModelFactory.getInstanceViewModel(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return fragmentLoginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionLogin()
        toRegisterPage()
    }

    private fun toRegisterPage() {
        fragmentLoginBinding.register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun actionLogin() {
        fragmentLoginBinding.actionLogin.setOnClickListener {
            val email = fragmentLoginBinding.etEmail.text.toString()
            val password = fragmentLoginBinding.etPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                showToast("Please fill email and password")
            } else {
                viewModel.login(email, password).observe(viewLifecycleOwner) {
                    when (it) {
                        is Response.Success -> {
                            viewModel.saveSession(it.data)
                            showToast("Welcome ${it.data.username}")

                            startActivity(Intent(activity, MainActivity::class.java))
                            activity?.finish()
                        }

                        is Response.Error -> {
                            showToast(it.exception)
                        }
                        else -> {}
                    }
                }
            }
        }

    }


    private fun showToast(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}