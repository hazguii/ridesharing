package com.rs.domain;

public record GeoPoint(double latitude, double longitude) {
    public boolean equals(GeoPoint geoPoint){
        return this.latitude == geoPoint.latitude && this.longitude == geoPoint.longitude;
    }
}
