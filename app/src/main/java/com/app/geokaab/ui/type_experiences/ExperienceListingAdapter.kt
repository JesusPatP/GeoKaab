package com.app.geokaab.ui.type_experiences

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.geokaab.data.model.Experience
import com.app.geokaab.databinding.ItemExperienceCardBinding
import com.app.geokaab.util.hide
import com.app.geokaab.util.show
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import kotlinx.coroutines.flow.callbackFlow


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
            Picasso.get().load(item.images[0]).into(binding.image, object : com.squareup.picasso.Callback{
                override fun onSuccess() {
                    //set animations here
                    binding.progressBar.cancelLongPress()
                    binding.progressBar.hide()
                    binding.image.show()
                }

                override fun onError(e: java.lang.Exception?) {
                    //do smth when there is picture loading error
                }
            })





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
            }

        }

        updateList(list)


    }


}

