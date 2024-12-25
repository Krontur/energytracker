package com.energytracker.datacollector.infrastructure.adapter.outbound.connection;

import com.energytracker.datacollector.application.port.outbound.ConfigLoaderPort;
import com.energytracker.datacollector.application.port.outbound.ConnectionPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Repository
public class TCPConnection implements ConnectionPort {

    private Socket socket;
    PrintWriter out;
    BufferedReader in;
    private final String host;
    private final int port;

    public TCPConnection(ConfigLoaderPort configLoaderPort) {
        this.host = configLoaderPort.getProperty("tcp.connection.host");
        this.port = configLoaderPort.getIntProperty("tcp.connection.port");
        this.socket = null;
    }

    @Override
    public void openConnection() {
        try {
            log.info("Connecting to server {}:{}", host, port);
            this.socket = new Socket(host, port);
            socket.setSoTimeout(1000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (NumberFormatException | UnknownHostException e) {
            log.error("Error in the server address: {}", e.getMessage());
        } catch (SocketException e) {
            log.error("Error opening the socket: {}", e.getMessage());
        } catch (IOException e) {
            log.error("Error charging the config file or opening de socket flow: {}", e.getMessage());
        }

        if (socket != null && socket.isConnected()) {
            log.info("Connection established with the server.");
        } else {
            log.error("Connection could not be established.");
        }
    }

    @Override
    public void closeConnection() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            log.error("Error al cerrar la conexión: {}", e.getMessage());
        }
    }

    @Override
    public void sendCommand(String command) {
        log.info("Enviando command por TCP: {}", command);
        try {
            out.println(command);
            log.info("Command enviado con éxito.");
        } catch (Exception e) {
            log.error("Error al enviar el comando: {}", e.getMessage());
        }
    }

    @Override
    public String receiveData() throws IOException {
        String regex = "^<.*>$";
        Pattern pattern = Pattern.compile(regex);

        StringBuilder receivedData = new StringBuilder();
        String line;

        try {
            while ((line = in.readLine()) != null) {
                receivedData.append(line).append(";");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    break;
                }
            }
        } catch (SocketTimeoutException e) {
            log.warn("Exceeded Timeout: {}", e.getMessage());
        } catch (IOException e) {
            log.error("Error receiving data: {}", e.getMessage());
            throw e;
        }

        return receivedData.toString();
    }

    @Override
    public boolean isOpened() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }
}
