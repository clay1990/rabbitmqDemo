package tomcat.zubotongxun;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * @Auther: yuyao
 * @Date: 2019/7/10 14:29
 * @Description:
 */
public class ZuBoNode1 {

    private static int port = 8000;
    private static String address = "228.0.0.4";
    public static void main(String[] args) {

        try {
            InetAddress group = InetAddress.getByName(address);
            MulticastSocket mss = null;
            mss = new MulticastSocket(port);
            mss.joinGroup(group);
            while (true){
                byte[] buffer = "Hello from node1".getBytes();
                DatagramPacket dp = new DatagramPacket(buffer,buffer.length,group,port);
                mss.send(dp);
                Thread.sleep(1000);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}