package jpcap;

public class Params {
    /*
     * if true, PackagePrinter class will treat and print FTP packets properly
     * otherwise FTP packets will be showed as TCP packets
     *
     * the default value is false
     *
     * added in 2019/11/28
     */
    public static boolean interpretFTP = false;

    /*
     * if true, PackagePrinter class will show only FTP packets
     *
     * the default value is false
     *
     * added in 2019/11/28
     */
    public static boolean showOnlyFTP = false;
}
