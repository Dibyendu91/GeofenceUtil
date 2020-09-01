package com.dibyendu.gefenceutil.data.repository

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.dibyendu.gefenceutil.data.dao.GeofenceDao
import com.dibyendu.gefenceutil.data.model.Reminder
import com.dibyendu.gefenceutil.service.GeofenceBroadcastReceiver
import com.dibyendu.gefenceutil.service.GeofenceErrorMessages
import com.dibyendu.gefenceutil.util.GEOFENCE_RADIUS_IN_METERS
import com.dibyendu.gefenceutil.view.GeofenceActivity
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import javax.inject.Inject


class GeofenceRepository @Inject constructor(
    private val context: Context,
    private val geofenceDao: GeofenceDao
) {

    private val geofencingClient = LocationServices.getGeofencingClient(context)
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        intent.action = GeofenceActivity.ACTION_GEOFENCE_EVENT
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun addGeofence(
        reminder: Reminder,
        success: () -> Unit,
        failure: (error: String) -> Unit
    ) {
        val geofence = buildGeofence(reminder)
        if (geofence != null
            && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            geofencingClient
                .addGeofences(buildGeofencingRequest(geofence), geofencePendingIntent)
                .addOnSuccessListener {
                    success()
                }
                .addOnFailureListener {
                    failure(GeofenceErrorMessages.getErrorString(context, it))
                }
        }
    }

    private fun buildGeofence(reminder: Reminder): Geofence? {
        val latitude = reminder.location.latitude
        val longitude = reminder.location.longitude
        val radius = GEOFENCE_RADIUS_IN_METERS
        return Geofence.Builder()
            .setRequestId(reminder.id)
            .setCircularRegion(
                latitude,
                longitude,
                radius
            )
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()
    }

    private fun buildGeofencingRequest(geofence: Geofence): GeofencingRequest {
        return GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofences(listOf(geofence))
            .build()
    }

    suspend fun addReminder(reminder: Reminder) {
        geofenceDao.insert(reminder)
    }

    suspend fun getAvailableGeofences() = geofenceDao.getGeofences()

    fun getGeofence(id: String) = geofenceDao.getGeofence(id)

}