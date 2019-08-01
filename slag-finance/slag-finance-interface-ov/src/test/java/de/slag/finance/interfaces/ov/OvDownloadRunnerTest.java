package de.slag.finance.interfaces.ov;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OvDownloadRunnerTest {

	String url = "http://speedtest.ftp.otenet.gr/files/test100k.db";

	File file;

	OvDownloadRunner runner;

	@Before
	public void setUp() throws IOException {
		file = Files.createTempFile("test", ".csv").toFile();
		runner = new OvDownloadRunner(new URL(url), file);
	}

	@Test
	public void testCall() throws Exception {
		Assert.assertNotNull(runner.call());
		Assert.assertEquals(file.length(), 102400);
	}
}
