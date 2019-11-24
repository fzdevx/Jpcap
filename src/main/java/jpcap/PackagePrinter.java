package jpcap;

import jpcap.packet.ICMPPacket;
import jpcap.packet.Packet;

public class PackagePrinter implements PacketReceiver {

    @Override
    public void receivePacket(Packet p) {
        if (p instanceof ICMPPacket) {
            System.out.println("ICMP");
            System.out.println(((ICMPPacket) p).dst_ip.isSiteLocalAddress());
        }
        System.out.println(p.toString());
    }
}
