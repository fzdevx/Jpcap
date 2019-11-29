package jpcap.util;

import java.net.InetAddress;

public class TcpConnection {

    private InetAddress clientIP;
    private InetAddress serverIP;
    private int clientPort;
    private int serverPort;
    private boolean isReverseConnection = false;

    public TcpConnection(InetAddress clientIp, InetAddress serverIp, int clientPort, int serverPort) {
        if (clientPort < serverPort) {
            this.clientIP = clientIp;
            this.serverIP = serverIp;
            this.clientPort = clientPort;
            this.serverPort = serverPort;
        } else {
            this.serverIP = clientIp;
            this.clientIP = serverIp;
            this.serverPort = clientPort;
            this.clientPort = serverPort;
            isReverseConnection = true;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        TcpConnection o = (TcpConnection) obj;
        if (!clientIP.equals(o.clientIP)) {
            return false;
        }
        if (clientPort != o.clientPort) {
            return false;
        }
        if (!serverIP.equals(o.serverIP)) {
            return false;
        }
        if (serverPort != o.serverPort) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return clientIP.hashCode() ^ clientPort ^ serverIP.hashCode() ^ serverPort;
    }


    public InetAddress getClientIp() {
        return isReverseConnection ? serverIP : clientIP;
    }


    public InetAddress getServerIp() {
        return isReverseConnection ? clientIP : serverIP;
    }


    public int getClientPort() {
        return isReverseConnection ? serverPort : clientPort;
    }


    public int getServerPort() {
        return isReverseConnection ? clientPort : serverPort;
    }

    @Override
    public String toString() {
        return String.format("%s:%d -> %s:%d", getClientIp().getHostAddress(), getClientPort(), getServerIp()
                .getHostAddress(), getServerPort());
    }
}
