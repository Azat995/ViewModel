package com.example.viewmodel

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import coil.load
import com.example.viewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //    private val userViewModel: UserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
    private val userViewModel by viewModels<UserViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObservers()
        setClickListeners()
    }

    private fun setObservers() = with(userViewModel) {
        userLiveData.observe(this@MainActivity) { userModel ->
            setUserData(userModel)
        }
        counterLiveData.observe(this@MainActivity) { counterValue ->
            binding.counterTextView.text = counterValue.toString()
        }
    }

    private fun setUserData(user: UserModel) = with(binding) {
        nameTextView.text = user.name
        surnameTextView.text = user.surname
        avatarImageView.load(user.avatar)
    }

    private fun setClickListeners() = with(binding) {
        startCounterButton.setOnClickListener {
            userViewModel.startCounter()
        }
        changeButton.setOnClickListener {
            progressBar.isVisible = true
            userViewModel.changeUserName("Narek", successCallback = {
                progressBar.isVisible = false
            })
        }
    }
}
