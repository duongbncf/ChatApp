package com.dungnd.mvvm.ui.detail.signup

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.dungnd.mvvm.R
import com.dungnd.mvvm.data.remote.model.User
import com.dungnd.mvvm.databinding.FragmentSignUpBinding
import com.dungnd.mvvm.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.util.*

class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbref: DatabaseReference
    private lateinit var mStorage: FirebaseStorage
    private lateinit var selectedImage: Uri
    private var fullName = ""
    private var image = ""
    private var gmail = ""
    private var password = ""
    private var passwordAgain = ""

    private lateinit var dialog: AlertDialog
    override fun layoutRes(): Int {
        return R.layout.fragment_sign_up
    }

    override fun viewModelClass(): Class<SignUpViewModel> = SignUpViewModel::class.java
    override fun initView() {
        mAuth = FirebaseAuth.getInstance()
        mStorage = FirebaseStorage.getInstance();
        binding.btnSignUp.setOnClickListener {
            if (binding.btnSignUp?.isEnabled == true) {
                val name = binding.edtName.text.toString()
                val email = binding.edtGmail.text.toString()
                val password = binding.edtPassword.text.toString()
                binding.progressLogin.visibility = View.VISIBLE
                binding.btnSignUp.visibility = View.GONE
                signUp(name, email, password)
            }
        }
        binding.imagView.setOnClickListener {
            image = it.toString()

            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 45)
            binding.btnSignUp.isEnabled =
                fullName.isNotEmpty() && gmail.isNotEmpty() && password.isNotEmpty() && passwordAgain.isNotEmpty() && image.isNotEmpty()
//                uploadData()
        }

        binding.tvBack.setOnClickListener {
            findNavController().navigate(
                R.id.action_signUpFragment_to_loginFragment
            )
        }
        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(
                R.id.action_signUpFragment_to_loginFragment
            )
        }
        //

        binding.btnSignUp.isEnabled = false
        binding.edtName.addTextChangedListener {
            fullName = it.toString()
            binding.btnSignUp.isEnabled =
                fullName.isNotEmpty() && gmail.isNotEmpty() && password.isNotEmpty() && passwordAgain.isNotEmpty() && image.isNotEmpty()
        }
        binding.edtGmail.addTextChangedListener {
            gmail = it.toString()
            binding.btnSignUp.isEnabled =
                fullName.isNotEmpty() && gmail.isNotEmpty() && password.isNotEmpty() && passwordAgain.isNotEmpty() && image.isNotEmpty()
            if (Patterns.EMAIL_ADDRESS.matcher(gmail).matches()) {
                binding.edtGmail.error = null
            } else {
                binding.edtGmail.error = "Email của bạn không đúng. Vui lòng nhập lại!"
            }
        }
        binding.edtPassword.addTextChangedListener {
            password = it.toString()
            binding.btnSignUp.isEnabled =
                fullName.isNotEmpty() && gmail.isNotEmpty() && password.isNotEmpty() && passwordAgain.isNotEmpty()&& image.isNotEmpty()
            if (it!!.length < 8) {
                binding.edtPassword.setError("Mật khẩu tối thiểu 8 ký tự");
            } else {
                binding.edtPassword.setError(null)
            }
        }
        binding.edtPasswordAgain.addTextChangedListener {

            passwordAgain = it.toString()
            binding.btnSignUp.isEnabled =
                fullName.isNotEmpty() && gmail.isNotEmpty() && password.isNotEmpty() && passwordAgain.isNotEmpty()&& image.isNotEmpty()

            if (passwordAgain.length < 8) {
                binding.edtPasswordAgain.setError("Mật khẩu tối thiểu 8 ký tự");
            } else {
                binding.edtPasswordAgain.setError(null)
            }
            if (password == passwordAgain) {
                binding.edtPasswordAgain.setError(null)
            } else {
                binding.edtPasswordAgain.setError("Mật khẩu không trùng khớp")
            }
        }
    }

    fun callApiSignUp(name: String, email: String, password: String, profileImage: String) {
        activity?.let {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        AddUser(name, email, mAuth.currentUser?.uid!!, profileImage)
                        findNavController().navigate(
                            R.id.action_signUpFragment_to_loginFragment
                        )
//                        activity?.finish()
                    } else {
                        binding.progressLogin.visibility = View.GONE
                        binding.btnSignUp.visibility = View.VISIBLE
                        Toast.makeText(context, task.exception.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

    fun updateFileToFirebase(name: String, email: String, password: String) {
        val storageRef = mStorage.reference
        val riversRef = storageRef.child("images/${selectedImage.lastPathSegment}")
        var uploadTask = riversRef.putFile(selectedImage)
        uploadTask.addOnFailureListener {
            print(it.message.toString())
        }.addOnSuccessListener { taskSnapshot ->
            taskSnapshot!!.storage.downloadUrl.addOnSuccessListener { task ->
                var imageUrl = task.toString()
                callApiSignUp(name, email, password, imageUrl)
            }
            taskSnapshot!!.storage.downloadUrl.addOnFailureListener { task ->
            }
        }
    }

    private fun signUp(name: String, email: String, password: String) {
        updateFileToFirebase(name, email, password)
    }

    private fun AddUser(name: String, email: String, uid: String, profileImage: String) {
        mDbref = FirebaseDatabase.getInstance().getReference()
        mDbref.child("user").child(uid).setValue(User(name, email, uid, profileImage))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (data.data != null) {
                selectedImage = data.data!!
                binding.imagView.setImageURI(selectedImage)
            }
        }
    }
}