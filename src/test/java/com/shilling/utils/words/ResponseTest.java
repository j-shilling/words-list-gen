package com.shilling.utils.words;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class ResponseTest {
	private static String amantisSearch;
	
	public ResponseTest() {
		URL url = Resources.getResource("search.amantis");
		try {
			amantisSearch = new String(Resources.toString(url, Charsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void ConstructorTest() {
		assertNotNull(amantisSearch);
		assertTrue (amantisSearch.equals("hello"));
	}
}
