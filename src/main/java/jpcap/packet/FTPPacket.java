package jpcap.packet;

import jpcap.JpcapCaptor;

import java.net.InetAddress;
import java.util.Arrays;

public class FTPPacket extends TCPPacket {
    private String data;
    private InetAddress src_ip, dst_ip;

    public FTPPacket(int src_port, int dst_port, long sequence, long ack_num, boolean urg, boolean ack, boolean psh, boolean rst, boolean syn, boolean fin, boolean rsv1, boolean rsv2, int window, int urgent) {
        super(src_port, dst_port, sequence, ack_num, urg, ack, psh, rst, syn, fin, rsv1, rsv2, window, urgent);
    }

    public FTPPacket(TCPPacket p, int urgent){
        super(p.src_port, p.dst_port, p.sequence, p.ack_num, p.urg, p.ack, p.psh, p.rst, p.syn, p.fin, p.rsv1, p.rsv2, p.window, urgent);
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
        String s = super.toString();
        return s + "\nFTP: \ndata=" + data + "\n";

//        return "FTPPacket{" +
//                "src_ip=" + src_ip + "\n" +
//                "dst_ip=" + dst_ip + "\n" +
//                "src_ip=" + src_ip + "\n" +
//                "dst_ip=" + dst_ip + "\n" +
//                "src_port=" + src_port + "\n" +
//                "dst_port=" + dst_port + "\n" +
//                "sequence=" + sequence + "\n" +
//                "ack_num=" + ack_num + "\n" +
//                "urg=" + urg + "\n" +
//                "ack=" + ack + "\n" +
//                "psh=" + psh + "\n" +
//                "rst=" + rst + "\n" +
//                "syn=" + syn + "\n" +
//                "fin=" + fin + "\n" +
//                "rsv1=" + rsv1 + "\n" +
//                "rsv2=" + rsv2 + "\n" +
//                "window=" + window + "\n" +
//                "urgent_pointer=" + urgent_pointer + "\n" +
//                "option=" + Arrays.toString(option) + "\n" +
//                "version=" + version + "\n" +
//                "priority=" + priority + "\n" +
//                "d_flag=" + d_flag + "\n" +
//                "t_flag=" + t_flag + "\n" +
//                "r_flag=" + r_flag + "\n" +
//                "rsv_tos=" + rsv_tos + "\n" +
//                "length=" + length + "\n" +
//                "rsv_frag=" + rsv_frag + "\n" +
//                "dont_frag=" + dont_frag + "\n" +
//                "more_frag=" + more_frag + "\n" +
//                "offset=" + offset + "\n" +
//                "hop_limit=" + hop_limit + "\n" +
//                "protocol=" + protocol + "\n" +
//                "ident=" + ident + "\n" +
//                "flow_label=" + flow_label + "\n" +
//                "option=" + Arrays.toString(option) + "\n" +
//                "options=" + options + "\n" +
//                "sec=" + sec + "\n" +
//                "usec=" + usec + "\n" +
//                "caplen=" + caplen + "\n" +
//                "len=" + len + "\n" +
//                "datalink=" + datalink + "\n" +
//                "header=" + header + "\n" +
//                "data=" + data + "\n" +
//                '}';

//        return "FTPPacket{" +
//                "data=" + data + "\n" + "}\n" +
//                "TCPPacket{" +
//                "" +
//                "src_ip=" + src_ip + "\n" +
//                "dst_ip=" + dst_ip + "\n" +
//                "src_ip=" + src_ip + "\n" +
//                "dst_ip=" + dst_ip + "\n" +
//                "src_port=" + src_port + "\n" +
//                "dst_port=" + dst_port + "\n" +
//                "sequence=" + sequence + "\n" +
//                "ack_num=" + ack_num + "\n" +
//                "urg=" + urg + "\n" +
//                "ack=" + ack + "\n" +
//                "psh=" + psh + "\n" +
//                "rst=" + rst + "\n" +
//                "syn=" + syn + "\n" +
//                "fin=" + fin + "\n" +
//                "rsv1=" + rsv1 + "\n" +
//                "rsv2=" + rsv2 + "\n" +
//                "window=" + window + "\n" +
//                "urgent_pointer=" + urgent_pointer + "\n" +
//                "option=" + Arrays.toString(option) + "\n" +
//                "version=" + version + "\n" +
//                "priority=" + priority + "\n" +
//                "d_flag=" + d_flag + "\n" +
//                "t_flag=" + t_flag + "\n" +
//                "r_flag=" + r_flag + "\n" +
//                "rsv_tos=" + rsv_tos + "\n" +
//                "length=" + length + "\n" +
//                "rsv_frag=" + rsv_frag + "\n" +
//                "dont_frag=" + dont_frag + "\n" +
//                "more_frag=" + more_frag + "\n" +
//                "offset=" + offset + "\n" +
//                "hop_limit=" + hop_limit + "\n" +
//                "protocol=" + protocol + "\n" +
//                "ident=" + ident + "\n" +
//                "flow_label=" + flow_label + "\n" +
//                "option=" + Arrays.toString(option) + "\n" +
//                "options=" + options + "\n" +
//                "sec=" + sec + "\n" +
//                "usec=" + usec + "\n" +
//                "caplen=" + caplen + "\n" +
//                "len=" + len + "\n" +
//                "datalink=" + datalink + "\n" +
//                "header=" + header + "\n" +
//
//                '}';
    }
}
