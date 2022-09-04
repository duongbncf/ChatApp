package com.dungnd.mvvm.ui.detail.profile

import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.dungnd.mvvm.R
import com.dungnd.mvvm.databinding.FragmentProfileBinding
import com.dungnd.mvvm.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var imageUri: Uri
    private lateinit var mStorage: FirebaseStorage
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var selectedImage: Uri
//    private var fullName = ""
//    private var image = ""


    override fun layoutRes(): Int {
        return R.layout.fragment_profile
    }

    override fun viewModelClass(): Class<ProfileViewModel> = ProfileViewModel::class.java

    override fun initView() {

        mAuth = FirebaseAuth.getInstance()
        mStorage = FirebaseStorage.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().reference
        val user = mAuth.currentUser

        mDatabaseReference.child("user").child(mAuth!!.currentUser!!.uid).child("name").get()
            .addOnSuccessListener {
                val editable: Editable = SpannableStringBuilder(it.value.toString())
                binding.edtName.text = editable
            }

        mDatabaseReference.child("user").child(mAuth!!.currentUser!!.uid).child("profileImage")
            .get().addOnSuccessListener {
                //val editable: Editable = SpannableStringBuilder(it.value.toString())
                Picasso.get().load(it.value.toString()).fit().into(binding.imagView)
            }
        // binding.imagView.setImageURI(user?.photoUrl)
        menu()


        //
//        binding.btnSignUp.isEnabled = false
        binding.imagView.setOnClickListener {
//            image = it.toString()
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 45)
//                fullName = it.toString()
//                binding.btnSignUp.isEnabled = fullName.isNotEmpty() && image.isNotEmpty()
        }
//        binding.edtName.addTextChangedListener{
//            fullName = it.toString()
//            binding.btnSignUp.isEnabled = fullName.isNotEmpty() && image.isNotEmpty()
//        }
        binding.btnSave.setOnClickListener {
            binding.btnSave.visibility = View.GONE
            binding.progressLogin.visibility = View.VISIBLE
            val username = binding.edtName.text.toString()
            if (selectedImage != Uri.EMPTY) {
                updateFileToFirebase()
                updateName(username)
            } else
                updateName(username)
        }
        selectedImage = Uri.EMPTY
    }

    private fun updateImage(profileImage: String) {
        mDatabaseReference.child("user").child(mAuth!!.currentUser!!.uid).child("profileImage")
            .setValue(profileImage).addOnSuccessListener {

                mDatabaseReference.child("user").child(mAuth!!.currentUser!!.uid).child("image")
                    .get()
                    .addOnSuccessListener {
                        binding.progressLogin.visibility = View.GONE
                        binding.btnSave.visibility = View.VISIBLE
                        selectedImage = Uri.EMPTY
                        Toast.makeText(context, "Update Successfuly", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        binding.progressLogin.visibility = View.GONE
                        binding.btnSave.visibility = View.VISIBLE

                        Toast.makeText(context,"fail",Toast.LENGTH_SHORT).show()
                    }
            }
    }

    private fun updateName(username: String) {
        mDatabaseReference.child("user").child(mAuth!!.currentUser!!.uid).child("name")
            .setValue(username).addOnSuccessListener {
                mDatabaseReference.child("user").child(mAuth!!.currentUser!!.uid).child("name")
                    .get()
                    .addOnSuccessListener {
//                    Toast.makeText(context,"Update Successfuly",Toast.LENGTH_SHORT).show()
                        val editable: Editable = SpannableStringBuilder(it.value.toString())
                        binding.edtName.text = editable
                        if(selectedImage == Uri.EMPTY){
                            binding.progressLogin.visibility = View.GONE
                            binding.btnSave.visibility = View.VISIBLE
                            Toast.makeText(context, "Update Successfuly", Toast.LENGTH_SHORT).show()
                        }

                    }.addOnFailureListener {
                        Toast.makeText(context,"fail",Toast.LENGTH_SHORT).show()
                        binding.progressLogin.visibility = View.GONE
                        binding.btnSave.visibility = View.VISIBLE

                    }
            }

    }


    private fun menu() {
        binding.menuMessage.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_messageFragment
            )
        }
        binding.menuPeople.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_userFragment
            )
        }
        binding.vback2.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_loginFragment
            )
        }
    }

    fun updateFileToFirebase() {
        val storageRef = mStorage.reference
        val riversRef = storageRef.child("images/${selectedImage.lastPathSegment}")
        var uploadTask = riversRef.putFile(selectedImage)
        uploadTask.addOnFailureListener {
            print(it.message.toString())
        }.addOnSuccessListener { taskSnapshot ->
            taskSnapshot!!.storage.downloadUrl.addOnSuccessListener { task ->
                var imageUrl = task.toString()
                updateImage(imageUrl)
            }
            taskSnapshot!!.storage.downloadUrl.addOnFailureListener { task ->
            }
        }
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