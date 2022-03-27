package id.fauwiiz.gitconnect.ui.detail.follower

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.fauwiiz.gitconnect.data.remote.response.User
import id.fauwiiz.gitconnect.databinding.FragmentFollowerBinding
import id.fauwiiz.gitconnect.ui.adapter.UserAdapter
import id.fauwiiz.gitconnect.ui.detail.DetailFragment
import id.fauwiiz.gitconnect.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FollowerFragment : Fragment() {

    private var _binding : FragmentFollowerBinding?= null
    private val binding get() = _binding as FragmentFollowerBinding

    private val followerViewModel by sharedViewModel<DetailViewModel>()
    private lateinit var userAdapter: UserAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)

        followerViewModel.getFollowers(DetailFragment.USERNAME)

        followerViewModel.listFollower.observe(viewLifecycleOwner){
            showLoading(false)
            if (!it.isNullOrEmpty()){
                setUpRecyclerView(it)
                showNoData(false)
            }else{
                showNoData(true)
            }
        }
    }

    private fun setUpRecyclerView(data: ArrayList<User>) {
        userAdapter = UserAdapter()
        userAdapter.setFragment(this::class.java.simpleName)
        userAdapter.setData(data)
        binding.recyclerviewFollowers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
            setHasFixedSize(true)
        }
    }
    private fun showNoData(condition: Boolean) {
        binding.empty.visibility = if (condition) View.VISIBLE else View.GONE
        binding.recyclerviewFollowers.visibility = if (condition) View.GONE else View.VISIBLE
    }

    private fun showLoading(condition: Boolean) {
        binding.loadingFollowers.visibility = if (condition) View.VISIBLE else View.GONE
    }
}