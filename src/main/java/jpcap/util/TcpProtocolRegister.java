package jpcap.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TcpProtocolRegister {

    public enum Protocol {
        IPv4, IPv6, ARP, TCP, UDP, FTP, HTTP, SSH, TELNET
    }

    private ConcurrentMap<Integer, Protocol> tcpMap;
    private ConcurrentMap<TcpConnection, Protocol> temporaryTcpMap;

    public TcpProtocolRegister() {
        tcpMap = new ConcurrentHashMap<>();
        temporaryTcpMap = new ConcurrentHashMap<>();

        tcpMap.put(80, Protocol.HTTP);
        tcpMap.put(8080, Protocol.HTTP);
        tcpMap.put(21, Protocol.FTP);
        tcpMap.put(22, Protocol.SSH);
        tcpMap.put(23, Protocol.TELNET);
        
    }


    public void register(TcpConnection sockAddr, Protocol protocol) {
        temporaryTcpMap.put(sockAddr, protocol);
    }

    public void unregister(TcpConnection sockAddr) {
        if (temporaryTcpMap.containsKey(sockAddr)) {
            temporaryTcpMap.remove(sockAddr);
        }
    }
}
