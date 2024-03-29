package com.app.geokaab.ui.type_experiences

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.app.geokaab.R
import com.app.geokaab.data.model.Experience
import com.app.geokaab.data.model.TypeExperience
import com.app.geokaab.databinding.ItemTypeExperienceBinding
import com.squareup.picasso.Picasso

class TypeExperienceListingAdapter(
    val onItemClicked: (Int, TypeExperience) -> Unit
) : RecyclerView.Adapter<TypeExperienceListingAdapter.MyViewHolder>() {

    private var list: MutableList<TypeExperience> = arrayListOf()
    private var listOriginal: MutableList<TypeExperience> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemTypeExperienceBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<TypeExperience>){
        this.list = list
        notifyDataSetChanged()
    }

    fun onCreateList(listOn: MutableList<TypeExperience>) : String{

        this.listOriginal.clear()
        listOriginal = listOn

        var status = "cW8j8mHtofEvflsOqVM0";

        for ((index,item) in listOn.withIndex()){
            if (item.status != 0){
                status = item.id
                break
            }
        }
        filter()
        return status.toString()
    }

    fun filter(){
        this.list.clear()
        //listOriginal = list

        listOriginal.forEachIndexed{
                index, parameters ->
            if (parameters.status != 0){
                list.add(parameters)
            }

        }
        updateList(list)
    }

    fun removeItem(position: Int){
        list.removeAt(position)
        notifyItemChanged(position)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemTypeExperienceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TypeExperience){
            binding.title.setText(item.name)
            binding.image.scaleType = ImageView.ScaleType.FIT_XY
            Picasso.get().load(item.image).into(binding.image)

            //binding.image.setImageURI(imageUris)

            binding.itemExperienceTypeCard.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }
        }
    }

}

