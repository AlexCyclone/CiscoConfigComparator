package icu.cyclone.alex.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import icu.cyclone.alex.cisco.reader.InvalidReadDataException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ShellExec {
    private String username;
    private String password;
    private String hostname;
    private int port;
    private ArrayList<String> result = new ArrayList<>();

    public ShellExec(String username, String password, String hostname, int port) {
        this.username = username;
        this.password = password;
        this.hostname = hostname;
        this.port = port;
    }

    public ArrayList<String> exec(String command) throws IOException {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, hostname, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            InputStream in = channelExec.getInputStream();

            channelExec.setCommand(command);
            channelExec.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = reader.readLine()) != null) {
                result.add(line);
            }

            channelExec.disconnect();
            session.disconnect();

        } catch (JSchException | IOException e) {
            throw new IOException("Connection failed");
        }
        return result;
    }
}
