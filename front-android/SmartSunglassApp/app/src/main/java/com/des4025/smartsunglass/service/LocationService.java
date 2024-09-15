package com.des4025.smartsunglass.service;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class LocationService {

    //좌표 경도(리스너 에서 입력)
    private static double longitude;

    //좌표 위도(리스너 에서 입력)
    private static double latitude;

    //좌표 위치 매니저
    private static LocationManager locationManager;

    //좌표 위치 입력 리스너
    private static LocationListener locationListener;

    private static void initLocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
    }

    public static void setLocation(Object systemService) {
        // 좌표 위치 매니저
        locationManager = (LocationManager) systemService;
        try {
            // 1. 이전 좌표 출력
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                System.out.println(location.getLatitude());
                System.out.println(location.getLongitude());
            }

            // 2. 좌표 위치 매니저 요청 위한, 값 초기화
            // 2-1: LocationListener 초기화
            LocationService.initLocationListener();
            long minTime = 10000;
            float minDistance = 0;

            // 3. 좌표 위치 매니저 세팅
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    locationListener
            );
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public static double getLongitude() {
        return longitude;
    }

    public static double getLatitude() {
        return latitude;
    }
}
