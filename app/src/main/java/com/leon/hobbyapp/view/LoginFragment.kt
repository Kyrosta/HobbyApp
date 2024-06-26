package com.leon.hobbyapp.view

import android.content.Context
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
        val username = binding.txtUsername.text.toString()
        val password = binding.txtPwd.text.toString()
        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(context,"Fields cannot be empty!", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.login(username, password)
            viewModel.loginLD.observe(viewLifecycleOwner, Observer { isLogin ->
                if (isLogin!!){
                    val sharedPref = requireActivity().getSharedPreferences("onAccount", Context.MODE_PRIVATE)
                    var editor = sharedPref.edit()
                    viewModel.userLD.value?.let { logedin->
                        editor.putString("id", logedin.uuid.toString())
                        editor.putString("username", logedin.username)
                        editor.putString("firstName", logedin.firstName)
                        editor.putString("lastName", logedin.lastName)
                        editor.putString("email", logedin.email)
                        editor.putString("password", logedin.password)
                        editor.apply()
                    }
                    val action = LoginFragmentDirections.actionHomeFragment()
                    Navigation.findNavController(requireView()).navigate(action)
                    Toast.makeText(requireContext(), "Login Success!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onButtonActionNavClick(v: View) {
        val action = LoginFragmentDirections.actionRegisterFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }
}