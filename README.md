# orders-axon

1. Run AxonServer
```
docker run --name my-axon-server -it -p 8024:8024 -p 8124:8124 axoniq/axonserver:4.2.4-jdk11
```
2. Start application
3. Place an order
```
curl -X POST -H "Content-Type: application/json"-d '{"name": "Deluxe CHair"}' localhost:8080/ship
```
4. Get all orders
```
curl localhost:8080/orders
```
