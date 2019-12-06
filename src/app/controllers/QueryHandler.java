package app.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class QueryHandler {
    public static String getQuery(InputStream inputStream) throws IOException {
        byte[] message = new byte[100];
        try {
             inputStream.read(message);
        }catch (SocketException e){}
        String queryContent = new String(message, StandardCharsets.UTF_8).trim();
        return queryContent;
    }
}
