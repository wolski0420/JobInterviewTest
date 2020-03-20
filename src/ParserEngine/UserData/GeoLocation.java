package ParserEngine.UserData;

public class GeoLocation {
    private double lat;
    private double lng;

    public GeoLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat(){
        return this.lat;
    }

    public double getLng(){
        return this.lng;
    }
}
