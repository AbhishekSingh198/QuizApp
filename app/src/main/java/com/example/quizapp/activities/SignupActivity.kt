package com.example.quizapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quizapp.R
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.btnSignup
import kotlinx.android.synthetic.main.activity_signup.etEmailAddress
import kotlinx.android.synthetic.main.activity_signup.etPassword

class SignupActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        firebaseAuth= FirebaseAuth.getInstance()
        btnSignup.setOnClickListener {
            signUpUser()
        }
        btnLogin.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun signUpUser(){
        val email = etEmailAddress.text.toString()
        val password=etPassword.text.toString()
        val confirmPassword=etConfirmPassword.text.toString()
        if(email.isBlank() || password.isBlank()|| confirmPassword.isBlank()){
            Toast.makeText(this,"Email and Password cannot be empty",Toast.LENGTH_SHORT).show()
            return
        }
        if(password!=confirmPassword){
            Toast.makeText(this,"Password and Confirm Password do not match",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this,"Sign-Up Successful",Toast.LENGTH_SHORT).show()
                    val intent= Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this,"Error creating User",Toast.LENGTH_SHORT).show()
                }
            }

    }
}