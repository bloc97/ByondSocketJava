/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byondsocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author bowen
 */
public class ByondSocket {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //Socket echoSocket = new Socket("bagil.game.tgstation13.org", 2337);
            Socket echoSocket = new Socket("sybil.game.tgstation13.org", 1337);
            //Socket echoSocket = new Socket("nanotrasen.se", 6666);
            DataOutputStream out = new DataOutputStream(echoSocket.getOutputStream());
            DataInputStream in = new DataInputStream(echoSocket.getInputStream());
            echoSocket.setSoTimeout(40000);
            System.out.println("Is Connected: " + echoSocket.isConnected());
            System.out.println("Local Port: " + echoSocket.getLocalPort());
            
            out.write(parsePacket("?status"));
            out.flush();
            System.out.println("Sent.");
            String back = in.readUTF();
            System.out.println(back);
            System.out.println("End");

            in.close();
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static byte[] parsePacket(String send) {
        //byte[] bytes = toByte(new int[] {0x00, 0x83, 0x00, 0x0D, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3f, 0x73, 0x74, 0x61, 0x74, 0x75, 0x73, 0x00}); //Test Bytes
        final char c0 = 0;
        final char c83 = 0x83;
        final char csh = 0x00; //evil hack, assuming string length never exceeds 256
        final char csl = (char)(send.getBytes().length+6);
        
        char[] chars0 = new char[] {c0, c83, csh, csl, c0, c0, c0, c0, c0};
        char[] chars1 = send.toCharArray();
        char[] chars2 = new char[] {c0};
        char[] allChars = joinArray(new char[][] {chars0, chars1, chars2});
        
        return toByte(allChars);
        
    }
    
    static byte[] toByte(char[] array) {
        final byte[] result = new byte[array.length];
        for (int i=0; i<result.length; i++) {
            result[i] = (byte)array[i];
        }
        return result;
    }
    static byte[] toByte(int[] array) {
        final byte[] result = new byte[array.length];
        for (int i=0; i<result.length; i++) {
            result[i] = (byte)array[i];
        }
        return result;
    }
    
    static char[] joinArray(char[]... arrays) {
        int length = 0;
        for (char[] array : arrays) {
            length += array.length;
        }

        final char[] result = new char[length];

        int offset = 0;
        for (char[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }

        return result;
    }
}
