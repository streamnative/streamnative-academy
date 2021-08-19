package sn.academy.food_delivery.services.processing;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.slf4j.Logger;
import sn.academy.food_delivery.models.avro.Address;
import sn.academy.food_delivery.models.avro.GeoEncodedAddress;
import sn.academy.food_delivery.models.avro.ValidatedFoodOrder;
import sn.academy.food_delivery.services.external.GoogleAPIGeocodingService;

public class GeoEncoderService implements Function<Address, ValidatedFoodOrder> {
    private boolean isInitialized = false;
    private Logger logger;
    private GoogleAPIGeocodingService googleAPIGeoEncodingService;
    private final boolean useMock = true;

    @Override
    public ValidatedFoodOrder process(Address address, Context context) {
        if (!isInitialized) {
            logger = context.getLogger();
            init(context);
        }
        GeoEncodedAddress geoEncodedAddress = googleAPIGeoEncodingService.validateAddressMock(address);

        return ValidatedFoodOrder.newBuilder()
                    .setDeliveryLocation(geoEncodedAddress)
                    .build();
    }

    private void init(Context context) {
        logger.info("Initializing Google Api Geo Context..");

        // for demonstration purposes we will mock the service
        // alternatively you can provide an apiKey as a secret
        // and extract it from the context
        if (useMock) {
            googleAPIGeoEncodingService = new GoogleAPIGeocodingService();
        } else {
            String apiKey = context.getSecret("apiKey");
            googleAPIGeoEncodingService = new GoogleAPIGeocodingService(apiKey);
        }
        isInitialized = true;
    }
}
