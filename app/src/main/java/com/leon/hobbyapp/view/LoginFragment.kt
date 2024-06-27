package com.leon.hobbyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.leon.hobbyapp.databinding.FragmentLoginBinding
import com.leon.hobbyapp.view.RegisterFragmentDirections.Companion.homeFragmentAction
import com.leon.hobbyapp.viewmodel.UserViewModel

class LoginFragment : Fragment(), ButtonActionNav, ButtonClickListener {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listener = this
        binding.nav = this

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onButtonClick(v: View) {
        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString().trim()
            val password = binding.txtPwd.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.signin(username, password)

                viewModel.userLD.observe(viewLifecycleOwner, Observer { success ->
                    if (success) {
                        Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    override fun onButtonActionNavClick(v: View) {
        val action = LoginFragmentDirections.actionHomeFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }
}