package src.backend;

import src.backend.interfaces.IHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.*;

/**
 * This class represents a multi-threaded server
 */
public class TcpServer {
    private final int port;
    private volatile boolean stopServer;
    private final ThreadPoolExecutor executor;
    private final IHandler requestConcreteIHandler;
    private final Object lock = new Object();

    public TcpServer(int port, MainTasksIHandler concreteIHandlerStrategy) {
        this.port = port;
        stopServer = false;
        executor = new ThreadPoolExecutor(
                3, 5, 10,
                TimeUnit.SECONDS, new PriorityBlockingQueue<>());
        this.requestConcreteIHandler = concreteIHandlerStrategy;
    }

    public void startServer() {
        Runnable mainLogic = () -> {
            try {
                ServerSocket server = new ServerSocket(port);
                server.setSoTimeout(1000);
                while (!stopServer) {
                    try {
                        Socket request = server.accept();
                        System.out.println("server::client");
                        Runnable runnable = () -> {
                            try {
                                System.out.println("server::handle");
                                requestConcreteIHandler.handle(request.getInputStream(),
                                        request.getOutputStream());
                                // Close all streams
                                request.getInputStream().close();
                                request.getOutputStream().close();
                                request.close();
                            } catch (Exception e) {
                                System.out.println("server::" + e.getMessage());
                                System.err.println(e.getMessage());
                            }
                        };
                        synchronized (lock) {
                            executor.execute(runnable);
                        }
                    } catch (SocketTimeoutException ignored) {
                    }
                }
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        new Thread(mainLogic).start();
    }

    public void stop() {
        if (!stopServer) {
            stopServer = true;
            executor.shutdown();
        }
    }

    public static void main(String[] args) {
        new TcpServer(8010, new MainTasksIHandler()).startServer();
    }

}