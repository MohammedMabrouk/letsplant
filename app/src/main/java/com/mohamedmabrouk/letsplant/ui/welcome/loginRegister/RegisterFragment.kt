package com.mohamedmabrouk.letsplant.ui.welcome.loginRegister

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.data.DevicePreferences
import com.mohamedmabrouk.letsplant.databinding.FragmentRegisterBinding
import com.mohamedmabrouk.letsplant.ui.home.HomeActivity
import com.mohamedmabrouk.letsplant.util.CrashlyticsManager
import com.mohamedmabrouk.letsplant.util.hideKeyboard

class RegisterFragment : Fragment(), OnCompleteListener<AuthResult> {

    lateinit var devicePreferences: DevicePreferences

    private val TAG = "RegisterFragment"
    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        devicePreferences = DevicePreferences(activity as Context)
        auth = Firebase.auth

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.btnRegister.setOnClickListener {
            validateInput()
        }

        binding.tvLogin.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return binding.root
    }

    private fun validateInput() {
        // reset
        binding.tfEmail.error = null
        binding.tfPassword.error = null
        binding.tfConfirmPassword.error = null

        if (binding.etEmail.text.isNullOrEmpty()) {
            binding.tfEmail.error = getString(R.string.empty_email_msg)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text).matches()) {
            binding.tfEmail.error = getString(R.string.wrong_email_msg)
        } else if (binding.etPassword.text.isNullOrEmpty()) {
            binding.tfPassword.error = getString(R.string.empty_password_msg)
        } else if (binding.etPassword.text.toString().length < 6) {
            binding.tfPassword.error = getString(R.string.password_length_msg)
        } else if (binding.etConfirmPassword.text.isNullOrEmpty()) {
            binding.tfConfirmPassword.error = getString(R.string.empty_password_msg)
        } else if (binding.etConfirmPassword.text.toString().length < 6) {
            binding.tfConfirmPassword.error = getString(R.string.password_length_msg)
        } else if (binding.etConfirmPassword.text.toString() != binding.etPassword.text.toString()) {
            binding.tfConfirmPassword.error = getString(R.string.confirm_password_not_match)
        } else {
            signupUser(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun signupUser(email: String, password: String) {
        hideKeyboard()
        binding.btnRegister.visibility = View.INVISIBLE
        binding.pbLoading.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this)
    }

    override fun onComplete(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "createUserWithEmail:success")
            val user = auth.currentUser
            // save user data
            devicePreferences.saveUserId(user?.uid!!)
            devicePreferences.saveLoginState(true)
            startActivity(Intent(activity, HomeActivity::class.java))
            activity?.finish()
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "createUserWithEmail:failure", task.exception)
            CrashlyticsManager.logString("$TAG : Error", task.exception.toString())
            Toast.makeText(activity, "Authentication failed: ${task.exception.toString()}",
                Toast.LENGTH_SHORT).show()
            binding.btnRegister.visibility = View.VISIBLE
            binding.pbLoading.visibility - View.INVISIBLE
        }
    }
}