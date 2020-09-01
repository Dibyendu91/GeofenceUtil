package com.dibyendu.gefenceutil.di

import android.content.Context
import com.dibyendu.gefenceutil.data.repository.GeofenceRepository
import com.dibyendu.gefenceutil.view.AddGeofenceFragment
import com.dibyendu.gefenceutil.view.GeofenceActivity
import com.dibyendu.gefenceutil.view.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun repository(): GeofenceRepository
    fun inject(activity: GeofenceActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(addGeofenceFragment: AddGeofenceFragment)

}