package com.capstone.project.trashhub

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.project.trashhub.databinding.ItemBankSampahBinding
import com.capstone.project.trashhub.network.model.ListStoryUser

class ListBankSampahAdapter (private val listUser: ArrayList<ListStoryUser>) :
    RecyclerView.Adapter<ListBankSampahAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemBankSampahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
        return listUser.size
    }


    inner class ListViewHolder(private val binding: ItemBankSampahBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ListStoryUser) {
            val dateList = user.createdAt.split("T")
            val dateListStory = dateList[0]
            binding.apply {
                tvNameBankSampah.text = user.name
                Glide.with(itemView.context)
                    .load(user.photoUrl)
                    .into(imgBankSampah)
                tvLocation.text = dateListStory
                val listUserDetail = ListStoryUser(
                    user.id,
                    user.name,
                    user.description,
                    user.photoUrl,
                    user.createdAt,
                    user.lat,
                    user.lon
                )
                Log.d("listStoryUser: ", listUserDetail.toString())

                /*itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                    intent.putExtra(DetailStoryActivity.EXTRA_DATA, user)

                    val optionCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(imgUserStory, "imageStory"),
                            Pair(tvName, "nameStory"),
                            Pair(date, "dateStory")
                        )
                    itemView.context.startActivity(intent, optionCompat.toBundle())
                */}
            }
        }
    }

