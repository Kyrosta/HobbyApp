package com.leon.hobbyapp.view

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
import com.leon.hobbyapp.databinding.FragmentProfilBinding
import com.leon.hobbyapp.model.User
import com.leon.hobbyapp.viewmodel.UserViewModel

class ProfilFragment : Fragment() {
    private lateinit var binding: FragmentProfilBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        viewModel.userLD.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                binding.txtChangeFName.setText(user.firstName)
                binding.txtChangeLName.setText(user.lastName)

                binding.btnUpdate.setOnClickListener {
                    val firstName = binding.txtChangeFName.text.toString()
                    val lastName = binding.txtChangeLName.text.toString()
                    val newPassword = binding.txtChangePassword.text.toString()

                    viewModel.update(firstName, lastName, newPassword)
                }
            }
        })

        binding.btnLogout.setOnClickListener {
            MainActivity.user = null
            Navigation.findNavController(it).navigateUp()
        }
    }

    fun observeViewModel() {
        viewModel.successLD.observe(viewLifecycleOwner, Observer { success ->
            if (success != false) {
                Toast.makeText(requireContext(), "Profile updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Update failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}