mvn clean package

echo "Creating the folders."
kubectl exec -it pulsar-toolset-0 -n demo -- mkdir -p myjars
kubectl exec -it pulsar-toolset-0 -n demo -- mkdir -p connectors

echo "Uploading resources on the cluster ..."
kubectl cp target/pulsar-gcs-bigquery-0.0.1.jar pulsar-toolset-0:/pulsar/myjars/examples.jar -n demo
kubectl cp jars/pulsar-io-cloud-storage-2.8.1.30.nar pulsar-toolset-0:/pulsar/connectors/ -n demo
kubectl cp src/main/resources/config/ pulsar-toolset-0:/pulsar/ -n demo