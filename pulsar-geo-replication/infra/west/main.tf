terraform {
  required_providers {
    pulsar = {
      version = "1.0.0"
      source = "registry.terraform.io/apache/pulsar"
    }
  }
}

provider "pulsar" {
  web_service_url = "http://localhost:8080"
}

#resource "pulsar_cluster" "pulsar-west" {
#  cluster = "pulsar-west"
#
#  cluster_data {
#    web_service_url    = "http://pulsar-west-broker.pulsar.svc.cluster.local:8080/"
#    broker_service_url = "pulsar://pulsar-west-broker.pulsar.svc.cluster.local:6650/"
##    peer_clusters      = ["pulsar-central", "pulsar-east"]
#  }
#}

resource "pulsar_cluster" "pulsar-east" {
  cluster = "pulsar-east"

  cluster_data {
    web_service_url    = "http://pulsar-east-broker.pulsar.svc.cluster.local:8080/"
    broker_service_url = "pulsar://pulsar-east-broker.pulsar.svc.cluster.local:6650/"
#    peer_clusters      = ["pulsar-central", "pulsar-west"]
  }
}

resource "pulsar_cluster" "pulsar-central" {
  cluster = "pulsar-central"

  cluster_data {
    web_service_url    = "http://pulsar-central-broker.pulsar.svc.cluster.local:8080/"
    broker_service_url = "pulsar://pulsar-central-broker.pulsar.svc.cluster.local:6650/"
#    peer_clusters      = ["pulsar-west", "pulsar-east"]
  }
}

resource "pulsar_tenant" "test_tenant" {
  tenant           = "testt"

  allowed_clusters = ["pulsar-west", "pulsar-central", "pulsar-east"]
#  admin_roles      = ["admin"]
}

resource "pulsar_namespace" "testns" {
  tenant    = pulsar_tenant.test_tenant.tenant
  namespace = "testns"

  namespace_config {
    replication_clusters = ["pulsar-west", "pulsar-east", "pulsar-central"]
  }
}