package com.dibyendu.gefenceutil

import android.app.Application
import com.dibyendu.gefenceutil.di.AppComponent
import com.dibyendu.gefenceutil.di.DaggerAppComponent

class GeofenceUtilApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

}