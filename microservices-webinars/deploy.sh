echo "Deploying the Validation function."
kubectl exec -it pulsar-toolset-0 -n workshop -- pulsar-admin functions create \
 --function-config-file function_config/validation_func_config.yaml \
 --jar myjars/workshop.jar


echo "Deploying the Geo Encoder Service."
kubectl exec -it pulsar-toolset-0 -n workshop -- pulsar-admin functions create \
  --function-config-file function_config/geo_func_config.yaml \
  --jar myjars/workshop.jar

echo "Deploying the Payments Service."
kubectl exec -it pulsar-toolset-0 -n workshop -- pulsar-admin functions create \
  --function-config-file function_config/payments_func_config.yaml \
  --jar myjars/workshop.jar

echo "Deploying the Restaurants Service."
kubectl exec -it pulsar-toolset-0 -n workshop -- pulsar-admin functions create \
  --function-config-file function_config/restaurants_func_config.yaml \
  --jar myjars/workshop.jar

echo "Deploying the Aggregation Service."
kubectl exec -it pulsar-toolset-0 -n workshop -- pulsar-admin functions create \
  --function-config-file function_config/aggregation_func_config.yaml \
  --jar myjars/workshop.jar

echo "Services deployed successfully."