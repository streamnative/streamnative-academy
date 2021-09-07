Create Logical Components
-------------------------
1. Run Pulsar Standalone
```
docker run -it \
  -p 6650:6650 \
  -p 8080:8080 \
  -p 4181:4181 \
  --name pulsar \
  apachepulsar/pulsar:2.8.0 \
  bin/pulsar standalone
```

2. Create tenants and namespaces
```
pulsar-admin tenants create orders
pulsar-admin namespaces create orders/inbound
pulsar-admin namespaces create orders/validation
pulsar-admin namespaces create orders/outbound
```

3. Create topics
```
pulsar-admin topics create persistent://orders/inbound/food-orders
pulsar-admin topics create persistent://orders/validation/geo-encoder
pulsar-admin topics create persistent://orders/validation/payments
pulsar-admin topics create persistent://orders/validation/restaurants
pulsar-admin topics create persistent://orders/validation/aggregated-orders
pulsar-admin topics create persistent://orders/outbound/orders-accepted
pulsar-admin topics create persistent://orders/outbound/orders-declined
```

4. Verify creation
```
pulsar-admin topics list orders/inbound
pulsar-admin topics list orders/validation
pulsar-admin topics list orders/outbound
```

5. Deploy the Validation function
```
pulsar-admin functions create \
 --function-config-file function_config/validation_func_config.yaml \
 --jar myjars/workshop.jar
```

6.Deploy the Geo function
```
pulsar-admin functions create \
 --function-config-file function_config/geo_func_config.yaml \
--jar myjars/workshop.jar
```

7.Deploy the Payments function
```
pulsar-admin functions create \
 --function-config-file function_config/payments_func_config.yaml \
--jar myjars/workshop.jar
```

8.Deploy the Restaurant function
```
pulsar-admin functions create \
 --function-config-file function_config/restaurants_func_config.yaml \
--jar myjars/workshop.jar
```

9.Deploy the Aggregation function
```
pulsar-admin functions create \
 --function-config-file function_config/aggregation_func_config.yaml \
--jar myjars/workshop.jar
```

10. Get Functions Configurations
```
pulsar-admin functions get --name order-validation-func
pulsar-admin functions get --name geo-encoder-func
pulsar-admin functions get --name payments-func
pulsar-admin functions get --name restaurants-func
pulsar-admin functions get --name order-aggregation-func
```

11. Get Functions Status
```
pulsar-admin functions status --name order-validation-func
pulsar-admin functions status --name geo-encoder-func
pulsar-admin functions status --name payments-func
pulsar-admin functions status --name restaurants-func
pulsar-admin functions status --name order-aggregation-func
```

12. Get Functions Stats
```
pulsar-admin functions stats --name order-validation-func
pulsar-admin functions stats --name geo-encoder-func
pulsar-admin functions stats --name payments-func
pulsar-admin functions stats --name restaurants-func
pulsar-admin functions stats --name order-aggregation-func
```

13. Delete Functions
```
pulsar-admin functions delete --name order-validation-func
pulsar-admin functions delete --name geo-encoder-func
pulsar-admin functions delete --name payments-func
pulsar-admin functions delete --name restaurants-func
pulsar-admin functions delete --name order-aggregation-func
```


14. Check topics stats
```
pulsar-admin topics stats persistent://orders/inbound/food-orders
pulsar-admin topics stats-internal persistent://orders/inbound/food-orders

pulsar-admin topics stats persistent://orders/validation/geo-encoder
pulsar-admin topics stats-internal persistent://orders/validation/geo-encoder

pulsar-admin topics stats persistent://orders/validation/payments
pulsar-admin topics stats-internal persistent://orders/validation/payments

pulsar-admin topics stats persistent://orders/validation/restaurants
pulsar-admin topics stats-internal persistent://orders/validation/restaurants

pulsar-admin topics stats persistent://orders/validation/aggregated-orders
pulsar-admin topics stats-internal persistent://orders/validation/aggregated-orders

pulsar-admin topics stats persistent://orders/outbound/food-orders
pulsar-admin topics stats-internal persistent://orders/outbound/food-orders

pulsar-admin topics stats persistent://orders/outbound/orders-accepted
pulsar-admin topics stats-internal persistent://orders/outbound/orders-accepted

pulsar-admin topics stats persistent://orders/outbound/orders-declined
pulsar-admin topics stats-internal persistent://orders/outbound/orders-declined
```