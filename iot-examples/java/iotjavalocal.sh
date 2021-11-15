java -jar target/IoTProducer-1.0-jar-with-dependencies.jar --serviceUrl pulsar://localhost:6650 --topic 'iotjetsonjson' --message "`cat testrow.json`"
