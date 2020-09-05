package com.dibyendu.gefenceutil.util

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "geofence.db"
const val GEOFENCE_RADIUS_IN_METERS = 500f
const val APP_CENTER_KEY = "3d1498fc-57ae-445c-b29a-e05b0238f447"

enum class Type{
    NAME,
    LATITUDE,
    LONGITUDE
}
