kubectl exec -it pulsar-toolset-0 -n demo -- bin/pulsar-admin sinks delete --name gcs-sink
kubectl exec -it pulsar-toolset-0 -n demo -- bin/pulsar-admin functions delete --name event_parser_func
