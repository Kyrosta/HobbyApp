package com.leon.hobbyapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.leon.hobbyapp.R
import com.leon.hobbyapp.databinding.FragmentProfilBinding
import com.leon.hobbyapp.model.User
import com.leon.hobbyapp.viewmodel.UserViewModel

class ProfilFragment : Fragment(), ButtonActionNav, ButtonClickListener {
    private lateinit var binding: FragmentProfilBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this
        binding.nav = this

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onButtonActionNavClick(v: View) {
        val sharedPref = requireActivity().getSharedPreferences("account", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()

        val action = ProfilFragmentDirections.actionLogoutFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    override fun onButtonClick(v: View) {
        val sharedPref = requireActivity().getSharedPreferences("onAccount", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username","")
        binding.txtShowUsername.setText(username)
        val email = sharedPref.getString("email","")
        binding.txtShowEmail.setText(email)
        val newPassword = binding.txtChangePassword.text.toString()

        if (newPassword.isNotEmpty()) {
            viewModel.update(newPassword, id)
            viewModel.updateLD.observe(viewLifecycleOwner, Observer { success ->
                if (success) {
                    Toast.makeText(
                        requireContext(),"Password successfully changed",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Password update failed", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(requireContext(), "All fields must not be empty", Toast.LENGTH_SHORT).show()
        }
    }
}