package com.shilling.utils.words;

import java.util.List;

public class ListGen {
	
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
		
		UserInput input = opts.getUserInput();
		OutputFilter output = opts.getOutputFiler();
		WordSearcher searcher = opts.getWordSearcher();
		
		Messenger.instance.setVerbose(opts.isVerbose());

		int total = input.length();
		int current = 0;
		System.out.println("Looking up " + total + " words:");
			
		while (input.hasNext()) {
			current++;
			showProgressBar((int)Math.round(current * 100.0 / total));
				
			List<Response> rets = searcher.search(input.next());
				
			if (rets != null) {
				for (Response r : rets) {
					output.add(r.toEntry());
				}
			}		
		}

	}

}
