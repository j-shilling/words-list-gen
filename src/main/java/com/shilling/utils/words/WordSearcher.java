package com.shilling.utils.words;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WordSearcher {
	Path path = null;
	
	public WordSearcher(Path path) {
		if ((path != null) && (!path.toString().isEmpty())) {
			this.path = path;
		}
	}
	
	@SuppressWarnings("unchecked")
	private String searchOnline(String searchTerm) {
		String ret = null;
		WebClient client = new WebClient();
		
		HtmlPage page = null;
		
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		
		try {
			String searchUrl = "http://www.archives.nd.edu/cgi-bin/words.exe?" + URLEncoder.encode(searchTerm, "UTF-8");
			page = client.getPage(searchUrl);
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<HtmlElement> items = null;
		List<?> result = page.getByXPath("//pre");
		
		if (result.isEmpty()) {
			return null;
		}
		
		if (result.get(0) instanceof HtmlElement) {
			items = (List<HtmlElement>) result;
		} else {
			return null;
		}
		
		if (!items.isEmpty()) {
			ret = new String ("");
			for (HtmlElement item : items) {
				ret = ret.concat(item.asText());
			}
		}
		
		return ret;
	}
	
	private String searchLocal(String searchTerm) {
		CommandLine cmdLine = new CommandLine(path.toFile());
		cmdLine.addArgument(searchTerm);
		
		DefaultExecutor executor = new DefaultExecutor();
		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
		executor.setWatchdog(watchdog);
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PumpStreamHandler psh = new PumpStreamHandler(output);
		
		executor.setStreamHandler(psh);
		
		try {
			executor.execute(cmdLine);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			return output.toString("UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			return null;
		}
	}
	
	List<Response> search(String searchTerm) {
		String response = null;
		
		if (this.path == null) {
			response = searchOnline(searchTerm);
		} else {
			response = searchLocal(searchTerm);
		}
		
		return Response.parseResponse(response);
	}
}
