mvn clean package
echo "Copying function configuration files."
kubectl cp src/main/resources/function_config/ pulsar-toolset-0:/pulsar/ -n workshop
echo "Creating the jar folder."
kubectl exec -it pulsar-toolset-0 -n workshop -- mkdir -p myjars
echo "Uploading the jar file."
kubectl cp target/microservices-webinars-0.0.1.jar pulsar-toolset-0:/pulsar/myjars/workshop.jar -n workshop
echo "Setup completed successfully."