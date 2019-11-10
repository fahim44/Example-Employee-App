package com.fahim.example_employee_app.workManager

import android.content.Context
import androidx.work.*
import com.fahim.example_employee_app.util.EmployeeKeys
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class EmployeeWorkManagerController @Inject constructor(private val context: Context) {

    fun startDummyDataWorker(){
        // Create a Constraints object that defines when the task should run
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresDeviceIdle(true)
            .build()

        // ...then create a OneTimeWorkRequest that uses those constraints
        val workRequest = OneTimeWorkRequestBuilder<DummyDataRetrieveWorker>()
            .setConstraints(constraints)
            .setInitialDelay(1, TimeUnit.MINUTES)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS)
            .build()

        // unique task existing policy
        val existingWorkPolicy = ExistingWorkPolicy.KEEP

        //start worker
        WorkManager.getInstance(context).enqueueUniqueWork(EmployeeKeys.DUMMY_DATA_WORKER,existingWorkPolicy,workRequest)
    }


    fun stopDummyDataWorker(){
        WorkManager.getInstance(context).cancelUniqueWork(EmployeeKeys.DUMMY_DATA_WORKER)
    }
}