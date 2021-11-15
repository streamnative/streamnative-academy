import requests, uuid, websocket, base64, json

uuid2 = uuid.uuid4()
row = {}
row['host'] = 'nvidia-desktop'
ws = websocket.create_connection( 'ws://server:8080/ws/v2/producer/persistent/public/default/energy')
message = str(json.dumps(row) )
message_bytes = message.encode('ascii')
base64_bytes = base64.b64encode(message_bytes)
base64_message = base64_bytes.decode('ascii')
ws.send(json.dumps({ 'payload' : base64_message, 'properties': { 'device' : 'jetson2gb', 'protocol' : 'websockets' },'key': str(uuid2), 'context' : 5 }))
response =  json.loads(ws.recv())
if response['result'] == 'ok':
            print ('Message published successfully')
else:
            print ('Failed to publish message:', response)
ws.close()
