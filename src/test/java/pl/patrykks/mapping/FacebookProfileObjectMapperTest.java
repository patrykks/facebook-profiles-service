package pl.patrykks.mapping;

import org.junit.Before;
import org.junit.Test;
import pl.patrykks.domain.FacebookProfile;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class FacebookProfileObjectMapperTest {
    private FacebookProfileObjectMapper facebookProfileMapper;

    @Before
    public void initializeFacebookDataSource() {
        facebookProfileMapper = new FacebookProfileObjectMapper();
    }

    @Test
    public void mapperShouldReturnEmptyOptionalWhenFacebookIdIsNull()  {
        //given
        String stringifiedProfile = "{ \"firstname\": \"Luna\", \"lastname\": \"King\"}";

        //when
        Optional<FacebookProfile> profile = facebookProfileMapper.map(stringifiedProfile);

        //then
        assertEquals(false, profile.isPresent());
    }

    @Test
    public void mapperShouldCorrectlyMapIdField()  {
        //given
        String stringifiedProfile = "{ \"id\": \"1\"}";

        //when
        Optional<FacebookProfile> profile = facebookProfileMapper.map(stringifiedProfile);

        //then
        assertEquals("1", profile.get().getId());
    }

}
