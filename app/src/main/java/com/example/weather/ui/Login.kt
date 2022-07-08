package com.example.weather.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.databinding.FragmentLoginBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class Login : Fragment() {
    private var auth = FirebaseAuth.getInstance()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val RC_SIGN_IN = 100
        private const val TAG = "Google signin tag"
    }

    private lateinit var googleSignInClient: GoogleSignInClient

    // Initialize Facebook Login button
    var callbackManager = CallbackManager.Factory.create()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Google login with firebase
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        //let,s init firebase auth


        checkUser()
        binding.btnGoogle.setOnClickListener {
            Log.d(TAG, "onViewCreated: begin google signin")
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }


        //Facebook login with firebase
        binding.btnFacebook.setOnClickListener {
            if (userLoggedIn()) {
                auth.signOut()
            } else {
                LoginManager.getInstance()
                    .logInWithReadPermissions(this, listOf("public_profile", "email"))
            }
        }

        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d(TAG, "facebook:onSuccess:$loginResult")
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.d(TAG, "facebook:onCancel")
                }

                override fun onError(error: FacebookException) {
                    Log.d(TAG, "facebook:onError", error)
                }
            })
    }


    private fun checkUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
//            user already loggedIn
            Log.d(TAG, "checkUser: ")
            Intent(context, MainActivity::class.java).apply {
                startActivity(this)
            }
            requireActivity().finish()

        }
    }


    fun userLoggedIn(): Boolean {
        return auth.currentUser != null && AccessToken.getCurrentAccessToken()!!.isExpired
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)

        // pass the activity result back to the google SDK
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "onActivityResult: Google signin intent result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                fireBaseAuthWithGoogleAccount(account)
            } catch (e: Exception) {
                Log.d(TAG, "onActivityResult: ${e.message}")
            }
        }


    }

    private fun fireBaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                Log.d(TAG, "fireBaseAuthWithGoogleAccount: Successfully Logged")
                Toast.makeText(requireContext(), "Successfully logged in", Toast.LENGTH_SHORT)
                    .show()
                val firebaseUser = auth.currentUser
                val email = firebaseUser!!.email
                if (it.additionalUserInfo!!.isNewUser) {
                    Log.d(TAG, "fireBaseAuthWithGoogleAccount: Account Created...\n$email")
                    Toast.makeText(
                        requireContext(),
                        "Account created successfully:$email",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Log.d(TAG, "fireBaseAuthWithGoogleAccount: Existing User :$email")
                    Toast.makeText(requireContext(), "LoggedIn...\n$email", Toast.LENGTH_SHORT)
                        .show()
                }
                Intent(context, MainActivity::class.java).apply {
                    startActivity(this)
                }
                requireActivity().finish()

            }


            .addOnFailureListener {
                Log.d(TAG, "fireBaseAuthWithGoogleAccount: Login failed due to :${it.message}")
                Toast.makeText(
                    requireContext(),
                    "Login failed due to :${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }


    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    Toast.makeText(
                        requireContext(),
                        "Facebook successfully logged in",
                        Toast.LENGTH_SHORT
                    ).show()
                    val user = auth.currentUser
                    Intent(context, MainActivity::class.java).apply {
                        startActivity(this)
                    }
                    requireActivity().finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)

                    Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT)
                        .show()


                }
            }
    }


}