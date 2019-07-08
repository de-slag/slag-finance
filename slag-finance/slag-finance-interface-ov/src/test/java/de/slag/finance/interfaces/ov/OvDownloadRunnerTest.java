package de.slag.finance.interfaces.ov;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OvDownloadRunnerTest {

	String url = "http://speedtest.ftp.otenet.gr/files/test100k.db";

	File file;

	OvDownloadRunner runner;

	@BeforeEach
	public void setUp() throws IOException {
		file = Files.createTempFile("test", ".csv").toFile();
		runner = new OvDownloadRunner(url, file);
	}

	@Test
	void testCall() throws Exception {
		Assertions.assertNotNull(runner.call());
		Assertions.assertEquals(file.length(), 102400);
	}
}
