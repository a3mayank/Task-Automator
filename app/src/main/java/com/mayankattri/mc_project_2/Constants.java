package com.mayankattri.mc_project_2;

/**
 * Created by mayank on 21/10/16.
 */
public class Constants {
    public static class Geometry {
        public static double MinLatitude = -90.0;
        public static double MaxLatitude = 90.0;
        public static double MinLongitude = -180.0;
        public static double MaxLongitude = 180.0;
        public static double MinRadius = 0.01; // kilometers
        public static double MaxRadius = 20.0; // kilometers
    }

    public static class SharedPrefs {
        public static String Geofences = "SHARED_PREFS_GEOFENCES";
    }
}
