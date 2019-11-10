package com.fahim.example_employee_app.di.workerModule

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

@Module(includes = [AssistedInject_WorkerAssistedInjectionModule::class])
@AssistedModule
interface WorkerAssistedInjectionModule