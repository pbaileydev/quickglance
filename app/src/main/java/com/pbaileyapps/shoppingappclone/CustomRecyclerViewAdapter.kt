package com.pbaileyapps.shoppingappclone

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.drawable.DrawableUtils
import com.pbaileyapps.shoppingappclone.data.Item

class CustomRecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomVH>() {
    var mList = emptyList<Item>()
    class CustomVH(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.caption)
        val imageView: ImageView = view.findViewById(R.id.image_view)
        val quantityView:TextView = view.findViewById(R.id.quantity)
        val skuTextView:TextView = view.findViewById(R.id.skuTextView)
        var cardView:CardView = view.findViewById(R.id.card_quantity)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVH {
        return CustomVH(LayoutInflater.from(parent.context).inflate(R.layout.horizontal_item,parent,false) )
    }

    override fun onBindViewHolder(holder: CustomVH, position: Int) {
        var text:String = mList.get(position).name
        var drawable:String? = mList.get(position).drawable
        var quantity:Int = mList.get(position).amount
        var needed:Int = mList.get(position).needed
        var sku:String? = mList.get(position).sku
        if(drawable != null) {

            holder.imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(context.contentResolver,Uri.parse(drawable)))

        }
        if(drawable == null){
            holder.imageView.setImageDrawable(AppCompatResources.getDrawable(context,R.drawable.ic_baseline_photo_24))
        }
        if(sku == null){
            holder.skuTextView.setText("SKU: Unavailable")
        }
        if(sku != null){
            var text = "SKU:"+sku
            holder.skuTextView.setText(text)
        }
        holder.textView.setText(text)
        if(quantity>=needed){
            holder.cardView.setCardBackgroundColor(Color.WHITE)
        }
        else{
            holder.cardView.setCardBackgroundColor(Color.WHITE)
        }
        holder.quantityView.setText(quantity.toString())
        holder.itemView.setOnClickListener {
            val intent = Intent(context,UpdateActivity::class.java)
            intent.putExtra("ItemId",mList.get(position))
            context.startActivity(intent) }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    fun setData(list:List<Item>){
        mList = list
        notifyDataSetChanged()
    }

}