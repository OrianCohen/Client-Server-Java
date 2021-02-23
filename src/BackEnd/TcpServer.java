package src.BackEnd;

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
    private ThreadPoolExecutor executor;
    private IHandler requestConcreteIHandler;

    public TcpServer(int port) {
        this.port = port;
        stopServer = false;
        executor = null;
    }

    public void run(IHandler concreteIHandlerStrategy) {
        this.requestConcreteIHandler = concreteIHandlerStrategy;

        Runnable mainLogic = () -> {
            try {
                executor = new ThreadPoolExecutor(
                        3, 5, 10,
                        TimeUnit.SECONDS, new PriorityBlockingQueue<>());
                ServerSocket server = new ServerSocket(port);
                server.setSoTimeout(1000);
                while (!stopServer) {
                    try {
                        Socket request = server.accept(); // Wrap with a separate thread
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
                                System.out.println("server::"+e.getMessage());
                                System.err.println(e.getMessage());
                            }
                        };
                        executor.execute(runnable);
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
            if (executor != null) {
                executor.shutdown();
            }
        }
    }

    public static void main(String[] args) {
        TcpServer tcpServer =new TcpServer(8010);
        tcpServer.run(new MainTasksIHandler());
    }

}