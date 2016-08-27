package com.shilling.utils.words;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class UserInput {
	Queue<String> words;
	
	public UserInput(List<Path> files, List<String> args) {
		words = new LinkedList<String>();
		
		if (args != null) {
			for (String word : args) {
				this.words.add(word);
			}
		}
		
		if (files != null) {
			for (Path path : files) {
				try {
					FileReader reader = new FileReader(path.toFile());
					StringBuilder sb = new StringBuilder("");
					int ch = 0;
					
					while (ch != -1) {
						ch = reader.read();
						if (Character.isAlphabetic(ch)) {
							sb.append((char) ch);
						} else if (Character.isWhitespace(ch)) {
							this.words.add(sb.toString());
							sb = new StringBuilder("");
						}
					}
					
					reader.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public int length() {
		return this.words.size();
	}
	
	public boolean hasNext() {
		return this.words.peek() != null;
	}
	
	public String next() {
		return this.words.poll();
	}
}
