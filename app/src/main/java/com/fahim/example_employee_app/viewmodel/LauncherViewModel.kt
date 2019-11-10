package com.fahim.example_employee_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahim.example_employee_app.repository.EmployeeRepository
import com.fahim.example_employee_app.workManager.EmployeeWorkManagerController
import kotlinx.coroutines.launch
import javax.inject.Inject

class LauncherViewModel @Inject constructor(private val repository: EmployeeRepository, private val workerController : EmployeeWorkManagerController) : ViewModel() {
    
    private val _navigateToTabActivityMLD = MutableLiveData<Boolean>()

    val navigateToTabActivityLD : LiveData<Boolean> = _navigateToTabActivityMLD

    //check if data already loaded from server or not( from pref), if it is loaded navigate to next page
    //if it is not loaded, load and save, then set pref value, then navigate to next page
    //if it is not loaded and failed to load/save data, navigate to next page
    fun checkExistingData(){
        if (!repository.isDummyDataLoaded()){
            viewModelScope.launch {
                val result = repository.getDummyDataFromServerAndLoadToLocalDB()
                if (result) {
                    repository.setDummyDataLoaded()
                    workerController.stopDummyDataWorker()
                }else
                    workerController.startDummyDataWorker()
                _navigateToTabActivityMLD.value = true
            }
        }else {
            workerController.stopDummyDataWorker()
            _navigateToTabActivityMLD.value = true
        }
    }
}