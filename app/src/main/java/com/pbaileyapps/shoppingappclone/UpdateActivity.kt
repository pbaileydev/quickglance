package com.pbaileyapps.shoppingappclone

import android.app.Activity
import android.content.ContentResolver
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.zxing.integration.android.IntentIntegrator
import com.pbaileyapps.shoppingappclone.data.Item
import com.pbaileyapps.shoppingappclone.data.ItemViewModel

class UpdateActivity : AppCompatActivity() {
    private lateinit var itemViewModel: ItemViewModel
    private var imageUri: Uri? = null
    private var sku: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolBar.findViewById<TextView>(R.id.toolbar_name).setText("Update")
        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        val name: EditText = findViewById(R.id.update_item_text)
        val needed: EditText = findViewById(R.id.update_needed_text)
        val quantity: EditText = findViewById(R.id.update_quantity_text)
        var item: Item? = intent.getParcelableExtra<Item>("ItemId")
        if (item != null) {
            name.setText(item.name)
            quantity.setText(item.amount.toString())
            needed.setText(item.needed.toString())
            if (item.drawable != null) {
                imageUri = Uri.parse(item.drawable)
                findViewById<ImageView>(R.id.update_image).setImageBitmap(
                    MediaStore.Images.Media.getBitmap(
                        this.contentResolver, Uri.parse(item.drawable)
                    )
                )
            }
            findViewById<ImageView>(R.id.update_image).setOnClickListener(View.OnClickListener {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(intent, 100)
            })
            findViewById<ImageButton>(R.id.update_scanButton).setOnClickListener({
                val integrator = IntentIntegrator(this);
                integrator.initiateScan();
            })
            findViewById<ImageView>(R.id.update_delete).setOnClickListener({
                AlertDialog.Builder(this)
                    .setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.cancel()
                }).setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->
                    itemViewModel.deleteItem(item)
                    startActivity(Intent(applicationContext,MainActivity::class.java))
                }).setMessage("Are you sure you want to delete this item?").create().show()

            })
            findViewById<Button>(R.id.update_button).setOnClickListener {
                if (!TextUtils.isEmpty(name.text) && !TextUtils.isEmpty(needed.text) && !TextUtils.isEmpty(
                        quantity.text
                    )
                ) {
                    if (imageUri != null && sku != null) {
                        val itemUpdate: Item = Item(
                            item.id, name.text.toString(), imageUri.toString(),
                            Integer.parseInt(quantity.text.toString()),
                            Integer.parseInt(needed.text.toString()), sku
                        )
                        itemViewModel.updateItem(itemUpdate)

                    } else if (imageUri == null && sku != null) {
                        val itemUpdate: Item = Item(
                            item.id, name.text.toString(), null,
                            Integer.parseInt(quantity.text.toString()),
                            Integer.parseInt(needed.text.toString()), sku
                        )
                        itemViewModel.updateItem(itemUpdate)
                        startActivity(Intent(this, MainActivity::class.java))
                    } else if (imageUri != null && sku == null) {
                        val itemUpdate: Item = Item(
                            item.id, name.text.toString(), imageUri.toString(),
                            Integer.parseInt(quantity.text.toString()),
                            Integer.parseInt(needed.text.toString()), null
                        )
                        itemViewModel.updateItem(itemUpdate)
                        startActivity(Intent(this, MainActivity::class.java))

                    } else {
                        val itemUpdate: Item = Item(
                            item.id, name.text.toString(), null,
                            Integer.parseInt(quantity.text.toString()),
                            Integer.parseInt(needed.text.toString()), null
                        )
                        itemViewModel.updateItem(itemUpdate)
                        startActivity(Intent(this, MainActivity::class.java))

                    }
                }
            }
        }
    }
                override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                    super.onActivityResult(requestCode, resultCode, data)
                    if (resultCode == Activity.RESULT_OK && requestCode == 100) {
                        imageUri = data?.data!!
                        findViewById<ImageView>(R.id.update_image)?.setImageURI(imageUri)
                    }
                    var scanResult =
                        IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                    if (scanResult != null) {
                        sku = scanResult.contents.toString()
                        findViewById<EditText>(R.id.update_sku)?.setText(sku)
                        // handle scan result
                    }
                    // else continue with any other code you need in the method
                }

}