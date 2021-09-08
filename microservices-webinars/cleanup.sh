echo "Deleting Functions and Sources"
kubectl exec -it pulsar-toolset-0 -n workshop -- pulsar-admin functions delete --name order-validation-func
kubectl exec -it pulsar-toolset-0 -n workshop -- pulsar-admin functions delete --name geo-encoder-func
kubectl exec -it pulsar-toolset-0 -n workshop -- pulsar-admin functions delete --name payments-func
kubectl exec -it pulsar-toolset-0 -n workshop -- pulsar-admin functions delete --name restaurants-func
kubectl exec -it pulsar-toolset-0 -n workshop -- pulsar-admin functions delete --name order-aggregation-func
kubectl exec -it pulsar-toolset-0 -n workshop -- pulsar-admin source delete --name food-orders-source
