package com.dibyendu.gefenceutil.util

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "geofence.db"
const val GEOFENCE_RADIUS_IN_METERS = 500f
const val APP_CENTER_KEY = "16a83511-5769-409e-b851-6d410af84a08"

enum class Type{
    NAME,
    LATITUDE,
    LONGITUDE
}