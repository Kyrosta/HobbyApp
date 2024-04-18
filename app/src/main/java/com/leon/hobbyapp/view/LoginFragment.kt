package com.leon.hobbyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.leon.hobbyapp.databinding.FragmentLoginBinding
import com.leon.hobbyapp.view.RegisterFragmentDirections.Companion.homeFragmentAction
import io.reactivex.rxjava3.core.Observer

class LoginFragment : Fragment() {

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

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPwd.text.toString()

            if (username.isEmpty() && password.isEmpty()) {
                Toast.makeText(this.context, "Field is empty", Toast.LENGTH_SHORT).show()
            } else{
                viewModel.signin(username, password)
                observeViewModel()
            }
        }

        binding.btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionRegisterFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
    fun observeViewModel(){
        viewModel.userLD.observe(viewLifecycleOwner, Observer {
            if (it != null){
                MainActivity.username = it
                val action = LoginFragmentDirections.homeFragmentAction()
                Navigation.findNavController(requireView()).navigate(action)
            }
            else{
                Toast.makeText(this.context, "User does not exist", Toast.LENGTH_SHORT).show()
            }
        })
    }
}