import { Client } from '@stomp/stompjs'
import SockJS from 'sockjs-client'

class WebSocketService {
    constructor() {
        this.stompClient = null
        this.connected = false
        this.subscriptions = {}
    }

    connect(gameId, onMessageReceived) {
        const socket = new SockJS('http://localhost:8080/ws')

        this.stompClient = new Client({
            webSocketFactory: () => socket,
            connectHeaders: {
                Authorization: `Bearer ${localStorage.getItem('accessToken')}`
            },
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000,
            onConnect: () => {
                this.connected = true
                this.subscribe(gameId, onMessageReceived)
            },
            onStompError: (frame) => {
                console.error('WebSocket error:', frame.headers.message)
            }
        })

        this.stompClient.activate()
    }

    subscribe(gameId, callback) {
        if (this.stompClient && this.connected) {
            if (this.subscriptions[gameId]) {
                this.subscriptions[gameId].unsubscribe() // Отписываемся, если уже подписаны
            }

            const subscription = this.stompClient.subscribe(
                `/topic/game/${gameId}`,
                (message) => {
                    callback(JSON.parse(message.body))
                },
                { Authorization: `Bearer ${localStorage.getItem('accessToken')}` }
            )

            this.subscriptions[gameId] = subscription
        }
    }

    disconnect() {
        if (this.stompClient) {
            Object.values(this.subscriptions).forEach(sub => sub.unsubscribe())
            this.stompClient.deactivate()
            this.connected = false
            this.subscriptions = {}
        }
    }
}

export default new WebSocketService()