package com.shilling.utils.words;

import static org.junit.Assert.*;

import org.junit.Test;

public class CLIArgsTest {
/*	
	@Test
	public void InputShortTest() {
		CLIArgs opts = CLIArgs.getOpts("-i /home/jake/input1.txt");
		assertNotNull(opts);
		assertTrue (opts.getInput().size() == 1);
		assertTrue (opts.getInput().get(0).toString().equals("/home/jake/input1.txt"));
	}
	
	@Test
	public void InputLongTest() {
		CLIArgs opts = CLIArgs.getOpts("--input /home/jake/input1.txt");
		assertNotNull(opts);
		assertTrue (opts.getInput().size() == 1);
		assertTrue (opts.getInput().get(0).toString().equals("/home/jake/input1.txt"));
	}
	
	@Test
	public void InputMultiTest() {
		CLIArgs opts = CLIArgs.getOpts("-i /home/jake/input1.txt:/home/jake/input2.txt:/home/jake/input3.txt");
		assertNotNull(opts);
		assertTrue (opts.getInput().size() == 3);
		assertTrue (opts.getInput().get(0).toString().equals("/home/jake/input1.txt"));
		assertTrue (opts.getInput().get(1).toString().equals("/home/jake/input2.txt"));
		assertTrue (opts.getInput().get(2).toString().equals("/home/jake/input3.txt"));

	}
	
	@Test
	public void OutputShortTest() {
		CLIArgs opts = CLIArgs.getOpts("-o output.txt");
		assertNotNull(opts);
		assertTrue (opts.getOutput().toString().equals("output.txt"));
	}
	
	@Test
	public void OutputLongTest() {
		CLIArgs opts = CLIArgs.getOpts("--output /home/jake/output.txt");
		assertNotNull(opts);
		assertTrue (opts.getOutput().toString().equals("/home/jake/output.txt"));
	}
	
	@Test
	public void InputAndOutputTest() {
		CLIArgs opts = CLIArgs.getOpts("-i /home/jake/input1.txt:/home/jake/input2.txt --output /home/jake/output.txt");
		assertNotNull(opts);
		assertTrue(opts.getInput().size() == 2);
		assertTrue(opts.getInput().get(0).toString().equals("/home/jake/input1.txt"));
		assertTrue(opts.getInput().get(1).toString().equals("/home/jake/input2.txt"));
		assertTrue(opts.getOutput().toString().equals("/home/jake/output.txt"));
	}
	
	@Test
	public void InputExclueAndOutputTest() {
		CLIArgs opts = CLIArgs.getOpts("-i /home/jake/input1.txt:/home/jake/input2.txt -x exclude1.txt:exclude2.txt --output /home/jake/output.txt");
		assertNotNull(opts);
		assertTrue(opts.getInput().size() == 2);
		assertTrue(opts.getInput().get(0).toString().equals("/home/jake/input1.txt"));
		assertTrue(opts.getInput().get(1).toString().equals("/home/jake/input2.txt"));
		assertTrue(opts.getOutput().toString().equals("/home/jake/output.txt"));
		assertTrue(opts.getExcl().size() == 2);
		assertTrue(opts.getExcl().get(0).toString().equals("exclude1.txt"));
		assertTrue(opts.getExcl().get(1).toString().equals("exclude2.txt"));
	}
	
	@Test
	public void InputExclueAndOutputWithArgsTest() {
		CLIArgs opts = CLIArgs.getOpts("-i /home/jake/input1.txt:/home/jake/input2.txt -x exclude1.txt:exclude2.txt --output /home/jake/output.txt word1 word2 word3");
		assertNotNull(opts);
		assertTrue(opts.getInput().size() == 2);
		assertTrue(opts.getInput().get(0).toString().equals("/home/jake/input1.txt"));
		assertTrue(opts.getInput().get(1).toString().equals("/home/jake/input2.txt"));
		assertTrue(opts.getOutput().toString().equals("/home/jake/output.txt"));
		assertTrue(opts.getExcl().size() == 2);
		assertTrue(opts.getExcl().get(0).toString().equals("exclude1.txt"));
		assertTrue(opts.getExcl().get(1).toString().equals("exclude2.txt"));
		assertTrue(opts.getArgs().size() == 3);
		assertTrue(opts.getArgs().get(0).toString().equals("word1"));
		assertTrue(opts.getArgs().get(1).toString().equals("word2"));
		assertTrue(opts.getArgs().get(2).toString().equals("word3"));

	}
*/
}
