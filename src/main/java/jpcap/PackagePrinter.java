package jpcap;

import jpcap.api.Capture;
import jpcap.packet.*;

public class PackagePrinter implements PacketReceiver {

    @Override
    public void receivePacket(Packet p) {
//        if(p instanceof UDPPacket || p instanceof ICMPPacket ||
//            p instanceof ARPPacket){
//
//        }else if(p instanceof TCPPacket){
//            if(((TCPPacket) p).dst_port == 20 || ((TCPPacket) p).dst_port == 21){
//
//                System.out.println("FTP Protocol: { \n" +
//                        "Dest Port: " + ((TCPPacket) p).dst_port + "\n" +
//                        "Dest Ip: " + ((TCPPacket) p).dst_ip);
//
//                System.out.println(p.toString());
//            }
//          //  System.out.println(p.toString());
//        }
        if(JpcapCaptor.interpretFTP){
            if (p instanceof TCPPacket) {
                if (((TCPPacket) p).src_port == 20 || ((TCPPacket) p).dst_port == 20 || ((TCPPacket) p).src_port == 21 || ((TCPPacket) p).dst_port == 21) {
                    FTPPacket p_ftp = new FTPPacket((TCPPacket) p,0);
                    System.out.println("\n" + p_ftp.toString() + "\n");
                }
            } else if(!JpcapCaptor.showOnlyFTP){
                System.out.println("\n" + p.toString() + "\n");
            }
        } else{
            System.out.println("\n" + p.toString() + "\n");
        }
    }
}
