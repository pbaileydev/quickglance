package com.pbaileyapps.quickglanceinventory

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.integration.android.IntentIntegrator
import com.pbaileyapps.quickglanceinventory.data.Item
import com.pbaileyapps.quickglanceinventory.data.ItemViewModel
import java.util.jar.Manifest

class AddItem : AppCompatActivity() {
    private lateinit var itemViewModel: ItemViewModel
    private var imageUri: Uri? = null
    private var sku:String? = null
    private var skuText: EditText? = null
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolBar.findViewById<TextView>(R.id.toolbar_name).setText("Add")
        auth = FirebaseAuth.getInstance()
        var uid:String? = auth.currentUser?.uid
        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        val name:EditText = findViewById(R.id.add_item_text)
        val needed:EditText = findViewById(R.id.add_needed_text)
        val quantity:EditText = findViewById(R.id.add_quantity_text)
        skuText = findViewById(R.id.sku)
        findViewById<ImageView>(R.id.add_image).setOnClickListener(View.OnClickListener {
            if(ContextCompat.checkSelfPermission(applicationContext,android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(intent,100)
            }
            else{
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }

        })
        findViewById<ImageButton>(R.id.scanButton).setOnClickListener({
            val integrator = IntentIntegrator(this)
            integrator.initiateScan()
        })
        findViewById<Button>(R.id.add_button).setOnClickListener {
            sku = skuText?.text.toString()
            if(TextUtils.isEmpty(name.text) || TextUtils.isEmpty(quantity.text) || TextUtils.isEmpty(needed.text)){
                findViewById<TextInputLayout>(R.id.add_needed_text).setError("Please, fill this field")
                findViewById<TextInputLayout>(R.id.text_input_quantity_layout).setError("Please, fill this field")
                findViewById<TextInputLayout>(R.id.add_item_input_layout).setError("Please, fill this field")
            }
            if(!TextUtils.isEmpty(name.text) && !TextUtils.isEmpty(needed.text) && !TextUtils.isEmpty(quantity.text)) {
                if(name.text.length > 10){
                    Snackbar.make(it,"Cannot be more than 10 characters",Snackbar.LENGTH_SHORT).show()

                }
                else if(imageUri != null && !TextUtils.isEmpty(sku)) {
                    val item: Item = Item(
                        0, name.text.toString(), imageUri.toString(),
                        Integer.parseInt(findViewById<EditText>(R.id.add_quantity_text).text.toString()),
                        Integer.parseInt(needed.text.toString()),sku,uid
                    )
                    itemViewModel.addItem(item)
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
                else if (imageUri == null && sku != null){
                    if(!sku!!.isDigitsOnly()){
                        Snackbar.make(it,"SKU can only contain numbers",Snackbar.LENGTH_SHORT).show()
                    }
                    else {
                        val item: Item = Item(
                            0, name.text.toString(), null,
                            Integer.parseInt(findViewById<EditText>(R.id.add_quantity_text).text.toString()),
                            Integer.parseInt(needed.text.toString()), sku,uid
                        )
                        itemViewModel.addItem(item)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
                else if (imageUri != null && sku == null){
                    val item: Item = Item(
                        0, name.text.toString(), imageUri.toString(),
                        Integer.parseInt(findViewById<EditText>(R.id.add_quantity_text).text.toString()),
                        Integer.parseInt(needed.text.toString()),null,uid
                    )
                    itemViewModel.addItem(item)
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
                else{
                    val item: Item = Item(
                        0, name.text.toString(), null,
                        Integer.parseInt(findViewById<EditText>(R.id.add_quantity_text).text.toString()),
                        Integer.parseInt(needed.text.toString()),null,uid
                    )
                    itemViewModel.addItem(item)
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100) {
            if (resultCode == Activity.RESULT_OK ) {
                imageUri = data?.data!!
                findViewById<ImageView>(R.id.add_image)?.setImageURI(imageUri)
            }
            return
        }

        var scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Log.d("SKURESULTS",scanResult.toString())
        if (scanResult != null) {
            if(scanResult.contents == null){
                Toast.makeText(applicationContext,"Failed to scan",Toast.LENGTH_SHORT).show()
            }
            else{
                sku = scanResult.contents.toString()
                skuText?.setText(sku)
                // handle scan result
            }}
        // else continue with any other code you need in the method
    }



}