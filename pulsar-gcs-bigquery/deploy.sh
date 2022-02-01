echo "Setting up the connector ..."
kubectl exec -it pulsar-toolset-0 -n demo -- bin/pulsar-admin sink create \
--sink-config-file config/gcs_sink.yaml \
--name gcs-sink --archive connectors/pulsar-io-cloud-storage-2.8.1.30.nar

echo "Checking the connector setup ..."
kubectl exec -it pulsar-toolset-0 -n demo -- bin/pulsar-admin sinks list
kubectl exec -it pulsar-toolset-0 -n demo -- bin/pulsar-admin sinks status --name gcs-sink --tenant public --namespace default

sleep 1
echo "Deploying the Parser function."
kubectl exec -it pulsar-toolset-0 -n demo -- bin/pulsar-admin functions create \
 --function-config-file config/parser_func_config.yaml \
 --jar myjars/examples.jar
