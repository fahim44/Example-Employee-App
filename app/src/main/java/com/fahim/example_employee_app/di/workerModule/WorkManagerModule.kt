package com.fahim.example_employee_app.di.workerModule

import androidx.work.WorkerFactory
import com.fahim.example_employee_app.di.anotation.WorkerKey
import com.fahim.example_employee_app.workManager.ChildWorkerFactory
import com.fahim.example_employee_app.workManager.DummyDataRetrieveWorker
import com.fahim.example_employee_app.workManager.MainWorkerFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkManagerModule {
    @Binds
    @IntoMap
    @WorkerKey(DummyDataRetrieveWorker::class)
    fun bindDummyDataRetriveWorker(factory: DummyDataRetrieveWorker.Factory): ChildWorkerFactory


    @Binds
    fun bindWorkerFactory(factory: MainWorkerFactory): WorkerFactory
}