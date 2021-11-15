### Internal Stats

```

bin/pulsar-admin topics stats-internal persistent://public/default/mqtt-2

```

### Consume Messages

```

bin/pulsar-client consume "persistent://public/default/mqtt-2" -s "mqtt2" -n 5

```

### Example Pulsar SQL Query

```

select * from pulsar."public/default".iotjetsonjson;

```

