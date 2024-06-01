package com.synrgy.mobielib.presentation.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgy.mobielib.data.local.UserModel
import com.synrgy.mobielib.databinding.FragmentRegisterBinding
import com.synrgy.mobielib.common.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _fragmentRegisterBinding: FragmentRegisterBinding? = null
    private val fragmentRegisterBinding get() = _fragmentRegisterBinding!!
    private val viewModel : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _fragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        return fragmentRegisterBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionRegister()
        checkConfirmationPassword()
    }

    private fun actionRegister() {
        fragmentRegisterBinding.actionRegister.setOnClickListener {
            val username = fragmentRegisterBinding.etUsername.text.toString()
            val email = fragmentRegisterBinding.etEmail.text.toString()
            val password = fragmentRegisterBinding.etPassword.text.toString()
            val passwordConfirm = fragmentRegisterBinding.etConfirmPassword.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showToast("Please fill all fields")
            } else {
                viewModel.register(
                    UserModel(email = email, username = username, password = password)
                ).observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            showToast("Register Success")
                            findNavController().popBackStack()
                        }

                        is Resource.Error -> showToast(it.exception)
                        Resource.Loading -> {}
                    }
                }
            }
        }
    }

    private fun checkConfirmationPassword(){
        fragmentRegisterBinding.etPassword.addTextChangedListener(passwordTextWatcher)
        fragmentRegisterBinding.etConfirmPassword.addTextChangedListener(passwordTextWatcher)
    }

    private val passwordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            checkPassword(s.toString(), fragmentRegisterBinding.etConfirmPassword.text.toString())
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            checkPassword(s.toString(), fragmentRegisterBinding.etConfirmPassword.text.toString())
        }

        override fun afterTextChanged(s: Editable?) {
            checkPassword(s.toString(), fragmentRegisterBinding.etConfirmPassword.text.toString())
        }
    }

    private fun checkPassword(password: String, passwordConfirm: String) {
        if (passwordConfirm != password) {
            fragmentRegisterBinding.confirmPassword.error = "Password not match"
            fragmentRegisterBinding.actionRegister.isEnabled = false
        } else {
            fragmentRegisterBinding.confirmPassword.error = null
            fragmentRegisterBinding.actionRegister.isEnabled = true
        }
    }

    private fun showToast(mssg: String?) {
        Toast.makeText(requireContext(), mssg, Toast.LENGTH_LONG).show()
    }

}