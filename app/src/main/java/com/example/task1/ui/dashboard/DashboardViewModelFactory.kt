package com.example.task1.ui.dashboard
import DashboardViewModel
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class DashboardViewModelFactory(
   private val application: Application,

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}
//When your ViewModel requires parameters that aren't directly available through default constructors or need to be injected (e.g., a Repository, a context, or any other dependencies), the ViewModelFactory pattern comes into play.
//my DashboardViewModel needs an Application instance for its initialization.
//This factory takes the Application instance and provides it to the DashboardViewModel when it is created.
//Use a ViewModelFactory to pass parameters.
//If your ViewModel has a no-arg constructor, you can create it without a factory:
//When your ViewModel requires parameters, you use a ViewModelFactory to supply them: