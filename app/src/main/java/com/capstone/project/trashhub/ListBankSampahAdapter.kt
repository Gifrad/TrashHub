package com.capstone.project.trashhub

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.project.trashhub.databinding.ItemBankSampahBinding
import com.capstone.project.trashhub.network.model.ListStoryUser

//    ADAPTER 1
class ListBankSampahAdapter(private val listUser: ArrayList<ListStoryUser>) :
    RecyclerView.Adapter<ListBankSampahAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemBankSampahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailBankSampahActivity::class.java)
                    intent.putExtra(DetailBankSampahActivity.EXTRA_DATA, user)

                    val optionCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            androidx.core.util.Pair(imgBankSampah, "imageStory"),
                            androidx.core.util.Pair(tvLocation, "dateStory"),
                            androidx.core.util.Pair(tvNameBankSampah, "nameStory"),
                        )
                    itemView.context.startActivity(intent, optionCompat.toBundle())

                }
            }
        }
    }
}


/*
class ListBankSampahAdapter :
    PagingDataAdapter<ListStoryUser, ListBankSampahAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemBankSampahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }


     class ListViewHolder(private val binding: ItemBankSampahBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryUser) {
            val dateList = data.createdAt.split("T")
            val dateListStory = dateList[0]
            binding.apply {
                tvNameBankSampah.text = data.name
                Glide.with(itemView.context)
                    .load(data.photoUrl)
                    .into(imgBankSampah)
                tvLocation.text = dateListStory
                val listUserDetail = ListStoryUser(
                    data.id,
                    data.name,
                    data.description,
                    data.photoUrl,
                    data.createdAt,
                    data.lat,
                    data.lon
                )
                Log.d("listStoryUser: ", listUserDetail.toString())

                */
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
                *//*

            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryUser>() {
            override fun areItemsTheSame(oldItem: ListStoryUser, newItem: ListStoryUser): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryUser,
                newItem: ListStoryUser
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
*/
