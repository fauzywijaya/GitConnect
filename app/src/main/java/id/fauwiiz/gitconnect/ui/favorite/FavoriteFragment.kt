package id.fauwiiz.gitconnect.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import id.fauwiiz.gitconnect.R
import id.fauwiiz.gitconnect.data.local.entity.UserEntity
import id.fauwiiz.gitconnect.databinding.FragmentFavoriteBinding
import id.fauwiiz.gitconnect.ui.adapter.FavoriteAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment() {

    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding as FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val viewModel: FavoriteViewModel by viewModel()

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
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){
            viewModel.userListFavorite.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    setupRecyclerView(it)
                } else {
                    showIllustration(true)
                }
            }
        }
    }

    private fun showIllustration(state: Boolean) {
        binding.empty.isVisible = state
        binding.recyclerviewFavorite.isVisible = !state

    }

    private fun setupRecyclerView(data: List<UserEntity>) {
        favoriteAdapter = FavoriteAdapter()
        favoriteAdapter.setData(data)
        binding.recyclerviewFavorite.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
            setHasFixedSize(true)
        }
    }
}