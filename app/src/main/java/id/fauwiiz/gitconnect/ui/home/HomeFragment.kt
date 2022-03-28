package id.fauwiiz.gitconnect.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.shashank.sony.fancytoastlib.FancyToast
import id.fauwiiz.gitconnect.data.remote.response.User
import id.fauwiiz.gitconnect.databinding.FragmentHomeBinding
import id.fauwiiz.gitconnect.ui.adapter.UserAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding ?= null
    private val binding get() = _binding as FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var userAdapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough().apply {
            duration = 250L
        }
        exitTransition = MaterialElevationScale(false).apply {
            duration = 250L
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 250L
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){

            showLoading(false)
            setIllustration(true)
            listObserver()
            initSearchUser()

            homeViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
                FancyToast.makeText(
                    context, error,
                    FancyToast.LENGTH_LONG,
                    FancyToast.ERROR, false
                ).show()
            }

            binding.tvGreet.text = grettingMessage()
        }
    }
    private fun initSearchUser(){
        binding.search
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                observeSearch(query)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
//                showLoading(true)
                setIllustration(false)
                return true
            }
        })
        binding.search.setOnCloseListener {
            listObserver()
            true
        }

    }

    private fun listObserver(){
        homeViewModel.listUser.observe(viewLifecycleOwner){
            setIllustration(false)
            setUpRecyclerView(it.items)
        }
    }

    private fun observeSearch(query: String){
        homeViewModel.getSearchUser(query)
        homeViewModel.listUser.observe(viewLifecycleOwner){
            if (it.items.isNullOrEmpty()){
                setIllustration(true)
                showLoading(false)
            }else{
                setIllustration(false)
                showLoading(false)
                setUpRecyclerView(it.items)
            }
        }
    }
    private fun setUpRecyclerView(data: ArrayList<User>) {
        userAdapter = UserAdapter()
        userAdapter.setFragment(this::class.java.simpleName)
        userAdapter.setData(data)
        showLoading(false)
        binding.rvUser.layoutManager = LinearLayoutManager(context)
        binding.rvUser.adapter = userAdapter
        binding.rvUser.setHasFixedSize(true)
    }

    private fun setIllustration(state: Boolean){
        when(state){
            true -> binding.empty.visibility = View.VISIBLE
            else -> binding.empty.visibility = View.INVISIBLE
        }
    }

    fun grettingMessage(): String{

        val calendar = Calendar.getInstance()
        val timeOfDay = calendar.get(Calendar.HOUR_OF_DAY)


        return when (timeOfDay){
            in 0..11 -> "Selamat Pagi, Dicoding"
            in 12..18 -> "Selamat Sore, Dicoding"
            in 18..23 -> "Selamat Malam, Dicoding"
            else -> "Hello"
        }
    }


    private fun showLoading(state: Boolean){
        when(state){
            true -> binding.loading.visibility = View.VISIBLE
            else -> binding.loading.visibility = View.INVISIBLE
        }
    }



}