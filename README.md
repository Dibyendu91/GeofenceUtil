# Geofencing Util

## Features implemented based on:
1. The home screen has a list of locations with location which is already added. The first time will be empty. Add bottom right side floating button with + icon
1. If user click + button Redirect to Add geofence screen, In this screen three edit text fields for location name, latitude, longitude and add button
1. Location name user can fill the name(i.e Home, Office, Mall..). Latitude, Longitude edit text user will enter the coordinate.
1. After entered valid inputs user will click the add button. then It will redirect to the home screen. The added item should reflect in the home screen. 
1. Home screen, List item should like the first line will be location name, the second line will be (lat,lng) points
1. All data should be store in Room database.
1. Implement Geofence, if the user entered any one of the added locations within 500m circle. Local Notification should trigger like (Entered into the Home(Location name)) same 
for exit also like(Exit from the home)
1. The app should work even killed state.
