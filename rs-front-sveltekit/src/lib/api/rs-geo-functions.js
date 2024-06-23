const MIN_LON = 31.229682;
const MAX_LON = 37.327410;
const MIN_LAT = 8.437305;
const MAX_LAT = 11.000369;

export function randomGeoPoint() {
    return {
      "longitude": MIN_LON+(MAX_LON-MIN_LON)*Math.random(),
      "latitude": MIN_LAT+(MAX_LAT-MIN_LAT)*Math.random()
    };
  }
  