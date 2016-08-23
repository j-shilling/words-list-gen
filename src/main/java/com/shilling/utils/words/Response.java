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

public class Response {
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
					String lex = line.split("  ")[0];
					
					if (lex.matches("[a-z]+")) {
						rs.setLexicalInfo(lex);
					} else {
						lex = rs.getFormInfo().get(0).split("\\s")[0];
						lex = lex.replaceAll("\\.", "");
					}
					rs.setLexicalInfo(line.split("  ")[0]);;
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

	public void setLexicalInfo(String lexicalInfo) {
		this.lexicalInfo = new String(lexicalInfo);
	}

	public String getEnDef() {
		return enDef;
	}

	public void setEnDef(String enDef) {
		this.enDef = new String(enDef);
	}

	public List<String> getFormInfo() {
		return formInfo;
	}

	public void addFormInfo(String form) {
		this.getFormInfo().add(form);
	}
}
