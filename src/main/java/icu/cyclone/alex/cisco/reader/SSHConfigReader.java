package icu.cyclone.alex.cisco.reader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import icu.cyclone.alex.utils.ShellExec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SSHConfigReader implements ConfigReader {
    private String hostname;
    private ShellExec shellExec;
    private ArrayList<String> result = new ArrayList<>();

    public SSHConfigReader(String username, String password, String hostname, int port) {
        this.hostname = hostname;
        shellExec = new ShellExec(username, password, hostname, port);
    }

    @Override
    public ArrayList<String> read() throws InvalidReadDataException {
        try {
            result = shellExec.exec("sh run");
        } catch (IOException e) {
            throw new InvalidReadDataException("Connection failed");
        }
        return result;
    }

    @Override
    public String getName() {
        return hostname;
    }
}
