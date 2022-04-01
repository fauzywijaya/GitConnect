package id.fauwiiz.gitconnect.ui.setting

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import id.fauwiiz.gitconnect.databinding.FragmentSettingBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SettingFragment : Fragment() {


    private var _binding : FragmentSettingBinding? = null
    private val binding get() = _binding as FragmentSettingBinding

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

        _binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null){

            val pref = SettingPreferences.getInstance(requireContext().dataStore)
            val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]
            mainViewModel.getThemeSettings().observe(viewLifecycleOwner
            ) { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.switchTheme.isChecked = false
                }
            }

            binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                mainViewModel.saveThemeSetting(isChecked)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}