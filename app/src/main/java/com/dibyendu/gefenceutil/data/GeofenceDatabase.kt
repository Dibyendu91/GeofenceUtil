package com.dibyendu.gefenceutil.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dibyendu.gefenceutil.data.dao.GeofenceDao
import com.dibyendu.gefenceutil.data.model.Reminder
import com.dibyendu.gefenceutil.util.DATABASE_VERSION

@Database(entities = [Reminder::class], version = DATABASE_VERSION, exportSchema = false)
abstract class GeofenceDatabase : RoomDatabase() {

    abstract fun geofenceDao(): GeofenceDao

}