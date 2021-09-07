package sn.academy.food_delivery.services.processing;

import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.schema.AvroSchema;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.slf4j.Logger;
import sn.academy.food_delivery.config.AppConfig;
import sn.academy.food_delivery.models.avro.Address;
import sn.academy.food_delivery.models.avro.GeoEncodedAddress;
import sn.academy.food_delivery.models.avro.ValidatedFoodOrder;
import sn.academy.food_delivery.services.external.GoogleAPIGeocodingService;

public class GeoEncoderService implements Function<Address, Void> {
    private boolean isInitialized = false;
    private Logger logger;
    private GoogleAPIGeocodingService googleAPIGeoEncodingService;
    private final boolean useMock = true;

    @Override
    public Void process(Address address, Context context) throws PulsarClientException {
        if (!isInitialized) {
            logger = context.getLogger();
            init(context);
        }

        // Mock http call to an external API
        GeoEncodedAddress geoEncodedAddress = googleAPIGeoEncodingService.validateAddressMock(address);

        ValidatedFoodOrder validatedFoodOrder = new ValidatedFoodOrder();
        validatedFoodOrder.setDeliveryLocation(geoEncodedAddress);

        logger.info("Sending: " + validatedFoodOrder);
        context.newOutputMessage(AppConfig.ORDER_AGGREGATION_TOPIC_NAME, AvroSchema.of(ValidatedFoodOrder.class))
                .properties(context.getCurrentRecord().getProperties())
                .value(validatedFoodOrder)
                .sendAsync();
        return null;
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
