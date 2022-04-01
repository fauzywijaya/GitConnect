package id.fauwiiz.gitconnect.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.shashank.sony.fancytoastlib.FancyToast
import id.fauwiiz.gitconnect.R
import id.fauwiiz.gitconnect.data.remote.response.DetailUserResponse
import id.fauwiiz.gitconnect.databinding.FragmentDetailBinding
import id.fauwiiz.gitconnect.ui.adapter.SectionPagerAdapter
import id.fauwiiz.gitconnect.utils.DataMapper
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.ln
import kotlin.math.pow


class DetailFragment : Fragment() {

    private var _fragmentDetailBinding : FragmentDetailBinding ?= null
    private val binding get() = _fragmentDetailBinding as FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()
    private var profileUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getDetail(args.username)
        viewModel.setStatus(false)


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
        _fragmentDetailBinding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){
            USERNAME = args.username
            setFavorite(args.username)

            viewModel.loading.observe(viewLifecycleOwner) { loading ->
                showLoading(loading)
            }
            viewModel.detail.observe(viewLifecycleOwner) { data ->
                populateDetail(data)
                initFavoriteState(data)
                profileUrl = data.htmlUrl
            }

            viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
                FancyToast.makeText(
                    requireContext(), error,
                    FancyToast.LENGTH_LONG,
                    FancyToast.ERROR, true
                ).show()
                val action = DetailFragmentDirections.actionNavigationDetailToNavigationHome()
                findNavController().navigate(action)

            }


            val message = getString(R.string.share_bt, USERNAME)
            binding.ivShare.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, message)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, null)
                startActivity(shareIntent)
            }
            binding.ivBack.setOnClickListener {
                val action = DetailFragmentDirections.actionNavigationDetailToNavigationHome()
                findNavController().navigate(action)
            }
            binding.btShare.setOnClickListener {
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(profileUrl)
                }.also {
                    startActivity(it)
                }
            }
        }
    }

    private fun populateDetail(user: DetailUserResponse){
        with(binding){
            tvName.text = user.name ?: "-"
            tvUsername.text = user.username
            tvLocation.text = user.location ?: "-"
            tvCompany.text = user.company ?: "-"
            tvRepositories.text = convertNumber(user.publicRepos.toLong())
            tvFollowers.text = convertNumber(user.followers.toLong())
            tvFollowing.text = convertNumber(user.following.toLong())
            Glide.with(root)
                .load(user.avatarUrl)
                .into(ivAvatar)
        }
        setUpViewPager()
    }

    private fun initFavoriteState(user: DetailUserResponse){
        val data = DataMapper.responseToEntity(user)

        viewModel.status.observe(viewLifecycleOwner) { state ->
            if (state) {
                binding.btFavorite.apply {
                    text = getString(R.string.favorited)
                    setBackgroundResource(R.drawable.button_primary)
                    setOnClickListener {
                        viewModel.deleteFavorite(args.username)
                        viewModel.setStatus(false)
                        FancyToast.makeText(
                            context, getString(R.string.delete_message),
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS, false
                        ).show()

                    }
                }
            } else {
                binding.btFavorite.apply {
                    setBackgroundResource(R.drawable.button_secondary)
                    setOnClickListener {
                        viewModel.insertFavorite(data)
                        viewModel.setStatus(true)
                        FancyToast.makeText(
                            context, getString(R.string.add_massage),
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS, false
                        ).show()
                    }
                }
            }
        }
    }

    private fun setUpViewPager() {
        val viewPager: ViewPager2 = binding.viewPager
        val tabs : TabLayout = binding.tabs

        viewPager.adapter = SectionPagerAdapter(this@DetailFragment)

        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
    private fun convertNumber(format: Long) : String {
        if (format < 1000) return ""+format

        val count = (ln(format.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f%c", format / 1000.0.pow(count.toDouble()), "KMBTPE"[count-1])
    }

    private fun showLoading(state: Boolean) {
        binding.loadingDetail.isVisible = state
        binding.appBarLayout.isVisible = !state
        binding.coordinatorLayout.isVisible = !state
    }

    private fun setFavorite(username: String){
        viewModel.search(username).observe(viewLifecycleOwner){
            viewModel.setStatus(it != null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentDetailBinding
    }

    companion object {
        lateinit var USERNAME: String
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.followings
        )

    }
}