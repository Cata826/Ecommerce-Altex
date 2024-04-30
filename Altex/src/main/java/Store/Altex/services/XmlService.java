package Store.Altex.services;

import Store.Altex.models.User;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

@Service
public class XmlService {

    private final XmlMapper xmlMapper;

    public XmlService() {
        xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule()); // Register the module
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Write dates in ISO-8601 format
    }

    public String userToXml(User user) {
        StringWriter writer = new StringWriter();
        try {
            xmlMapper.writeValue(writer, user);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize user to XML", e);
        }
    }
}
