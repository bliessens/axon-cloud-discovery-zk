package be.cheops.cloud.axonclouddiscovery;

import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"cloud", "unittest"})
public class AxonCloudDiscoveryApplicationTests {

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

    @Test
    public void contextLoads() {
    }

}
