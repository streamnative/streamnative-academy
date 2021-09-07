package sn.academy.food_delivery.services.external;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import sn.academy.food_delivery.models.avro.Address;
import sn.academy.food_delivery.models.avro.GeoEncodedAddress;
import sn.academy.food_delivery.models.avro.GeoLocation;

public class GoogleAPIGeocodingService {
    private GeoApiContext geoApiContext;

    public GoogleAPIGeocodingService() {}

    public GoogleAPIGeocodingService(String apiKey) {
        geoApiContext = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .maxRetries(3)
                .retryTimeout(3000, TimeUnit.MILLISECONDS)
                .build();
    }

    public GeoEncodedAddress validateAddressMock(Address address) {
        GeoEncodedAddress geoEncodedAddress = new GeoEncodedAddress();
        geoEncodedAddress.setAddress(address);
        geoEncodedAddress.setIsValidAddress(true);
        return geoEncodedAddress;
    }

    public GeoEncodedAddress validateAddress(Address address) {
        GeoEncodedAddress geoEncodedAddress = new GeoEncodedAddress();
        geoEncodedAddress.setAddress(address);
        geoEncodedAddress.setIsValidAddress(false);


        try {
            GeocodingResult[] result = GeocodingApi.geocode(geoApiContext, formatAddress(address)).await();
            if(result != null && result[0].geometry != null) {
                Geometry geometry = result[0].geometry;
                GeoLocation geoLocation = new GeoLocation(geometry.location.lat, geometry.location.lng);
                geoEncodedAddress.setGeoLocation(geoLocation);
                geoEncodedAddress.setIsValidAddress(true);
            }
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
           return geoEncodedAddress;
        }
    }

    private String formatAddress(Address address) {
        return String.format(
                "%s %s %s %s",
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZip()
        );
    }
}
