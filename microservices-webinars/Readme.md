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
```

Verify creation
```
bin/pulsar-admin topics list orders/inbound/
```
