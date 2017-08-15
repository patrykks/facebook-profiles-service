package pl.patrykks.mapping;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.patrykks.domain.Facebook;

import java.io.IOException;
import java.util.Optional;

public class FacebookJsonToObjectMapper {
    private static final Logger logger = LoggerFactory.getLogger(FacebookJsonToObjectMapper.class);
    private ObjectReader jsonToObjectFacebookReader;

    public FacebookJsonToObjectMapper() {
        jsonToObjectFacebookReader = new ObjectMapper().
                registerModule(new JavaTimeModule()).
                disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS).
                readerFor(Facebook.class);
    }

    public Optional<Facebook> map(String stringifiedJsonOfFacebook) {
        logger.debug("Converting facebook profile from JSON to Java object \n {}", stringifiedJsonOfFacebook);
        Facebook facebook;

        try {
            facebook = jsonToObjectFacebookReader.readValue(stringifiedJsonOfFacebook);
        } catch (IOException e) {
            logger.error("Cannot map JSON representing facebook profile into Java object", e);
            return Optional.empty();
        }

        if (facebook.getId() == null) {
            logger.error("Id of facebook profile cannot be extracted from JSON");
            return Optional.empty();
        }

        logger.debug("Facebook profile with id {} was converted from JSON object to Java representation", facebook.getId());
        return Optional.of(facebook);
    }
}
