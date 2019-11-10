package com.fahim.example_employee_app.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.fahim.example_employee_app.repository.EmployeeRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope

class DummyDataRetrieveWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    private val repository: EmployeeRepository
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork()= coroutineScope {
        if (repository.isDummyDataLoaded()) return@coroutineScope Result.success()

        if (isStopped) return@coroutineScope Result.failure()
        val serverData = repository.retriveDataFromServer()

        if (isStopped) return@coroutineScope Result.failure()
        var result = false
        if(serverData.first)
            result = repository.insertEmployees(serverData.second)

        if(!result) return@coroutineScope Result.retry()
        repository.setDummyDataLoaded()
        Result.success()
    }


    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}