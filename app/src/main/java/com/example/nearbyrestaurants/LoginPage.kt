package com.example.nearbyrestaurants

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.constraintlayout.widget.Constraints
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login_page.view.*
import java.util.concurrent.Executor


class LoginPage : Fragment() {

    companion object{
        private const val RC_SIGN_IN = 120
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var executor: Executor
    var callbackManager : CallbackManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_login_page, container, false)

        auth = FirebaseAuth.getInstance()
        executor = ContextCompat.getMainExecutor(context)
        val biometricManager = context?.let { BiometricManager.from(it) }
        view.fingerprint.setOnClickListener {
            when (biometricManager?.canAuthenticate()) {
                BiometricManager.BIOMETRIC_SUCCESS ->
                    authUser(executor)
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                    Log.d(
                        Constraints.TAG,
                        "error_msg_no_biometric_hardware"
                    )
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                    Log.d(
                        Constraints.TAG,
                        "error_msg_biometric_hw_unavailable"
                    )
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                    Log.d(
                        Constraints.TAG,
                        "error_msg_biometric_not_setup"
                    )
            }
        }
        return view
    }

    private fun authUser(executor: Executor) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            // 2
            .setTitle("Biometric login for Nearby Restaurants")
            // 3
            .setSubtitle("Log in using your biometric credential")
            // 4
            .setDescription("You can also use Facebook or Google login options")
            // 5
            .setDeviceCredentialAllowed(true)
            // 6
            .build()

        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                // 2
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    view?.let {
                        Navigation.findNavController(it)
                            .navigate(R.id.goToRestaurantListPage)
                    }

                }

                // 3
                override fun onAuthenticationError(
                    errorCode: Int, errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)

                }

                // 4
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            })

        biometricPrompt.authenticate(promptInfo)
    }

}