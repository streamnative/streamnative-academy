Create Logical Components
-------------------------

Create tenants and namespaces
```
bin/pulsar-admin tenants create orders
bin/pulsar-admin namespaces create orders/inbound
```

Create topics
```
bin/pulsar-admin topics create persistent://orders/inbound/food-orders
bin/pulsar-admin topics create persistent://orders/inbound/geo-encoder
bin/pulsar-admin topics create persistent://orders/inbound/payments
bin/pulsar-admin topics create persistent://orders/inbound/restaurants
bin/pulsar-admin topics create persistent://orders/inbound/aggregated-orders

bin/pulsar-admin topics delete persistent://orders/inbound/food-orders
bin/pulsar-admin topics delete persistent://orders/inbound/geo-encoder
bin/pulsar-admin topics delete persistent://orders/inbound/payments
bin/pulsar-admin topics delete persistent://orders/inbound/restaurants
bin/pulsar-admin topics delete persistent://orders/inbound/aggregated-orders

bin/pulsar-admin schemas delete persistent://orders/inbound/food-orders
bin/pulsar-admin schemas delete persistent://orders/inbound/geo-encoder
bin/pulsar-admin schemas delete persistent://orders/inbound/payments
bin/pulsar-admin schemas delete persistent://orders/inbound/restaurants
bin/pulsar-admin schemas delete persistent://orders/inbound/aggregated-orders
```

Verify creation
```
bin/pulsar-admin topics list orders/inbound/
```

```
bin/pulsar-client consume -n 0 -s "subs" -p Earliest persistent://orders/inbound/aggregated-orders
```