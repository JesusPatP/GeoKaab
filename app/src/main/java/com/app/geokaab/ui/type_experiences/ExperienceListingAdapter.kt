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
import com.app.geokaab.databinding.ItemExperienceCardBinding
import com.app.geokaab.databinding.ItemTypeExperienceBinding
import com.squareup.picasso.Picasso

class ExperienceListingAdapter(
    val onItemClicked: (Int, Experience) -> Unit
) : RecyclerView.Adapter<ExperienceListingAdapter.MyViewHolder>() {

    private var list: MutableList<Experience> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemExperienceCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        
    }

    fun updateList(list: MutableList<Experience>){
        this.list = list
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        list.removeAt(position)
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemExperienceCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Experience){
            binding.title.setText(item.title)
            binding.type.setText(item.type[1])
            //binding.description.setText(item.description)
            binding.image.scaleType = ImageView.ScaleType.FIT_XY
            Picasso.get().load(item.images[0]).into(binding.image)
            binding.description.apply {
                if (item.description.length > 120){
                    text = "${item.description.substring(0,120)}..."
                }else{
                    text = item.description
                }
            }

            //binding.image.setImageURI(imageUris)

            binding.experienceCard.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }
        }
    }

}

