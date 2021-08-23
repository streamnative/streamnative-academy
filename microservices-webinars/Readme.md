Create Logical Components
-------------------------

Create tenants and namespaces
```
bin/pulsar-admin tenants create orders
bin/pulsar-admin namespaces create orders/inbound
```

Create topics
```
pulsar-admin --admin-url http://localhost:80 topics create persistent://orders/inbound/food-orders
pulsar-admin --admin-url http://localhost:80 topics create persistent://orders/inbound/geo-encoder
pulsar-admin --admin-url http://localhost:80 topics create persistent://orders/inbound/payments
pulsar-admin --admin-url http://localhost:80 topics create persistent://orders/inbound/restaurants
pulsar-admin --admin-url http://localhost:80 topics create persistent://orders/inbound/aggregated-orders

pulsar-admin --admin-url http://localhost:80 topics delete persistent://orders/inbound/food-orders
pulsar-admin --admin-url http://localhost:80 topics delete persistent://orders/inbound/geo-encoder
pulsar-admin --admin-url http://localhost:80 topics delete persistent://orders/inbound/payments
pulsar-admin --admin-url http://localhost:80 topics delete persistent://orders/inbound/restaurants
pulsar-admin --admin-url http://localhost:80 topics delete persistent://orders/inbound/aggregated-orders

pulsar-admin --admin-url http://localhost:80 schemas delete persistent://orders/inbound/food-orders
pulsar-admin --admin-url http://localhost:80 schemas delete persistent://orders/inbound/geo-encoder
pulsar-admin --admin-url http://localhost:80 schemas delete persistent://orders/inbound/payments
pulsar-admin --admin-url http://localhost:80 schemas delete persistent://orders/inbound/restaurants
pulsar-admin --admin-url http://localhost:80 schemas delete persistent://orders/inbound/aggregated-orders
```

Verify creation
```
bin/pulsar-admin topics list orders/inbound/
```

```
bin/pulsar-client consume -n 0 -s "subs" -p Earliest persistent://orders/inbound/aggregated-orders
```