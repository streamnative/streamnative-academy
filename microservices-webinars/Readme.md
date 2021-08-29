Create Logical Components
-------------------------

Create tenants and namespaces
```
pulsar-admin --admin-url http://localhost:80 tenants create orders
pulsar-admin --admin-url http://localhost:80 namespaces create orders/inbound
```

Create topics
```
pulsar-admin --admin-url http://localhost:80 topics create persistent://orders/inbound/food-orders
pulsar-admin --admin-url http://localhost:80 topics create persistent://orders/inbound/geo-encoder
pulsar-admin --admin-url http://localhost:80 topics create persistent://orders/inbound/payments
pulsar-admin --admin-url http://localhost:80 topics create persistent://ordersinbound/restaurants
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

pulsar-admin --admin-url http://localhost:80 schemas get persistent://orders/inbound/geo-encoder
pulsar-admin --admin-url http://localhost:80 schemas delete persistent://orders/inbound/geo-encoder
```

Verify creation
```
bin/pulsar-admin topics list orders/inbound/
```

```
bin/pulsar-client consume -n 0 -s "subs" -p Earliest persistent://orders/inbound/aggregated-orders
```


```
bin/pulsar-admin  topics delete persistent://orders/inbound/food-orders
bin/pulsar-admin  topics delete persistent://orders/inbound/geo-encoder
bin/pulsar-admin  topics delete persistent://orders/inbound/payments
bin/pulsar-admin  topics delete persistent://orders/inbound/restaurants
bin/pulsar-admin  topics delete persistent://orders/inbound/aggregated-orders

bin/pulsar-admin  schemas delete persistent://orders/inbound/food-orders
bin/pulsar-admin  schemas delete persistent://orders/inbound/geo-encoder
bin/pulsar-admin  schemas delete persistent://orders/inbound/payments
bin/pulsar-admin  schemas delete persistent://orders/inbound/restaurants
bin/pulsar-admin  schemas delete persistent://orders/inbound/aggregated-orders
```