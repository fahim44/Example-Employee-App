package com.fahim.example_employee_app.di.fragmentModule

import android.app.Activity
import com.fahim.example_employee_app.ui.fragment.RemoveListFragment
import dagger.Module
import dagger.Provides

@Module
class RemoveListFragmentModule {
    @Provides
    fun provideActivity(fragment: RemoveListFragment): Activity  = fragment.activity!!
}