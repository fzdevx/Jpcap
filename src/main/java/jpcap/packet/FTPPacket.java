package jpcap.packet;

import jpcap.JpcapCaptor;

import java.net.InetAddress;
import java.util.Arrays;

public class FTPPacket extends TCPPacket {
    private String data;
    private InetAddress src_ip, dst_ip;

    public FTPPacket(TCPPacket p){
        super(p.src_port, p.dst_port, p.sequence, p.ack_num, p.urg, p.ack, p.psh, p.rst, p.syn, p.fin, p.rsv1, p.rsv2, p.window, 0);
        this.data = byteArrayToString(p.data);
        this.src_ip = p.src_ip;
        this.dst_ip = p.dst_ip;
        this.sequence = p.sequence;
        this.rsv1 = p.rsv1;
        this.rsv2 = p.rsv2;
        this.window = p.window;
        this.datalink = p.datalink;
    }

    private String byteArrayToString(byte[] data){
        String strData = new String(data);
        return strData;
    }

    @Override
    public String toString() {
        return  "FTP: " + data + "TCP " +
                src_port + " > " + dst_port + " seq(" + sequence +
                ") win(" + window + ")" + (ack ? " ack " + ack_num : "") + " " +
                (syn ? " S" : "") + (fin ? " F" : "") + (psh ? " P" : "") +
                (rst ? " R" : "") + (urg ? " U" : "") + "\nIP: " + src_ip + "->" + dst_ip
                + " protocol(" + protocol + ") priority(" + priority
                + ") flowlabel(" + flow_label + ") hop(" + hop_limit + ")" + sec + ":" + usec;
    }
}
