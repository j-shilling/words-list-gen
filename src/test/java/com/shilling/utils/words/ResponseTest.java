package com.shilling.utils.words;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class ResponseTest {
	private static String amantisSearch;
	private static String mihiSearch;
	private static String egoSearch;
	
	private String getResource(String filename) {
		URL url = Resources.getResource(filename);
		try {
			return new String(Resources.toString(url, Charsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ResponseTest() {
		amantisSearch = getResource("search.amantis");
		mihiSearch = getResource("search.mihi");
		egoSearch = getResource("search.ego");
	}

	@Test
	public void AmantisTest() {
		assertNotNull(amantisSearch);
		
		List<Response> tests = Response.parseResponse(amantisSearch);
		assertNotNull(tests);
		assertTrue (tests.size() == 3);
		
		Response test = tests.get(0);
		
		assertNotNull(test);
		assertTrue (test.getLexicalInfo().equals("amo, amare, amavi, amatus"));
		assertTrue (test.getEnDef().equals("love, like; fall in love with; be fond of; have a tendency to;"));
		assertTrue (test.getFormInfo().size() == 1);
		assertTrue (test.getFormInfo().get(0).contains("am.antis             VPAR   1 1 GEN S X PRES ACTIVE  PPL"));
		
		test = tests.get(1);
		
		assertNotNull(test);
		assertTrue(test.getLexicalInfo().equals("amans, amantis"));
		assertTrue(test.getEnDef().equals("lover, sweetheart; mistress; one who is fond/affectionate;"));
		assertTrue(test.getFormInfo().size() == 2);
		assertTrue(test.getFormInfo().get(0).equals("amant.is             N      3 3 GEN S C                 "));
		assertTrue(test.getFormInfo().get(1).equals("amant.is             N      3 3 ACC P C                   Early   "));
	
		test = tests.get(2);
		
		assertNotNull(test);
		assertTrue(test.getLexicalInfo().equals("amans, amantis (gen.), amantior -or -us, amantissimus -a -um"));
		assertTrue(test.getEnDef().equals("loving/fond/affectionate; beloved/dear to; friendly/kind; having love/affection"));
		assertTrue(test.getFormInfo().size() == 2);
		assertTrue(test.getFormInfo().get(0).equals("amant.is             ADJ    3 1 GEN S X POS             "));
		assertTrue(test.getFormInfo().get(1).equals("amant.is             ADJ    3 1 ACC P C POS             "));
	}
	
	@Test
	public void MihiTest() {
		assertNotNull (mihiSearch);
		
		List<Response> tests = Response.parseResponse(mihiSearch);
		assertNotNull (tests);
		assertTrue (tests.size() == 1);
		
		Response test = tests.get(0);
		
		assertNotNull(test);
		assertTrue (test.getLexicalInfo().equals("mihi"));
		assertTrue (test.getEnDef().equals("I, me (PERS); myself (REFLEX);"));
		assertTrue (test.getFormInfo().size() == 1);
		assertTrue (test.getFormInfo().get(0).equals("m.ihi                PRON   5 1 DAT S C                 "));
	}
	
/*	@Test
	public void EgoTest() {
		assertNotNull (egoSearch);
		
		List<Response> tests = Response.parseResponse(egoSearch);
		assertNotNull (tests);
		assertTrue (tests.size() == 1);
		
		Response test = tests.get(0);
		
		assertNotNull(test);
		assertTrue (test.getLexicalInfo().equals("ego"));
		assertTrue (test.getEnDef().equals("I, me (PERS); myself (REFLEX);"));
		assertTrue (test.getFormInfo().size() == 1);
		assertTrue (test.getFormInfo().get(0).equals("ego                  PRON   5 1 NOM S C                 "));
	}*/
}
