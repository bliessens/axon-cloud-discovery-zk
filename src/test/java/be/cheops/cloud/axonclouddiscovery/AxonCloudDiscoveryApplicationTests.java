package be.cheops.cloud.axonclouddiscovery;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"cloud", "unittest"})
public class AxonCloudDiscoveryApplicationTests {

    @Test
    public void contextLoads() {
    }

}
