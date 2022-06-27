package com.pbaileyapps.quickglanceinventory

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var signIn:Button
    private lateinit var signUp:Button
    private lateinit var skip:Button
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val sharedPref = getSharedPreferences(
            getString(R.string.prefs), Context.MODE_PRIVATE)
        val save = sharedPref.getBoolean("SAVE",false)
        val prefsEmail = sharedPref.getString("EMAIL","email")
        val prefsPassword = sharedPref.getString("PASSWORD","password")
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        checkBox = findViewById(R.id.checkBox)
        signIn = findViewById(R.id.Login)
        signUp = findViewById(R.id.signup)
        skip = findViewById(R.id.skipAuth)
        firebaseAuth = FirebaseAuth.getInstance()
        if(save == true){
            emailEditText.setText(prefsEmail)
            passwordEditText.setText(prefsPassword)
            checkBox.isChecked = true
        }
        var intent: Intent = Intent(this,MainActivity::class.java)
        var intentSignUp: Intent = Intent(this,OnboardingActivity::class.java)
        signIn.setOnClickListener {
            if(!emailEditText.text.isEmpty() && !passwordEditText.text.isEmpty()){

                firebaseAuth.signInWithEmailAndPassword(emailEditText.text.toString(),passwordEditText.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            var editor = sharedPref.edit()
                            if(checkBox.isChecked){
                                if(!prefsEmail.equals(emailEditText.text.toString())){
                                    AlertDialog.Builder(this).setMessage("Would you like to override the your login credentials?").setPositiveButton("Yes",DialogInterface.OnClickListener{
                                        dialog,id->editor.putString("EMAIL",emailEditText.text.toString())
                                        editor.putString("PASSWORD",passwordEditText.text.toString())
                                        editor.putBoolean("SAVE",true)
                                        editor.commit()
                                        startActivity(intent)
                                    }
                                    ).setNegativeButton("No",DialogInterface.OnClickListener{
                                            dialog,id->editor.putString("EMAIL",prefsEmail)
                                        editor.putString("PASSWORD",prefsPassword)
                                        editor.putBoolean("SAVE",true)
                                        editor.commit()
                                        startActivity(intent)
                                    }).show()
                                }
                                else {
                                    editor.putString("EMAIL", emailEditText.text.toString())
                                    editor.putString("PASSWORD", passwordEditText.text.toString())
                                    editor.putBoolean("SAVE", true)
                                    intent.putExtra("EMAIL", emailEditText.text.toString())
                                    editor.commit()
                                    startActivity(intent)
                                }
                            }
                            else{
                                editor.putBoolean("SAVE",false)
                                editor.commit()
                                startActivity(intent)
                            }


                        }
                        else{
                            Toast.makeText(this,"Failed to login",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        signUp.setOnClickListener {
            startActivity(intentSignUp)

        }
        skip.setOnClickListener {
            startActivity(intent)

        }
    }
}