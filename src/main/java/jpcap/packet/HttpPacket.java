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

    private String firstLine="";
    private String method="";
    private String path="";
    private String httpVersion="";
    private String userAgent="";
    private String host="";
    private String accept="";
    private String acceptLanguage="";
    private String acceptEncoding="";
    private String contentLength="";
    private String connection="";
    private String contentType="";
    private String date="";
    private String from="";
    private String lastModified="";
    private String server="";
    private String acceptRanges="";
    private String cacheControl="";
    private String warning="";
    private String referer="";
    private String cookie="";
    private String authorization="";
    private String proxyAuthorization="";

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
                if(linesList.indexOf(line)==0) {
                    firstLine = line;
                    continue;
                }

                String[] linesParts = line.split(":");

                if(linesParts.length > 1) {
                    String field = linesParts[0];
                   List<String> partsList = new ArrayList<String>(Arrays.asList(linesParts));
                    partsList.remove(0);
                    String content = String.join("", partsList);
                    fieldContent.put(field, content);
                } else {
                    fieldContent.put("content", line);
                }
            }
        }

        if(!fieldContent.isEmpty()) {
            for (String key : fieldContent.keySet()) {
                String value = fieldContent.get(key);
                if(key.equals("content")) {
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

        if(field!=null) {
            try {
                if(key=="content") {
                    content=content;
                } else{
                    Field declaredField = HttpPacket.class.getDeclaredField(field);
//                System.out.println(declaredField.getName()+"::"+value);
                    declaredField.set(this, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
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
        String str = "HttpPacket " +(response?"Response ":"Request");
        str += " head=" + firstLine + " ";
        str += (!userAgent.isEmpty()?(" userAgent= "  + userAgent):"");
        str += (!host.isEmpty()?(" host= "  + host):"");
        str += (!accept.isEmpty()?(" accept= "  + accept):"");
        str += (!acceptLanguage.isEmpty()?(" acceptLanguage= "  + acceptLanguage):"");
        str += (!acceptEncoding.isEmpty()?(" acceptEncoding= "  + acceptEncoding):"");
        str += (!contentLength.isEmpty()?(" contentLength= "  + contentLength):"");
        str += (!connection.isEmpty()?(" connection= "  + connection):"");
        str += (!contentType.isEmpty()?(" contentType= "  + contentType):"");
        str += (!date.isEmpty()?(" date= "  + date):"");
        str += (!from.isEmpty()?(" from= "  + from):"");
        str += (!lastModified.isEmpty()?(" lastModified= "  + lastModified):"");
        str += (!server.isEmpty()?(" server= "  + server):"");
        str += (!acceptRanges.isEmpty()?(" acceptRanges= "  + acceptRanges):"");
        str += (!cacheControl.isEmpty()?(" cacheControl= "  + cacheControl):"");
        str += (!referer.isEmpty()?(" referer= "  + referer):"");
        str += (!cookie.isEmpty()?(" cookie= "  + cookie):"");
        str += (!authorization.isEmpty()?(" authorization= "  + authorization):"");
        str += (!proxyAuthorization.isEmpty()?(" proxyAuthorization= "  + proxyAuthorization):"");
        return str;

    }
}
