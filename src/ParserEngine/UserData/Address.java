package ParserEngine.UserData;

public class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private GeoLocation geo;

    public Address(String street, String suite, String city, String zipcode, GeoLocation geo) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geo = geo;
    }

    public GeoLocation getGeo(){
        return this.geo;
    }
}
