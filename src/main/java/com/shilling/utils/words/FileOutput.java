package com.shilling.utils.words;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileOutput implements OutputFilter {
	Path path;
	List<Entry> buffer;
	
	public FileOutput (Path path) {
		buffer = new ArrayList<Entry>();
		this.path = path;
	}
	
	@Override
	public void add(Entry e) {
		
		boolean added = false;
		for (Entry entry : buffer) {
			if (entry.equals(e)) {
				entry.incrementFrequency();
				added = true;
			}
		}
		
		if (!added) {
			e.setFrequency(1);
			buffer.add(e);
		}
		
		Collections.sort(buffer);
		
		try (BufferedWriter bw = Files.newBufferedWriter(path);
				PrintWriter out = new PrintWriter (bw)) {
			for (Entry entry : buffer) {
				out.println(entry.toString());
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

}
