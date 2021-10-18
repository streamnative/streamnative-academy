### Deploy Pulsar Cluster

```shell
./scripts/pulsar/prepare_helm_release.sh \
    -n pulsar \
    -k pulsar \
    -c
```

```shell
helm install \
    --values k8s/values.yaml \
    --set initialize=true \
    --namespace pulsar \
    pulsar streamnative/pulsar
```

```shell
helm upgrade pulsar apache/pulsar --values examples/pulsar/values-minikube.yaml -n pulsar
```

### Deploy Function Mesh

```shell
kubectl create ns function-mesh
```

```shell
helm install function-mesh --values charts/function-mesh-operator/values.yaml charts/function-mesh-operator --namespace=function-mesh
```