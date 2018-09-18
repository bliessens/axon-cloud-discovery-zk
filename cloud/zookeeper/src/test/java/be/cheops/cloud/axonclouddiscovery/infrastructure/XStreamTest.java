package be.cheops.cloud.axonclouddiscovery.infrastructure;

import com.thoughtworks.xstream.XStream;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class XStreamTest {

    private final XStream xStream = new XStream();

    @Test
    public void XStreamSupports_jsr310() {
        assertThat(xStream.toXML(LocalDate.of(2018, 9, 17))).isEqualTo("<local-date>2018-09-17</local-date>");
    }
}