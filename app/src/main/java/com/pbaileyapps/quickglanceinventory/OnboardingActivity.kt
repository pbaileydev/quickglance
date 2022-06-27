package com.pbaileyapps.quickglanceinventory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class OnboardingActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var signUp: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

// ...


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        firstNameEditText = findViewById(R.id.firstname)
        lastNameEditText = findViewById(R.id.lastname)
        signUp = findViewById(R.id.signup)
        firebaseAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        var intent: Intent = Intent(this,MainActivity::class.java)
        signUp.setOnClickListener {
            if(!emailEditText.text.isEmpty() && !passwordEditText.text.isEmpty() && !firstNameEditText.text.isEmpty()
                && !lastNameEditText.text.isEmpty()){
                    var email = emailEditText.text.toString()
                    var password = passwordEditText.text.toString()
                    var firstName = firstNameEditText.text.toString()
                    var lastName = lastNameEditText.text.toString()
                firebaseAuth.createUserWithEmailAndPassword(emailEditText.text.toString(),passwordEditText.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            database.child("users").child(firebaseAuth.currentUser?.uid.toString())
                                .child("Email").setValue(email)
                            database.child("users").child(firebaseAuth.currentUser?.uid.toString())
                                .child("First Name").setValue(firstName)
                            database.child("users").child(firebaseAuth.currentUser?.uid.toString())
                                .child("Last Name").setValue(lastName)
                            intent.putExtra("EMAIL",emailEditText.text.toString())
                            intent.putExtra("NEW",true)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this,"Failed to add new account", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}