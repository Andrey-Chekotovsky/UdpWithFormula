package org.example.Client;

import org.example.DTO.FormulaArgsDto;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class User {

    static private DatagramSocket socket;
    static private InetAddress address;
    static private byte[] buf = new byte[512];
    static private Scanner in = new Scanner(System.in);


    static public void sendString(String msg) throws IOException{

        buf = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
    }
    static public void sendFormulaArgs(FormulaArgsDto dto) throws IOException{
        JSONObject json = new JSONObject(dto);
        buf = json.toString().getBytes();
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
    }
    static public String receive() throws IOException
    {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(
                packet.getData(), 0, packet.getLength());
        return received;
    }
    static public FormulaArgsDto initialiseFormulaArgs(){
        FormulaArgsDto dto = new FormulaArgsDto();
        System.out.println("Enter x");
        dto.setX(Integer.parseInt(in.nextLine()));
        System.out.println("Enter y");
        dto.setX(Integer.parseInt(in.nextLine()));
        System.out.println("Enter z");
        dto.setX(Integer.parseInt(in.nextLine()));
        return dto;
    }

    static public void main(String[] args) throws IOException
    {
        String switcher = "Yes";
        FormulaArgsDto dto;
        while(!switcher.equals("No")) {
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
            dto = initialiseFormulaArgs();
            sendFormulaArgs(dto);
            String result = receive();
            System.out.println(result);
            System.out.println("Do you want to continue?(Yes/No)");
            switcher = in.nextLine();
        }
        close();
    }

    static public void close() {
        socket.close();
    }
}