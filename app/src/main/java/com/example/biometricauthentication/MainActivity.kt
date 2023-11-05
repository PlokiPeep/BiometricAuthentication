package com.example.biometricauthentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor
class MainActivity : AppCompatActivity() {

    // Declare a Button variable
    private lateinit var btnAuth: Button

    private lateinit var tvAuthStatus: TextView

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAuth = findViewById(R.id.btnAuth)
        tvAuthStatus = findViewById(R.id.tvAuthStatus)

        // Initialize the values
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                // Error whilst authenticating
                tvAuthStatus.text = "Error: $errString"
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                val authMethod = result.authenticationType
                tvAuthStatus.text = "Successfully Authenticated by $authMethod"


            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                tvAuthStatus.text = "Failed Authentication"
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint or face")
            .setNegativeButtonText("Cancel")
            .build()

        btnAuth.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }
}