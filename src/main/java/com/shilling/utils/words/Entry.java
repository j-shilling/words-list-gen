package com.shilling.utils.words;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Entry implements Comparable<Entry> {
	int frequency;
	String word;
	String def;
	
	public Entry (String line) {
		String[] parsedLine = line.split(":");
		
		setFrequency(Integer.parseInt(parsedLine[0]));
		setWord(new String(parsedLine[1]));
		setDefinition(new String(parsedLine[2]));
	}
	
	public Entry () {
		setFrequency(0);
		setWord("");
	}
	
	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	public void incrementFrequency() {
		setFrequency (getFrequency() + 1);
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = new String(word);
	}
	
	public String getDefinition() {
		return def;
	}
	
	public void setDefinition(String val) {
		this.def = new String(val);
	}

	@Override
	public String toString() {
		return Integer.toString(this.getFrequency()) + ":" + this.getWord() + ":" + this.getDefinition();
	}

	@Override
	public int compareTo(Entry that) {
		if (this.getFrequency() == that.getFrequency()) {
			return this.getWord().toLowerCase().compareTo(that.getWord().toLowerCase());
		}
		
		return that.getFrequency() - this.getFrequency();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Entry) {
			Entry that = (Entry) obj;
			
			return this.getWord().toLowerCase().equals(
					that.getWord().toLowerCase());
		} else if (obj instanceof Response) {
			return this.equals(((Response)obj).toEntry());
		}
		
		return false;
	}
	
	public void writeToFile(String filename) throws IOException {
		try (FileWriter fw = new FileWriter(filename, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter (bw)) {
			out.println(this.toString());
		}
	}
}
