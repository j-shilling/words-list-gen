package com.shilling.utils.words;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class ListGen {
	
	private static String delimiter = "\\s";
	
	@SuppressWarnings("unchecked")
	private static String search(String searchQuery) {
		String ret = null;
		WebClient client = new WebClient();
		
		HtmlPage page = null;
		
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		
		try {
			String searchUrl = "http://www.archives.nd.edu/cgi-bin/words.exe?" + URLEncoder.encode(searchQuery, "UTF-8");
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
	
	private static String search(String cmd, String searchQuery) throws IOException, InterruptedException {
		CommandLine cmdLine = new CommandLine(cmd);
		cmdLine.addArgument(searchQuery);
		
		DefaultExecutor executor = new DefaultExecutor();
		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
		executor.setWatchdog(watchdog);
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PumpStreamHandler psh = new PumpStreamHandler(output);
		
		executor.setStreamHandler(psh);
		
		executor.execute(cmdLine);
		
		return output.toString("UTF-8");
	}
	
	private static void showProgressBar(int percent) {
		StringBuilder bar = new StringBuilder("[");
		
		for (int i = 0; i <= 50; i ++) {
			if (i< (percent/2)) {
				bar.append("=");
			} else if (i == (percent/2)) {
				bar.append(">");
			} else {
				bar.append(" ");
			}
		}
		
		bar.append(" ]     " + percent + "%   ");
		System.out.print(bar.toString() + '\r'); 
	}

	public static void main(String[] args) {
		CLIArgs opts = CLIArgs.getOpts(args);
		
		int current = 0;
		
		try {
			UserInput input = opts.getUserInput();

			List<Entry> results = new ArrayList<Entry>();
			
			int total = input.length();
			System.out.println("Looking up " + total + " words:");
			
			while (input.hasNext()) {
				current++;
				showProgressBar((int)Math.round(current * 100.0 / total));
				
				String response = new String ("");
				String searchTerm = input.next();
				if (opts.getExec() != null) {
					response = search(opts.getExec().toString(), searchTerm);
				} else {
					response = search(searchTerm);
				}
				List<Response> rets = Response.parseResponse(response);
				
				if (rets != null) {
					for (Response r : rets) {
						boolean added = false;
						for (Entry entry : results) {
							if (entry.getWord().equals(r.getLexicalInfo())) {
								entry.incrementFrequency();
								added = true;
							}
						}
						
						if (!added) {
							Entry entry = r.toEntry();
							entry.setFrequency(1);
							results.add(entry);
						}
					}
				}
				
				
			}
			
			Collections.sort(results);
			
			try {
			    Files.createFile(opts.getOutput());
			} catch (FileAlreadyExistsException ignored) {
				PrintWriter pw = new PrintWriter(opts.getOutput().toFile());
				pw.close();
			}
			
			System.out.println("Writing " + results.size() + " results to file:");
			current = 0;
			for (Entry entry : results) {
				current ++;
				showProgressBar((int)Math.round(current * 100.0 / results.size()));
				entry.writeToFile(opts.getOutput().toString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
