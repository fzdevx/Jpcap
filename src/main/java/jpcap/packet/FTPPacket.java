package jpcap.packet;

import java.util.Arrays;

public class FTPPacket extends TCPPacket {

    /**
     * Creates a TCP packet.
     *
     * @param src_port Source port number
     * @param dst_port Destination port number
     * @param sequence sequence number
     * @param ack_num  ACK number
     * @param urg      URG flag
     * @param ack      ACK flag
     * @param psh      PSH flag
     * @param rst      RST flag
     * @param syn      SYN flag
     * @param fin      FIN flag
     * @param rsv1     RSV1 flag
     * @param rsv2     RSV2 flag
     * @param window   window size
     * @param urgent   urgent pointer
     */
    public FTPPacket(int src_port, int dst_port, long sequence, long ack_num, boolean urg, boolean ack, boolean psh, boolean rst, boolean syn, boolean fin, boolean rsv1, boolean rsv2, int window, int urgent) {
        super(src_port, dst_port, sequence, ack_num, urg, ack, psh, rst, syn, fin, rsv1, rsv2, window, urgent);
    }

    @Override
    public String toString() {
        return "FTPPacket{" +
                "src_port=" + src_port + "\n" +
                "dst_port=" + dst_port + "\n" +
                "sequence=" + sequence + "\n" +
                "ack_num=" + ack_num + "\n" +
                "urg=" + urg + "\n" +
                "ack=" + ack + "\n" +
                "psh=" + psh + "\n" +
                "rst=" + rst + "\n" +
                "syn=" + syn + "\n" +
                "fin=" + fin + "\n" +
                "rsv1=" + rsv1 + "\n" +
                "rsv2=" + rsv2 + "\n" +
                "window=" + window + "\n" +
                "urgent_pointer=" + urgent_pointer + "\n" +
                "option=" + Arrays.toString(option) + "\n" +
                "version=" + version + "\n" +
                "priority=" + priority + "\n" +
                "d_flag=" + d_flag + "\n" +
                "t_flag=" + t_flag + "\n" +
                "r_flag=" + r_flag + "\n" +
                "rsv_tos=" + rsv_tos + "\n" +
                "length=" + length + "\n" +
                "rsv_frag=" + rsv_frag + "\n" +
                "dont_frag=" + dont_frag + "\n" +
                "more_frag=" + more_frag + "\n" +
                "offset=" + offset + "\n" +
                "hop_limit=" + hop_limit + "\n" +
                "protocol=" + protocol + "\n" +
                "ident=" + ident + "\n" +
                "flow_label=" + flow_label + "\n" +
                "src_ip=" + src_ip + "\n" +
                "dst_ip=" + dst_ip + "\n" +
                "option=" + Arrays.toString(option) + "\n" +
                "options=" + options + "\n" +
                "sec=" + sec + "\n" +
                "usec=" + usec + "\n" +
                "caplen=" + caplen + "\n" +
                "len=" + len + "\n" +
                "datalink=" + datalink + "\n" +
                "header=" + Arrays.toString(header) + "\n" +
                "data=" + Arrays.toString(data) + "\n" +
                '}';
    }
}
