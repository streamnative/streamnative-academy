// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package main

import (
        "context"
        "fmt"
        "log"

        "github.com/apache/pulsar-client-go/pulsar"
        "github.com/streamnative/pulsar-examples/cloud/go/ccloud"
	"github.com/hpcloud/tail"
)

// customized by Tim Spann
// 2021 August 04
func main() {
	client := ccloud.CreateClient()

	producer, err := client.CreateProducer(pulsar.ProducerOptions{
		Topic: "jetson-iot",
	})
	if err != nil {
		log.Fatal(err)
	}
	defer producer.Close()

	t, err := tail.TailFile("/home/nvidia/nvme/logs/demo1.log", tail.Config{Follow:true})
        for line := range t.Lines {
		if msgId, err := producer.Send(context.Background(), &pulsar.ProducerMessage{
			Payload: []byte(line.Text),
		}); err != nil {
			log.Fatal(err)
		} else {
			fmt.Printf("jetson:Published message: %v-%s \n", msgId,line.Text)
		}
	}
}
