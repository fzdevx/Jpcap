package jpcap.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class HttpFieldsHelper {
    public static final String userAgent = "User-Agent";
    public static final String host = "Host";
    public static final String acceptLanguage = "Accept-Language";
    public static final String acceptEncoding = "Accept-Encoding";
    public static final String connection = "Connection";
    public static final String contentLength = "Content-Length";
    public static final String contentType = "Content-Type";
    public static final String accept = "Accept";
    public static final String date = "Date";
    public static final String from = "From";

    public static final String lastModified = "Last-Modified";
    public static final String server = "Server";
    public static final String acceptRanges = "AcceptRanges";
    public static final String cacheControl = "Cache-Control";
    public static final String warning = "Warning";
    public static final String referer  = "Referer";
    public static final String cookie  = "Cookie";


    public final String field(String var) {
        Field[] declaredFields = this.getClass().getDeclaredFields();
        List<Field> fields = Arrays.asList(declaredFields);

        String variable = fields.stream()
                .filter(field -> field.getName()
                .equals(var))
                .map(field -> field.getName())
                .findFirst()
                .orElse(null);

        return variable;
    }



}