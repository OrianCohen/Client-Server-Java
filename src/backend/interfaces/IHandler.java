package src.backend.interfaces;

import java.io.*;

public interface IHandler {
    void handle(InputStream inClient, OutputStream outClient) throws Exception;
}