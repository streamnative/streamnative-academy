mvn clean package
echo "Copying function configuration files."
kubectl cp src/main/resources/s3_source_config.yaml pulsar-toolset-0:/pulsar/ -n pulsar
echo "Creating the jar folder."
kubectl exec -it pulsar-toolset-0 -n pulsar -- mkdir -p myjars
echo "Uploading the jar file."
kubectl cp target/pulsar-io-s3-simple-source-0.0.1.jar pulsar-toolset-0:/pulsar/myjars/pulsar-io-s3-simple-source-0.0.1.jar -n pulsar
echo "Setup completed successfully."