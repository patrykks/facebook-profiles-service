package pl.patrykks.mapping;

import org.junit.Before;
import org.junit.Test;
import pl.patrykks.domain.Facebook;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class FacebookJsonToObjectMapperTest {
    private FacebookJsonToObjectMapper facebookJsonToObjectMapper;

    @Before
    public void initializeFacebookDataSource() {
        facebookJsonToObjectMapper  = new FacebookJsonToObjectMapper();
    }

    @Test
    public void mapperShouldReturnEmptyOptionalWhenFacebookIdIsNull()  {
        //given
        String stringifiedFacebook = "{ \"firstname\": \"Luna\", \"lastname\": \"King\"}";

        //when
        Optional<Facebook> facebook = facebookJsonToObjectMapper.map(stringifiedFacebook);

        //then
        assertEquals(false, facebook.isPresent());
    }

    @Test
    public void mapperShouldCorrectlyMapIdField()  {
        //given
        String stringifiedFacebook = "{ \"id\": \"1\"}";

        //when
        Optional<Facebook> facebook = facebookJsonToObjectMapper.map(stringifiedFacebook);

        //then
        assertEquals("1", facebook.get().getId());
    }

}
