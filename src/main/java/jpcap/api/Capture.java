package jpcap.api;

import jpcap.JpcapCaptor;
import jpcap.PackagePrinter;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;

import java.io.IOException;
import java.net.NetworkInterface;
import java.util.concurrent.*;

public class Capture implements AutoCloseable {

    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
    private final JpcapCaptor captor;
    private final BlockingQueue<Packet> queue = new LinkedBlockingDeque<>(10);
    private final CopyOnWriteArrayList<PacketHandler> handlers = new CopyOnWriteArrayList<>();

    private final Runnable captureWorker = new Runnable() {
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                Packet packet = queue.poll();
                for (PacketHandler handler : handlers) {
                    handler.handle(packet);
                }
            }
        }
    };

    public Capture(JpcapCaptor captor) {
        this.captor = captor;
    }

    public void start() {
        EXECUTOR.execute(captureWorker);
        captor.loopPacket(-1, new PacketReceiver() {
            @Override
            public void receivePacket(Packet p) {
                queue.offer(p);
            }
        });
    }

    @Override
    public void close() throws Exception {
        captor.close();
    }

    public void addHandler(PacketHandler handler) {
        handlers.add(handler);
    }

    public void removeHandler(PacketHandler handler) {
        handlers.remove(handler);
    }

    public static Capture fromFile(String filename) {
        JpcapCaptor captor = null;
        try {
            captor = JpcapCaptor.openFile(filename);
        } catch (IOException e) {
            throw new CaptureException(e);
        }
        return new Capture(captor);
    }

    public static Capture fromInterface(NetworkInterface iface) {
        JpcapCaptor captor = null;
        try {
            captor = JpcapCaptor.openDevice(null, Integer.MAX_VALUE, true, 1000);
        } catch (IOException e) {
            throw new CaptureException(e);
        }
        return new Capture(captor);
    }

    public static void main(String... args) throws Exception {

        initSniffer();
//        try (Capture c = Capture.fromFile("")) {
//            c.addHandler(new PacketHandler() {
//                @Override
//                public void handle(Packet packet) {
//                    System.out.println(packet);
//                }
//            });
//            c.start();
//        }

    }

    public static void initSniffer(){
        //Pega as interfaces de rede
        jpcap.NetworkInterface[] devices = JpcapCaptor.getDeviceList();

        //Printa as interfaces
        for (int i = 0; i < devices.length ; i++) {
            System.out.println(devices[i].name  + " " + devices[i].description);

        }

        try {
            //usa a primeira interface para fazer a captura dos pacotes
            JpcapCaptor captor = JpcapCaptor.openDevice(devices[0], 65535, false, 1000);

            while (true){
                captor.processPacket(10, new PackagePrinter());
            }
            
            //captor.close();


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
