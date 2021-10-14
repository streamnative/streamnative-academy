echo "Deploying Data Generator: Deploying S3 Connector."

kubectl exec -it pulsar-toolset-0 -n pulsar -- bin/pulsar-admin source create \
  --source-config-file s3_source_config.yaml \
  --archive myjars/pulsar-io-s3-simple-source-0.0.1.jar


kubectl exec -it pulsar-toolset-0 -n pulsar -- bin/pulsar-admin source list

#pulsar-admin source delete --tenant ecommerce --namespace ingestion --name s3-file-source