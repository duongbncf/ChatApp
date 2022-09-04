package com.dungnd.mvvm.ui.login

import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.dungnd.mvvm.R
import com.dungnd.mvvm.databinding.FragmentLoginBinding
import com.dungnd.mvvm.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    private lateinit var mAuth: FirebaseAuth
    private var gmail = ""
    private var password = ""
    override fun layoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun viewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun initView() {
        mAuth = FirebaseAuth.getInstance()
        binding.btnSignUp.setOnClickListener {

            findNavController().navigate(
                R.id.action_loginFragment_to_signUpFragment
            )

        }
        binding.btnLogin.isEnabled = true
        binding.btnLogin.setOnClickListener {
            if (binding.btnLogin.isEnabled == true) {
                binding.progressLogin.visibility = View.VISIBLE
                binding.btnLogin.visibility = View.GONE
                val email = binding.edtGmail.text.toString()
                val password = binding.edtPassword.text.toString()
                login(email, password)
            }
        }

        binding.edtGmail.addTextChangedListener {
            gmail = it.toString()
            binding.btnLogin?.isEnabled = gmail.isNotEmpty() && password.isNotEmpty()
            if(Patterns.EMAIL_ADDRESS.matcher(gmail).matches()){
                binding.edtGmail.error = null
            }else
                binding.edtGmail.error = "Email của bạn không đúng. Vui lòng nhập lại"
        }
        binding.edtPassword.addTextChangedListener {
            password = it.toString()
            binding.btnLogin?.isEnabled = gmail.isNotEmpty() && password.isNotEmpty()
            if(it!!.length > 8){
                binding.edtPassword.setError(null)
            }else{
                binding.edtPassword.setError("Mật khẩu tối thiểu 8 ký tự")
            }
        }


    }

    private fun login(email: String, password: String) {
        activity?.let {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->

                    if (task.isSuccessful) {
                        findNavController().navigate(
                            R.id.action_loginFragment_to_messageFragment
                        )
                    } else {
                        binding.progressLogin.visibility = View.GONE
                        binding.btnLogin.visibility = View.VISIBLE
                        Toast.makeText(context, "Username is required", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}