package jpcap.packet;

import jpcap.util.HttpFieldsHelper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class HttpPacket extends TCPPacket {
    private String data;
    private String method;
    private String path;
    private String httpVersion;

    private String userAgent;
    private String host;
    private String accept;
    private String acceptLanguage;
    private String acceptEncoding;
    private String contentLength;
    private String connection;
    private String contentType;
    private String date;
    private String from;

    private String lastModified;
    private String server;
    private String acceptRanges;
    private String cacheControl;
    private String warning;
    private String referer;
    private String cookie;

    private String authorization;
    private String proxyAuthorization;

    private boolean response= false;
    private static String content;

    public HttpPacket(TCPPacket p) {
        super(p.src_port, p.dst_port, p.sequence, p.ack_num, p.urg, p.ack, p.psh, p.rst, p.syn, p.fin, p.rsv1, p.rsv2, p.window, 0);
        this.data = byteArrayToString(p.data);
        this.src_ip = p.src_ip;
        this.dst_ip = p.dst_ip;
        this.sequence = p.sequence;
        this.rsv1 = p.rsv1;
        this.rsv2 = p.rsv2;
        this.window = p.window;
        this.datalink = p.datalink;
        System.out.println("AAAA " + data);
//        configureHTTPHeader();
    }

    public static void configureHTTPHeader(String data) {
        HashMap<String,String> fieldContent = new HashMap<>();

//        String data = this.data;
        String[] lines = data.split("\r\n");
        List<String> linesList = Arrays.asList(lines);
        if(lines.length > 0) {
//            this.response = isHttpResponse(lines[0]);

            for (String line : linesList) {
                if(linesList.indexOf(line)==0) continue;

                String[] linesParts = line.split(":");
                if(linesParts.length > 0) {
                    String field = linesParts[0];
                   List<String> partsList = new ArrayList<String>(Arrays.asList(linesParts));
                    partsList.remove(0);
                    String content = String.join("", partsList);
                    fieldContent.put(field, content);
                } else {
                    fieldContent.put("", line);
                }
            }
        }

        if(!fieldContent.isEmpty()) {
            for (String key : fieldContent.keySet()) {
                String value = fieldContent.get(key);
                if(key.isEmpty()) {
                    if(!value.isEmpty()) {
                        content = value;
                        continue;
                    }
                }
                configureHttpField(key,value);
            }
        }

    }

    private static void configureHttpField(String key, String value) {
        HttpFieldsHelper helper = new HttpFieldsHelper();
        String field = helper.field(key);

        try {
            Field declaredField = HttpPacket.class.getDeclaredField(field);
            System.out.println("declarede field " + declaredField.getName());
            System.out.println("value " + value);
//            declaredField.set(this,value);
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isHttpResponse(String line) {
        String firstLine = line;
        String[] parts = firstLine.split(" ");

        List<String> lengthThreeStr = Arrays.stream(parts).filter(str -> str.length() == 3)
                .collect(Collectors.toList());
        for (String code : lengthThreeStr) {
            try {
                Integer.parseInt(code);
                return true;
            } catch (NumberFormatException ex){
                continue;
            }
        }
        return false;
    }

    public static boolean isHttp(byte[] data) {
        String firstLine = byteArrayToString(data);
        String[] parts = firstLine.split("\r\n");

        for (String code : parts) {
            if(code.contains("HTTP")) return true;
        }
        return false;
    }

    private static String byteArrayToString(byte[] data) {
        String strData = new String(data);
        return strData;
    }

    @Override
    public String toString() {
        return "HttpPacket{" +
                "data='" + data + '\'' +
                ", method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", httpVersion='" + httpVersion + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", host='" + host + '\'' +
                ", accept='" + accept + '\'' +
                ", acceptLanguage='" + acceptLanguage + '\'' +
                ", acceptEncoding='" + acceptEncoding + '\'' +
                ", contentLength='" + contentLength + '\'' +
                ", connection='" + connection + '\'' +
                ", contentType='" + contentType + '\'' +
                ", date='" + date + '\'' +
                ", from='" + from + '\'' +
                ", lastModified='" + lastModified + '\'' +
                ", server='" + server + '\'' +
                ", acceptRanges='" + acceptRanges + '\'' +
                ", cacheControl='" + cacheControl + '\'' +
                ", warning='" + warning + '\'' +
                ", referer='" + referer + '\'' +
                ", cookie='" + cookie + '\'' +
                ", authorization='" + authorization + '\'' +
                ", proxyAuthorization='" + proxyAuthorization + '\'' +
                ", response=" + response +
                ", content='" + content + '\'' +
                '}';
    }

    public static void main(String[] args) {
        String data = "GET /connecttest.txt?n=1575044103651 HTTP/1.1\r\n" +
                "Host: www.msftconnecttest.com\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: text/plain\r\n" +
                "Cache-Control: no-cache, no-store, must-revalidate\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Skype/8.54.0.91 Chrome/73.0.3683.121 Electron/5.0.10 Safari/537.36\r\n" +
                "Content-Type: text/plain\r\n" +
                "Accept-Encoding: gzip, deflate\r\n" +
                "Accept-Language: pt-BR\r\n";

        configureHTTPHeader(data);
    }
}
