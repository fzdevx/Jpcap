package jpcap;

import jpcap.packet.*;

public class PackagePrinter implements PacketReceiver {

    @Override
    public void receivePacket(Packet p) {
        if(p instanceof UDPPacket || p instanceof ICMPPacket ||
            p instanceof ARPPacket){

        }else if(p instanceof FTPPacket){
            if(((FTPPacket) p).dst_port == 20 || ((FTPPacket) p).dst_port == 21){

                System.out.println("FTP Protocol: { \n" +
                        "Dest Port: " + ((FTPPacket) p).dst_port + "\n" +
                        "Dest Ip: " + ((FTPPacket) p).dst_ip);

                System.out.println(((FTPPacket) p).toString());
            }
          //  System.out.println(p.toString());
        }
    }
}
