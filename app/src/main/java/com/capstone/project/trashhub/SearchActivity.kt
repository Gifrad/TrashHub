package com.capstone.project.trashhub

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.text.isDigitsOnly
import androidx.core.view.isEmpty
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.project.trashhub.databinding.ActivitySearchBinding
import com.capstone.project.trashhub.network.model.ListStoryUser

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private var listBankSampahAdapter: ListBankSampahAdapter? = null
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        viewModel()
        setupAction()
    }

    private fun setupAction() {
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java)).apply {
                finishAffinity()
            }
        }

    }


    private fun viewModel() {
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        searchViewModel.listUser.observe(this, {
            getAdapter()
            listBankSampahAdapter = it.let { ListBankSampahAdapter(it) }
            binding.rvSearch1.adapter = listBankSampahAdapter

            binding.searchView.imeOptions = EditorInfo.IME_ACTION_SEARCH
            binding.searchView.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean = false

                override fun onQueryTextChange(action: String?): Boolean {
                    if (action != null) {

                        if (action.isEmpty()) {
                            binding.tvNotFound.visibility = View.INVISIBLE
                            binding.rvSearch1.visibility = View.VISIBLE
                            binding.rvSearch2.visibility = View.GONE
                        } else if (action.isNotEmpty()) {
                            val listData = it
                            val filter =
                                listData?.filter { it.name.contains("$action", true) }
                            listBankSampahAdapter =
                                ListBankSampahAdapter(filter as ArrayList<ListStoryUser>)
                            if (action.isNotEmpty()) {
                                binding.rvSearch2.visibility = View.VISIBLE
                                binding.rvSearch2.adapter = listBankSampahAdapter
                                binding.rvSearch1.visibility = View.INVISIBLE
                                if (filter.isEmpty()){
                                    binding.tvNotFound.visibility = View.VISIBLE
                                }else{
                                    binding.tvNotFound.visibility = View.INVISIBLE
                                }
                            } else {
                                binding.rvSearch1.visibility = View.VISIBLE
                                binding.rvSearch2.visibility = View.GONE

                            }
                        }
                    }
                    return false
                }

            })

        })

        searchViewModel.showDataSearch()


    }

    private fun getAdapter() {
        binding.apply {
            rvSearch1.layoutManager = GridLayoutManager(this@SearchActivity, 2)
            rvSearch2.layoutManager = GridLayoutManager(this@SearchActivity, 2)
        }
    }

    /*private fun showDataSearch() {
        ApiConfig.getApiService().getAllStory("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXRGUkxmTjk0WGJqdjhHT2EiLCJpYXQiOjE2NTMxNDgwMTN9.P5Tda4xE6EkL55AfMa0gylmV8DqdkvNAy5RlOq7VT08")
            .enqueue(object : Callback<AllStoryResponse>{
                override fun onResponse(
                    call: Call<AllStoryResponse>,
                    response: Response<AllStoryResponse>
                ) {
                    val dataList = response.body()
                    listBankSampahAdapter = dataList?.let { ListBankSampahAdapter(it.listStory) }
                    binding.rvSearch1.adapter = listBankSampahAdapter

                    binding.searchView.imeOptions = EditorInfo.IME_ACTION_SEARCH
                    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean = false

                        override fun onQueryTextChange(action: String?): Boolean {
                            if (action != null){

                                if (action.isEmpty()){
                                    binding.rvSearch1.visibility = View.VISIBLE
                                    binding.rvSearch2.visibility = View.GONE
                                }else if (action.isNotEmpty()){
                                    val listData = dataList?.listStory
                                    val filter = listData?.filter { it.name.contains("$action", true)  }
                                    listBankSampahAdapter = ListBankSampahAdapter(filter as ArrayList<ListStoryUser>)

                                    if (action.isNotEmpty()){
                                        binding.rvSearch2.visibility = View.VISIBLE
                                        binding.rvSearch2.adapter = listBankSampahAdapter
                                        binding.rvSearch1.visibility = View.INVISIBLE
                                    }else{
                                        binding.rvSearch1.visibility = View.VISIBLE
                                        binding.rvSearch2.visibility = View.GONE
                                    }
                                }
                            }

                            return false
                        }

                    })

                }

                override fun onFailure(call: Call<AllStoryResponse>, t: Throwable) {
                   Toast.makeText(this@SearchActivity ,"${t.message}", Toast.LENGTH_SHORT).show()
                }

            })
    }*/
}