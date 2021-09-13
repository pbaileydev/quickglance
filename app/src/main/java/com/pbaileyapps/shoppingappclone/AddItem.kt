package com.pbaileyapps.shoppingappclone

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.zxing.integration.android.IntentIntegrator
import com.pbaileyapps.shoppingappclone.data.Item
import com.pbaileyapps.shoppingappclone.data.ItemViewModel

class AddItem : AppCompatActivity() {
    private lateinit var itemViewModel: ItemViewModel
    private var imageUri: Uri? = null
    private var sku:String? = null
    private var skuText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolBar.findViewById<TextView>(R.id.toolbar_name).setText("Add")
        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        val name:EditText = findViewById(R.id.add_item_text)
        val needed:EditText = findViewById(R.id.add_needed_text)
        val quantity:EditText = findViewById(R.id.add_quantity_text)
        skuText = findViewById(R.id.sku)
        findViewById<ImageView>(R.id.add_image).setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(intent,100)
        })
        findViewById<ImageButton>(R.id.scanButton).setOnClickListener({
            val integrator = IntentIntegrator(this)
            integrator.initiateScan()
        })
        findViewById<Button>(R.id.add_button).setOnClickListener {
            if(!TextUtils.isEmpty(name.text) && !TextUtils.isEmpty(needed.text) && !TextUtils.isEmpty(quantity.text)) {
                if(imageUri != null && sku != null) {
                    val item: Item = Item(
                        0, name.text.toString(), imageUri.toString(),
                        Integer.parseInt(findViewById<EditText>(R.id.add_quantity_text).text.toString()),
                        Integer.parseInt(needed.text.toString()),sku
                    )
                    itemViewModel.addItem(item)
                    startActivity(Intent(this,MainActivity::class.java))
                }
                else if (imageUri == null && sku != null){
                    val item: Item = Item(
                        0, name.text.toString(), null,
                        Integer.parseInt(findViewById<EditText>(R.id.add_quantity_text).text.toString()),
                        Integer.parseInt(needed.text.toString()),sku
                    )
                    itemViewModel.addItem(item)
                    startActivity(Intent(this,MainActivity::class.java))
                }
                else if (imageUri != null && sku == null){
                    val item: Item = Item(
                        0, name.text.toString(), imageUri.toString(),
                        Integer.parseInt(findViewById<EditText>(R.id.add_quantity_text).text.toString()),
                        Integer.parseInt(needed.text.toString()),null
                    )
                    itemViewModel.addItem(item)
                    startActivity(Intent(this,MainActivity::class.java))
                }
                else{
                    val item: Item = Item(
                        0, name.text.toString(), null,
                        Integer.parseInt(findViewById<EditText>(R.id.add_quantity_text).text.toString()),
                        Integer.parseInt(needed.text.toString()),null
                    )
                    itemViewModel.addItem(item)
                    startActivity(Intent(this,MainActivity::class.java))
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100) {
            if (resultCode == Activity.RESULT_OK ) {
                imageUri = data?.data!!
                Toast.makeText(this, imageUri.toString(), Toast.LENGTH_LONG).show()
                findViewById<ImageView>(R.id.add_image)?.setImageURI(imageUri)
            }
            return
        }

        var scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Log.d("SKURESULTS",scanResult.toString())
        if (scanResult != null) {
            if(scanResult.contents == null){
                Toast.makeText(this,"Cancelled", Toast.LENGTH_LONG).show()
            }
            else{
                sku = scanResult.contents.toString()
                skuText?.setText(sku)

                Toast.makeText(this,"Success", Toast.LENGTH_LONG).show()

                // handle scan result
            }}
        // else continue with any other code you need in the method
    }



}