package jpcap;

import jpcap.api.Capture;
import jpcap.packet.*;

public class PackagePrinter implements PacketReceiver {

    @Override
    public void receivePacket(Packet p) {
        if(Params.interpretFTP){
            if (p instanceof TCPPacket) {
                if (((TCPPacket) p).src_port == 20 || ((TCPPacket) p).dst_port == 20 || ((TCPPacket) p).src_port == 21 || ((TCPPacket) p).dst_port == 21) {
                    if(p.data.length > 0) {
                        FTPPacket p_ftp = new FTPPacket((TCPPacket) p);
                        System.out.println(p_ftp.toString() + "\n");
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
