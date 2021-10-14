1. Start Minikube
```shell
minikube start --cpus 4 --memory "7g"
```

```shell
./scripts/pulsar/prepare_helm_release.sh \
    -n pulsar \
    -k pulsar \
    -c
```

```shell
helm install \
    --values examples/pulsar/values-minikube.yaml \
    --set initialize=true \
    --namespace pulsar \
    pulsar apache/pulsar
```

```shell
helm upgrade pulsar apache/pulsar --values examples/pulsar/values-minikube.yaml -n pulsar
```