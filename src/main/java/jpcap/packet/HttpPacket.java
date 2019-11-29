package jpcap.packet;

import jpcap.util.HttpFieldsHelper;

import java.lang.reflect.Field;
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
    private String content;

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
        configureHTTPHeader();
    }

    public void configureHTTPHeader() {
        HashMap<String,String> fieldContent = new HashMap<>();

        String data = this.data;
        String[] lines = data.split("\r\n");
        List<String> linesList = Arrays.asList(lines);
        if(lines.length > 0) {
            this.response = isHttpResponse(lines[0]);

            for (String line : linesList) {
                if(linesList.indexOf(line)==0) continue;

                String[] linesParts = line.split(":");
                if(linesParts.length > 0) {
                    String field = linesParts[0];
                    List<String> partsList = Arrays.asList(linesParts);
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

    private void configureHttpField(String key, String value) {
        HttpFieldsHelper helper = new HttpFieldsHelper();
        String field = helper.field(key);

        try {
            Field declaredField = this.getClass().getDeclaredField(field);
            declaredField.set(this,value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean isHttpResponse(String line) {
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

    private String byteArrayToString(byte[] data) {
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
}
