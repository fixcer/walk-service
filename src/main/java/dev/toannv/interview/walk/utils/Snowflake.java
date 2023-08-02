package dev.toannv.interview.walk.utils;

import cn.ipokerface.snowflake.SnowflakeIdGenerator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.Enumeration;

public class Snowflake implements IdentifierGenerator {

    private static final SnowflakeIdGenerator GENERATOR = new SnowflakeIdGenerator(0L, createNodeId());

    /*
     * Generate a unique id as a long value.
     */
    public static long nextId(){
        return GENERATOR.nextId();
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return nextId();
    }

    @Override
    public boolean supportsJdbcBatchInserts() {
        return true;
    }

    private static int createNodeId() {
        int nodeId;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    for (byte b : mac) {
                        sb.append(String.format("%02X", b));
                    }
                }
            }
            nodeId = sb.toString().hashCode();
        } catch (Exception ex) {
            nodeId = (new SecureRandom().nextInt());
        }
        nodeId = nodeId & 31;
        return nodeId;
    }

}
