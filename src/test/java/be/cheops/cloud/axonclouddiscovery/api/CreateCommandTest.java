package be.cheops.cloud.axonclouddiscovery.api;

import com.thoughtworks.xstream.XStream;
import org.axonframework.serialization.xml.CompactDriver;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateCommandTest {

    private XStream xStream = new XStream(new CompactDriver());

    @Test
    public void testToXml() {

        assertThat(xStream.toXML(new CreateCommand("abc"))).isEqualTo("<be.cheops.cloud.axonclouddiscovery.api.CreateCommand><id>abc</id></be.cheops.cloud.axonclouddiscovery.api.CreateCommand>");
    }

    @Test
    public void testFromXml() {

        final CreateCommand command = (CreateCommand) xStream.fromXML("<be.cheops.cloud.axonclouddiscovery.api.CreateCommand><id>abc</id></be.cheops.cloud.axonclouddiscovery.api.CreateCommand>");

        assertThat(command.getId()).isEqualTo("abc");
    }
}