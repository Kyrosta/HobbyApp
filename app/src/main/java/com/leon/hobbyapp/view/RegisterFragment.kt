package com.leon.hobbyapp.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.leon.hobbyapp.databinding.FragmentRegisterBinding
import org.json.JSONObject

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private var queue: RequestQueue? = null
    val TAG = "volleyTag"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val firstName = binding.txtFName.text.toString()
            val lastName = binding.txtLName.text.toString()
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPwd.text.toString()
            val confPwd = binding.txtPwd2.text.toString()

            AlertDialog.Builder(activity).apply {
                if (password == confPwd) {
                    setTitle("Confirmation")
                    setMessage("Do you want to register this account?")
                    setPositiveButton("Register") { dialog, which ->
                        register(it, username, firstName, lastName, email, password)
                    }
                    setNegativeButton("Cancel") { dialog, which ->
                        dialog.dismiss()
                    }
                } else {
                    setTitle("Information")
                    setMessage("Fail to register.")
                    setPositiveButton("OK") { dialog, which ->
                        dialog.dismiss()
                    }
                }
                create().show()
            }
        }
    }

    fun register(view: View, username: String, firstName: String, lastName: String, email: String, password: String){
        //Log.d("register", "registerVolley")

        queue = Volley.newRequestQueue(context)
        val url = "http://10.0.2.2/anmputs/register.php"

        val alert = AlertDialog.Builder(activity).setTitle("Information")

        val stringRequest = object: StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("checkSuccess", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    alert.setMessage("User registered successfully!")
                    alert.setPositiveButton("OK") { dialog, which ->
                        val action = RegisterFragmentDirections.actionLoginFragment()
                        Navigation.findNavController(view).navigate(action)
                    }
                } else {
                    alert.setMessage("Failed to register!.")
                    alert.setPositiveButton("OK", null)
                }
                alert.create().show()
            },
            {
                Log.e("checkFail", it.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["username"] = username
                params["firstName"] = firstName
                params["lastName"] = lastName
                params["email"] = email
                params["password"] = password
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}
