Geo-Replication Demo
--------------------
The purpose of this repo is to demonstrate how you can leverage Pulsar's Geo-Replication in order to design and implement disaster recovery solutions.
In this demo we will create a full-mesh replication between 3 different Pulsar Clusters under one 
Global Zookeeper Configuration Store.

### Deploy 3 Pulsar Clusters with one Global Zookeeper
![Alt text](../images/geo/geo1.png "Geo1")

### Create Producers and Consumers on the clusters
![Alt text](../images/geo/geo2.png "Geo2")

### Simulate a Cluster Failure
![Alt text](../images/geo/geo3.png "Geo3")

### Resume on the Failover Cluster
![Alt text](../images/geo/geo4.png "Geo4")

1. Create the namespaces
```shell
kubectl create ns global
kubectl create ns central
kubectl create ns east
kubectl create ns west
```

2. Install the k8s operator
```shell
helm upgrade --install pulsar-operator streamnative/pulsar-operator -n default
```

3. Deploy a global zookeeper - global configuration store
```shell
helm install -f k8s/values-global.yaml global-zk streamnative/sn-platform --set initialize=true -n global
```

4. Get a terminal in the zookeeper pod 
```shell
kubectl exec -it global-zk-sn-platform-zookeeper-0 -n global -- bash
pulsar zookeeper-shell
```

5. Deploy the pulsar-west edge cluster
```shell
helm install -f k8s/values-sn.yaml pulsar-west streamnative/sn-platform --set initialize=true -n west
```

6. Deploy the pulsar-east edge cluster
```shell
helm install -f k8s/values-sn.yaml pulsar-east streamnative/sn-platform --set initialize=true -n east
```

7. Deploy the pulsar-central aggregator cluster
```shell
helm install -f k8s/values-sn.yaml pulsar-central streamnative/sn-platform --set initialize=true -n central
```

8. Get a shell in each cluster's toolset pod
```shell
kubectl exec -it pulsar-east-sn-platform-toolset-0 -n east -- bash
kubectl exec -it pulsar-west-sn-platform-toolset-0 -n west -- bash
kubectl exec -it pulsar-central-sn-platform-toolset-0 -n central -- bash
```

9. Create a tenant and namespace on one cluster e.g west and verify it's visible on the others as well
```shell
bin/pulsar-admin tenants create testt --allowed-clusters pulsar-west-sn-platform,pulsar-east-sn-platform,pulsar-central-sn-platform
bin/pulsar-admin namespaces create testt/testns --clusters pulsar-west-sn-platform,pulsar-east-sn-platform,pulsar-central-sn-platform

bin/pulsar-admin tenants list
bin/pulsar-admin namespaces testt
```

10. Expose the services so we can access the clusters from our local environment
```shell
kubectl port-forward service/pulsar-west-sn-platform-proxy-headless 8080:8080 6650:6650 -n west
kubectl port-forward service/pulsar-east-sn-platform-proxy-headless 8081:8080 6651:6650 -n east
kubectl port-forward service/pulsar-central-sn-platform-proxy-headless 8082:8080 6652:6650 -n central
```