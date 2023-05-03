package com.app.geokaab.ui.type_experiences

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.geokaab.data.model.Experience
import com.app.geokaab.databinding.ItemExperienceCardBinding
import com.squareup.picasso.Picasso
import java.util.function.Predicate
import java.util.stream.Collectors

class ExperienceListingAdapter(
    val onItemClicked: (Int, Experience) -> Unit
) : RecyclerView.Adapter<ExperienceListingAdapter.MyViewHolder>() {
    private var listOriginal: MutableList<Experience> = arrayListOf()
    private var list: ArrayList<Experience> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemExperienceCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        
    }
    fun createList(list: MutableList<Experience>, idTypeExperience : String){
        this.listOriginal.clear()
        listOriginal = list
        filterByTypeCode(idTypeExperience)
        //updateList(listOriginal)
    }

    fun updateList(list: ArrayList<Experience>){
        this.list = list
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        listOriginal.removeAt(position)
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

    fun filterByTypeCode(idType: String){
        this.list.clear()
        //listOriginal = list

        listOriginal.forEachIndexed{
            index, parameters ->
            if (parameters.type[0] == idType){
                list.add(parameters)
                println("######Hola########" + parameters.title)
            }

        }

        updateList(list)


    }



}

