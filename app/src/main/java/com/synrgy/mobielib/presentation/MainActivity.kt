package com.synrgy.mobielib.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.synrgy.mobielib.R
import com.synrgy.mobielib.common.navigation.HomeNavigation
import com.synrgy.mobielib.presentation.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        checkSession()
    }

    private fun checkSession() {
        viewModel.checkSession().observe(this) { user ->
            if ((user.username == "") && (user.password == "")) {
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            } else {
                val composeView = findViewById<ComposeView>(R.id.composeView)
                composeView.apply {
                    setContent {
                        HomeNavigation(
                            user = user,
                            onLogOut = {viewModel.clearSession()}
                        )
                    }
                }
            }
        }
    }
}