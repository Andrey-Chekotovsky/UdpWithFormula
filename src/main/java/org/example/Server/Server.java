package org.example.Server;

import org.example.DTO.FormulaArgsDto;
import org.example.DTO.FormulaArgsMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.lang.Math.*;

import static java.lang.Math.*;

public class Server {
    private static DatagramSocket socket;
    static private byte[] buf = new byte[512];
    static public String calculate(FormulaArgsDto dto){
        double arg1 = pow(2.71828, dto.getX() + dto.getY()) + pow(dto.getX(), abs(dto.getY())) + dto.getZ();
        double arg2 = dto.getX() + pow(dto.getX(), 3)/3 + pow(dto.getX(), 7)/7;
        return String.valueOf(log10(sqrt(arg1)) * arg2);
    }
    static public void workWithClient() throws IOException {
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received
                = new String(packet.getData(), 0, packet.getLength());
        FormulaArgsDto dto = new FormulaArgsMapper().mapFromJson(new JSONObject(received));

        received = calculate(dto);
        buf = received.getBytes();
        packet = new DatagramPacket(buf, buf.length, packet.getAddress(), packet.getPort());
        socket.send(packet);
    }
    static public void main(String[] args){
        try {
            socket = new DatagramSocket(4445);
            while (true) {
                workWithClient();
            }
        }
        catch (IOException excp)
        {
            excp.printStackTrace();
        }
        finally {
            socket.close();
        }
    }
}
