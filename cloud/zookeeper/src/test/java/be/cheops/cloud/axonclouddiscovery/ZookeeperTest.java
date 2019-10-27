package be.cheops.cloud.axonclouddiscovery;

import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Ignore
public class ZookeeperTest {

    private Thread zookeeper;

    @Before
    public void startZK() throws QuorumPeerConfig.ConfigException {
        QuorumPeerConfig quorumConfiguration = new QuorumPeerConfig();
        quorumConfiguration.parse("src/test/resources/zoo-unittest.cfg");

        ZooKeeperServerMain zooKeeperServer = new ZooKeeperServerMain();
        final ServerConfig configuration = new ServerConfig();
        configuration.readFrom(quorumConfiguration);

        zookeeper = new Thread() {
            public void run() {
                try {
                    zooKeeperServer.runFromConfig(configuration);
                } catch (IOException e) {
                    System.err.println("ZooKeeper Failed " + e);
                }
            }
        };
        zookeeper.start();
    }

    @After
    public void tearDown() throws Exception {
        zookeeper.interrupt();
    }

    @Test
    public void contextLoads() throws InterruptedException {
        TimeUnit.SECONDS.sleep(60);
    }

}
