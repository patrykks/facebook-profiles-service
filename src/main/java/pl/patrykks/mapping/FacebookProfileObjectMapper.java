package pl.patrykks.mapping;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.patrykks.domain.FacebookProfile;

import java.io.IOException;
import java.util.Optional;

public class FacebookProfileObjectMapper {
    private static final Logger logger = LoggerFactory.getLogger(FacebookProfileObjectMapper.class);
    private ObjectReader facebookProfileReader;

    public FacebookProfileObjectMapper() {
        facebookProfileReader = new ObjectMapper().
                registerModule(new JavaTimeModule()).
                disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS).
                readerFor(FacebookProfile.class);
    }

    public Optional<FacebookProfile> map(String stringifiedJsonOfFacebookProfile) {
        logger.debug("Converting facebook profile from JSON to Java object \n {}", stringifiedJsonOfFacebookProfile);
        FacebookProfile profile;

        try {
            profile = facebookProfileReader.readValue(stringifiedJsonOfFacebookProfile);
        } catch (IOException e) {
            logger.error("Cannot map JSON representing facebook profile into Java object", e);
            return Optional.empty();
        }

        if (profile.getId() == null) {
            logger.error("Id of facebook profile cannot be extracted from JSON");
            return Optional.empty();
        }

        logger.debug("Facebook profile with id {} was converted from JSON object to Java representation", profile.getId());
        return Optional.of(profile);
    }
}
