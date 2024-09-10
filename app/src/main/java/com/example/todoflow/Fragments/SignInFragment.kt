package com.example.todoflow.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todoflow.R
import com.example.todoflow.databinding.FragmentSignInBinding
import com.example.todoflow.databinding.FragmentSignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


class SignInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        registerEvents()
    }


    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }


    private fun registerEvents() {

        binding.authSignInTxt.setOnClickListener {
            navController.navigate(R.id.action_SIgnInFragment_to_signUpFragment)
        }

        binding.btnUp.setOnClickListener {
            val email = binding.emailTxt.text.toString().trim()
            val pass = binding.passtxt.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                binding.pb2.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(
                    OnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(context, "Signed in successfully", Toast.LENGTH_SHORT)
                                .show()
                            navController.navigate(R.id.action_SIgnInFragment_to_homeFragment)
                        } else {
                            Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        binding.pb2.visibility = View.GONE
                    })
            } else {
                Toast.makeText(context, "Empty fields not allowed", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}