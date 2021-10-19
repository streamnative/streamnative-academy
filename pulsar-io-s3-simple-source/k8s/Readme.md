1. Download the necessary repos
```shell
git clone https://github.com/streamnative/charts.git

git clone https://github.com/streamnative/function-mesh.git
```

```shell
cd charts
```

2. Prepare the installation
```shell
./scripts/pulsar/prepare_helm_release.sh \
    -n pulsar \
    -k pulsar \
    -c
```

2. Deploy the pulsar cluster
```shell
helm install \
    --values k8s/values.yaml \
    --set initialize=true \
    --namespace pulsar \
    pulsar streamnative/pulsar
```

3. Upgrade the Cluster
```shell
helm upgrade pulsar streamnative/pulsar --values k8s/values.yaml -n pulsar
```

### Deploy Function Mesh
```shell
cd function-mesh
```

4. Create a namespace
```shell
kubectl create ns function-mesh
```

5. Deploy the function mesh operator
```shell
helm install function-mesh --values charts/function-mesh-operator/values.yaml charts/function-mesh-operator --namespace=function-mesh
```