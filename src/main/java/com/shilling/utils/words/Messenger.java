package com.shilling.utils.words;

import java.io.PrintStream;

public class Messenger {
	public static final Messenger instance =
			new Messenger();
	
	private Messenger() { }
	
	private boolean verbose = false;
	
	public boolean isVerbose() {
		return this.verbose;
	}
	
	public void setVerbose(boolean val) {
		this.verbose = val;
	}
	
	public void msg (String msg, PrintStream out, boolean force) {
		if (this.isVerbose() || force) {
			out.println(msg);
		}
	}
	
	public void fine (String msg, boolean force) {
		this.msg(msg, System.out, force);
	}
	
	public void fine (String msg) {
		this.fine(msg, false);
	}
	
	public void err (String msg, boolean force) {
		this.msg(msg, System.err, force);
	}
	
	public void err (String msg) {
		this.err(msg, false);
	}
}
