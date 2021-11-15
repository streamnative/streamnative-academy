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

package io.streamnative.examples.oauth2;

import com.beust.jcommander.JCommander;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.auth.oauth2.AuthenticationFactoryOAuth2;
import org.apache.pulsar.client.impl.schema.JSONSchema;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * IoT Producer
 */
public class IoTProducer {

    private static String DEFAULT_TOPIC = "persistent://public/default/jetson-iot-json";

    /**
     * schemas https://pulsar.apache.org/docs/en/schema-understand/
     * http://pulsar.apache.org/docs/en/concepts-schema-registry/
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        JCommanderPulsar jct = new JCommanderPulsar();
        JCommander jCommander = new JCommander(jct, args);
        if (jct.help) {
            jCommander.usage();
            return;
        }

        System.out.println("serv:" + jct.serviceUrl);
        System.out.println("issuer:" + jct.issuerUrl);
        System.out.println("creds:" + jct.credentialsUrl);
        System.out.println("aud:" + jct.audience);
        System.out.println("msg:" + jct.message);
        System.out.println("topic:" + jct.topic);

        PulsarClient client = null;

        if ( jct.issuerUrl != null && jct.issuerUrl.trim().length() > 0 ) {
            try {
                client = PulsarClient.builder()
                        .serviceUrl(jct.serviceUrl.toString())
                        .authentication(
                                AuthenticationFactoryOAuth2.clientCredentials(new URL(jct.issuerUrl.toString()),
                                        new URL(jct.credentialsUrl.toString()), jct.audience.toString()))
                        .build();
            } catch (PulsarClientException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                client = PulsarClient.builder().serviceUrl(jct.serviceUrl.toString()).build();
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }
        }

        UUID uuidKey = UUID.randomUUID();
        String pulsarKey = uuidKey.toString();
        String OS = System.getProperty("os.name").toLowerCase();
        String message = "" + jct.message;
        IoTMessage iotMessage = parseMessage("" + jct.message);
        String topic = DEFAULT_TOPIC;
        if ( jct.topic != null && jct.topic.trim().length()>0) {
            topic = jct.topic.trim();
        }
        ProducerBuilder<IoTMessage> producerBuilder = client.newProducer(JSONSchema.of(IoTMessage.class))
                .topic(topic)
                .producerName("jetson").
                sendTimeout(5, TimeUnit.SECONDS);

        Producer<IoTMessage> producer = producerBuilder.create();

        MessageId msgID = producer.newMessage()
                .key(iotMessage.getUuid())
                .value(iotMessage)
                .property("device", OS)
                .property("uuid2", pulsarKey)
                .send();

        System.out.println("Publish message ID " + msgID + " OS:" + OS + " Key:" + pulsarKey);

        producer.close();
        client.close();
        producer = null;
        client = null;
    }

    /**
     * @param  message String of message
     * @return IoTMessage
     */
    private static IoTMessage parseMessage(String message) {

        IoTMessage iotMessage = null;

        try {
            if ( message != null && message.trim().length() > 0) {
                ObjectMapper mapper = new ObjectMapper();
                iotMessage = mapper.readValue(message, IoTMessage.class);
                mapper = null;
            }
        }
        catch(Throwable t) {
            t.printStackTrace();
        }

        if (iotMessage == null) {
            iotMessage = new IoTMessage();
        }
        return iotMessage;
    }
}
