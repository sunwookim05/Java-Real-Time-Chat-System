import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

class ChatServer {
    private val clients = mutableListOf<PrintWriter>()

    fun start() {
        val serverSocket = ServerSocket(8080)

        while (true) {
            val clientSocket = serverSocket.accept()
            val clientWriter = PrintWriter(clientSocket.getOutputStream(), true)
            clients.add(clientWriter)

            thread {
                val clientReader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                var message: String?

                while (true) {
                    message = clientReader.readLine()
                    if (message != null) {
                        broadcastMessage(message)
                    }
                }
            }
        }
    }

    private fun broadcastMessage(message: String) {
        for (client in clients) {
            client.println(message)
        }
    }
}

class ChatClient {
    fun start() {
        val serverAddress = "localhost"
        val serverPort = 8080

        val socket = Socket(serverAddress, serverPort)

        thread {
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            var message: String?

            while (true) {
                message = reader.readLine()
                if (message != null) {
                    println("새로운 메시지: $message")
                }
            }
        }

        val writer = PrintWriter(socket.getOutputStream(), true)
        val consoleReader = BufferedReader(InputStreamReader(System.`in`))
        var input: String?

        while (true) {
            input = consoleReader.readLine()
            writer.println(input)
        }
    }
}

fun main() {
    val server = ChatServer()
    thread { server.start() }

    val client = ChatClient()
    client.start()
}