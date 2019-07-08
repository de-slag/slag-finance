package de.slag.finance.interfaces.ov;

import java.io.File;
import java.net.URL;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;

public class OvDownloadRunner implements Callable<Long> {

	private String url;
	
	private File targetFile;

	public OvDownloadRunner(String url, File targetFile) {
		this.url = url;
		this.targetFile = targetFile;
	}

	@Override
	public Long call() throws Exception {
		final long start = System.currentTimeMillis();
		FileUtils.copyURLToFile(new URL(url), targetFile);
		return System.currentTimeMillis() - start;
	}
}
