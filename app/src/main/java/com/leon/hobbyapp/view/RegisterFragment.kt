package com.leon.hobbyapp.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.leon.hobbyapp.databinding.FragmentRegisterBinding
import com.leon.hobbyapp.model.User
import com.leon.hobbyapp.viewmodel.UserViewModel
import org.json.JSONObject

class RegisterFragment : Fragment(), ButtonClickListener {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onButtonClick(v: View) {
        val username = binding.txtUsername.text.toString()
        val firstName = binding.txtFName.text.toString()
        val lastName = binding.txtLName.text.toString()
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPwd.text.toString()
        val confPwd = binding.txtPwd2.text.toString()

        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confPwd.isEmpty()) {
            Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show()
            return
        }
        if (password == confPwd) {
            val user = User(username, firstName, lastName, email, password)
            viewModel.register(user)
            viewModel.registerLD.observe(viewLifecycleOwner, Observer { success ->
                if (success) {
                    Toast.makeText(requireContext(), "Account Created!", Toast.LENGTH_SHORT).show()
                    val action = RegisterFragmentDirections.actionLoginFragment()
                    Navigation.findNavController(requireView()).navigate(action)
                } else {
                    Toast.makeText(requireContext(), "Account Creation Failed", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(requireContext(), "Password and confirm password do not match", Toast.LENGTH_SHORT).show()
        }
    }
}
