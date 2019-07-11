package de.slag.finance.interfaces.ov;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OvUrlBuilderTest {
	
	OvUrlBuilder ovUrlBuilder;
	
	String expected = "http://xy.z/abc?notationId=1234&dateStart=01.01.1969&interval=Y1";
	
	
	@BeforeEach
	void setUp() {
		ovUrlBuilder = new OvUrlBuilder();
	}

	@Test
	void testBuild() throws MalformedURLException {
		ovUrlBuilder.date(new Date(0))
			.baseUrl("http://xy.z/abc")
			.notation("1234");
		
		final URL url = ovUrlBuilder.build();
		
		Assertions.assertNotNull(url);

		
		Assertions.assertEquals(expected, url.toString());
		
		
		
	}

}
