package com.example.nearbyrestaurants.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.constraintlayout.widget.Constraints
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.nearbyrestaurants.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_login_page.view.*
import java.util.*
import java.util.concurrent.Executor


class LoginPage : Fragment() {

    companion object{
        private const val RC_SIGN_IN = 120
    }

    val PERMISSION_ID_XX = 1010
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val EMAIL = "email"
    private lateinit var executor: Executor
    var callbackManager : CallbackManager? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_login_page, container, false)
        showLocationPrompt()
        checkLocationPermission()

        googleSignInOpt()
        auth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()
        if(LoginManager.getInstance()!=null){
            LoginManager.getInstance().logOut()
        }
        googleSignInClient.signOut()

        executor = ContextCompat.getMainExecutor(context)
        val biometricManager = context?.let { BiometricManager.from(it) }



        /*val loginbutton = view.facebook_login_button
        loginbutton.fragment = this
        loginbutton.setPermissions(Arrays.asList(EMAIL,"public_profile"))
        loginbutton.registerCallback(callbackManager,object :FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                Log.d(Constraints.TAG, "facebook:onSuccess:$result")
                handleFacebookAccessToken(result?.accessToken)
            }

            override fun onCancel() {
                Toast.makeText(activity, "Facebook login canceled !! ",Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(activity, "Error occurred during authentication !! ",Toast.LENGTH_SHORT).show()
            }

        })*/

        view.facebook_login_button.setOnClickListener { facebookLogin() }



        view.fingerprint.setOnClickListener {
            when (biometricManager?.canAuthenticate()) {
                BiometricManager.BIOMETRIC_SUCCESS ->
                    authUser(executor)
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                    Toast.makeText(activity,"No biometric hardware",Toast.LENGTH_SHORT).show()
                    /*Log.d(
                        Constraints.TAG,
                        "error_msg_no_biometric_hardware"
                    )*/
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                    Toast.makeText(activity,"Biometric hardware unavailable",Toast.LENGTH_SHORT).show()
                    /*Log.d(
                        Constraints.TAG,
                        "error_msg_biometric_hw_unavailable"
                    )*/
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                    Toast.makeText(activity,"Please setup fingerprint on your device !!",Toast.LENGTH_SHORT).show()
                    /*Log.d(
                        Constraints.TAG,
                        "error_msg_biometric_not_setup"
                    )*/
            }
        }
        view.google_button.setOnClickListener {
            signIn()
        }


        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode,resultCode,data)

        when (requestCode) {
            LocationRequest.PRIORITY_HIGH_ACCURACY -> {
                if (resultCode == Activity.RESULT_OK) {
                    Log.e("Status: ","On")
                } else {
                    Log.e("Status: ","Off")
                }
            }
        }

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception

            if(task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                    // ...
                }
            }else{
                Log.w("SignInActivity",exception.toString())
            }

        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        activity?.let {
            auth.signInWithCredential(credential)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(activity, "Successfully logged in !!",Toast.LENGTH_SHORT).show()
                        view?.let { it1 -> Navigation.findNavController(it1).navigate(
                            R.id.goToRestaurantListPage
                        ) }

                    } else {
                        Toast.makeText(activity, "Login process unsuccessful",Toast.LENGTH_SHORT).show()
                        Log.w("SignInActivity", "signInWithCredential:failure", task.exception)
                        // ...
                    }

                    // ...
                }
        }
    }
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent,
            RC_SIGN_IN
        )
    }
    fun googleSignInOpt(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = activity?.let { GoogleSignIn.getClient(it,gso) }!!
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
                    Toast.makeText(activity, "Authentication canceled !! ",Toast.LENGTH_SHORT).show()
                }

                // 4
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(activity, "Authentication failed !!",Toast.LENGTH_SHORT).show()
                }
            })

        biometricPrompt.authenticate(promptInfo)
    }

    fun facebookLogin(){

        LoginManager.getInstance().logInWithReadPermissions(this, listOf("email","public_profile"))
        LoginManager.getInstance()
            .registerCallback(callbackManager,object : FacebookCallback<LoginResult> {

                override fun onSuccess(loginResult: LoginResult) {
                    Log.d(Constraints.TAG, "facebook:onSuccess:$loginResult")
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Toast.makeText(activity, "Facebook login canceled !! ",Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: FacebookException?) {
                    Toast.makeText(activity, "Error occurred during authentication !! ",Toast.LENGTH_SHORT).show()
                }

            })
    }


    fun handleFacebookAccessToken(token : AccessToken?){
        val credential = FacebookAuthProvider.getCredential(token?.token!!)
        auth.signInWithCredential(credential)
            .addOnCompleteListener{
                    task ->
                if(task.isSuccessful){
                    moveToListPage(task.result?.user)
                }else{
                    Log.d(Constraints.TAG,"error occured")
                }
            }
    }

    fun moveToListPage(user: FirebaseUser?){

        if(user != null){
            view?.let { Navigation.findNavController(it).navigate(R.id.goToRestaurantListPage) }
        }

    }
    private fun checkLocationPermission(): Boolean {
        if ((ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_ID_XX
                )
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_ID_XX
                )
            }
            return false
        } else {
            return true
        }
    }
    private fun showLocationPrompt() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val result: Task<LocationSettingsResponse> =
            activity?.let { LocationServices.getSettingsClient(it).checkLocationSettings(builder.build()) } as Task<LocationSettingsResponse>

        result.addOnCompleteListener { task ->
            try {
                val response = task.getResult(ApiException::class.java)
                // All location settings are satisfied. The client can initialize location
                // requests here.
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        try {
                            // Cast to a resolvable exception.
                            val resolvable: ResolvableApiException = exception as ResolvableApiException
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(
                                activity, LocationRequest.PRIORITY_HIGH_ACCURACY
                            )
                        } catch (e: IntentSender.SendIntentException) {
                            // Ignore the error.
                        } catch (e: ClassCastException) {
                            // Ignore, should be an impossible error.
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.

                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                    }
                }
            }
        }
    }

}