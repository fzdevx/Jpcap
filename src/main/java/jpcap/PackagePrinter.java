package jpcap;

import jpcap.packet.*;
import jpcap.util.TcpConnection;
import jpcap.util.TcpProtocolRegister;

public class PackagePrinter implements PacketReceiver {

    @Override
    public void receivePacket(Packet p) {
        if(Params.interpretFTP){
            if (p instanceof TCPPacket) {
                if (((TCPPacket) p).src_port == 20 || ((TCPPacket) p).dst_port == 20 || ((TCPPacket) p).src_port == 21 || ((TCPPacket) p).dst_port == 21) {
                    if(p.data.length > 0) {
                        FTPPacket p_ftp = new FTPPacket((TCPPacket) p);

                        TcpConnection tcpConnection = new TcpConnection(((TCPPacket) p).src_ip,
                                ((TCPPacket) p).dst_ip, ((TCPPacket) p).src_port, ((TCPPacket) p).dst_port);

                        TcpProtocolRegister tcpProtocolRegister = new TcpProtocolRegister();
                        tcpProtocolRegister.register(tcpConnection, TcpProtocolRegister.Protocol.FTP);

                        System.out.println(tcpConnection.toString());

                        //System.out.println(p_ftp.toString() + "\n");


                    } else
                        System.out.println((p.toString() + "\n"));
                } else if(!Params.showOnlyFTP){
                        System.out.println((p.toString() + "\n"));
                }
            } else if(!Params.showOnlyFTP){
                System.out.println((p.toString() + "\n"));
            }
        } else{
            System.out.println(p.toString() + "\n");
        }
    }

}
