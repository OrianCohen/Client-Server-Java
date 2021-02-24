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
    private final int mPort;
    private volatile boolean mStopServer;
    private final ThreadPoolExecutor mExecutor;
    private final IHandler mRequestConcreteIHandler;
    private final Object mLock = new Object();

    public TcpServer(int port, MainTasksIHandler concreteIHandlerStrategy) {
        this.mPort = port;
        mStopServer = false;
        mExecutor = new ThreadPoolExecutor(
                3, 5, 10,
                TimeUnit.SECONDS, new PriorityBlockingQueue<>());
        this.mRequestConcreteIHandler = concreteIHandlerStrategy;
    }

    public void startServer() {
        Runnable mainLogic = () -> {
            try {
                ServerSocket server = new ServerSocket(mPort);
                server.setSoTimeout(1000);
                while (!mStopServer) {
                    try {
                        Socket request = server.accept();
                        System.out.println("server::client");
                        Runnable runnable = () -> {
                            try {
                                System.out.println("server::handle");
                                mRequestConcreteIHandler.handle(request.getInputStream(),
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
                        //wrap with tread safe
                        synchronized (mLock) {
                            mExecutor.execute(runnable);
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
        if (!mStopServer) {
            mStopServer = true;
            mExecutor.shutdown();
        }
    }

    public static void main(String[] args) {
        new TcpServer(8010, new MainTasksIHandler()).startServer();
    }

}