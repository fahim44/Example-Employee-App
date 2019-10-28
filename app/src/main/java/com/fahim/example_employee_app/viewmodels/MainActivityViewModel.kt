package com.fahim.example_employee_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fahim.example_employee_app.EmployeeApplication
import com.fahim.example_employee_app.repositories.EmployeeRepository
import com.fahim.example_employee_app.utils.SharedPreference
import javax.inject.Inject

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var repository: EmployeeRepository
    
    @Inject
    lateinit var sharedPreference: SharedPreference
    
    val navigateToTabActivityLD = MutableLiveData<Boolean>()

    lateinit var dummyDataSetLD : LiveData<Boolean>

    init {
        (application as EmployeeApplication).component.inject(this)
    }

    //check if data already loaded from server or not( from pref), if it is loaded navigate to next page
    //if it is not loaded, load and save, then set pref value, then navigate to next page
    fun checkExistingData(){
        if (!sharedPreference.isInitDataLoaded()){
            dummyDataSetLD = repository.getDummyDataFromServiceAndLoadToLocalDB()
        }else {
            dummyDataSetLD = MutableLiveData<Boolean>()
            navigateToTabActivityLD.value = true
        }
    }

    fun setPrefInitDataValue(value:Boolean){
        if(value)
            sharedPreference.initDataLoaded()
        navigateToTabActivityLD.value = true
    }
}