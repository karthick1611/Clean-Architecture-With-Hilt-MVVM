package com.clean_architecture.hilt_mvvm.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import com.clean_architecture.hilt_mvvm.core.extension.inTransaction
import com.clean_architecture.hilt_mvvm.databinding.ActivityLayoutBinding

/**
 * Base Activity class with helper methods for handling fragment transactions and back button
 * events.
 *
 * @see AppCompatActivity
 */
@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLayoutBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolBarContainer.toolbar)
        addFragment(savedInstanceState)

        // inside of any of your application's code
//        val consumerKey = BuildConfig.API_KEY_IMAGES

//        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
//                (supportFragmentManager.findFragmentById(binding.fragmentContainer.id) as BaseFragment).onBackPressed()
//            }
//        })
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(binding.fragmentContainer.id) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }

    fun toolbar() = binding.toolBarContainer.toolbar

    fun fragmentContainer() = binding.fragmentContainer
    fun progressBar() = binding.toolBarContainer.progress

    private fun addFragment(savedInstanceState: Bundle?) =
        savedInstanceState ?: supportFragmentManager.inTransaction {
            add(binding.fragmentContainer.id, fragment())
        }

    abstract fun fragment(): BaseFragment
}
