package com.shilling.utils.words;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Response implements Comparable<Response> {
	public static List<Response> parseResponse(String txt) {
		List<Response> ret = new ArrayList<Response>();
		
		InputStream is = new ByteArrayInputStream(txt.getBytes());
		InputStreamReader ir = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(ir);
		
		Pattern formInfoLine = Pattern.compile("[a-zA-Z]+\\.[a-zA-Z]+");
		Pattern lexicalInfoLine = Pattern.compile("\\[[A-Z][A-Z][A-Z][A-Z][A-Z]\\]");
		
		String line = null;
		Response rs = new Response();
		try {
			while ((line = br.readLine()) != null) {
				Matcher formInfoMatcher = formInfoLine.matcher(line);
				Matcher lexicalInfoMatcher = lexicalInfoLine.matcher(line);
				
				if (formInfoMatcher.find()) {
					rs.addFormInfo(line);
				} else if (lexicalInfoMatcher.find()) {
					String lex = line.split("\\[[A-Z][A-Z][A-Z][A-Z][A-Z]\\]")[0].trim();
					
					if (lex.isEmpty()) {
						lex = rs.getFormInfo().get(0).split("\\s")[0];
						lex = lex.replaceAll("\\.", "");
					} else {
						lex = line.split("  ")[0];
					}
					rs.setLexicalInfo(lex);;
					rs.setEnDef(br.readLine());
					
					ret.add(rs);
					rs = new Response();
				}
			}
			
			br.close();
			ir.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	String lexicalInfo;
	String enDef;
	List<String> formInfo;

	private Response() {
		lexicalInfo = null;
		enDef = null;
		formInfo = new ArrayList<String>();
	}

	public String getLexicalInfo() {
		return lexicalInfo;
	}

	private void setLexicalInfo(String lexicalInfo) {
		this.lexicalInfo = new String(lexicalInfo.trim());
	}

	public String getEnDef() {
		return enDef;
	}

	private void setEnDef(String enDef) {
		this.enDef = new String(enDef.trim());
	}

	public List<String> getFormInfo() {
		return formInfo;
	}

	private void addFormInfo(String form) {
		this.getFormInfo().add(form);
	}
	
	public Entry toEntry() {
		Entry ret = new Entry();
		
		ret.setWord(this.getLexicalInfo());
		ret.setDefinition(this.getEnDef());
		
		return ret;
	}

	@Override
	public int compareTo(Response that) {
		return this.getLexicalInfo().toLowerCase().compareTo(that.getLexicalInfo().toLowerCase());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Response) {
			Response that = (Response) obj;
			
			return this.getLexicalInfo().equals(that.getLexicalInfo()) 
					&& this.getEnDef().equals(that.getEnDef())
					&& this.formInfo.equals(that.getFormInfo());
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		
		for (String s : this.getFormInfo()) {
			sb.append(s + System.lineSeparator());
		}
		sb.append(this.getLexicalInfo() + System.lineSeparator());
		sb.append(this.getEnDef() + System.lineSeparator());
		
		return sb.toString();
	}
}
