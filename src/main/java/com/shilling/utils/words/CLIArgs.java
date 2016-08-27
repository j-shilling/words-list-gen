package com.shilling.utils.words;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.MultiPathOptionHandler;

public class CLIArgs {
		private static String getJarName() {
			try {
				return new File(CLIArgs.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getName();
			} catch (URISyntaxException e) {
				return "/path/to/progfile.jar";
			}
		}
	
		public static CLIArgs getOpts(String args) {
			return getOpts(args.split("\\s"));
		}
		public static CLIArgs getOpts(String[] args) {
			CLIArgs ret = new CLIArgs();
			CmdLineParser parser = new CmdLineParser(ret);
			
			try {
				parser.parseArgument(args);
				
				if (ret.isHelp()) {
					System.out.println("java -jar " + getJarName() + " [options] [word1 [word2 [[word3] [...]]]]");
					
					parser.printUsage(System.out);
					System.exit(0);
				}
			} catch (CmdLineException e) {
				System.out.println(e.getMessage());
				parser.printUsage(System.err);
				System.exit(1);
			} catch (NullPointerException e) {
				System.err.println("boooo");
			}
			
			ret.validateOptions();
			return ret;
		}

		@Option(name = "-i",
				aliases = {"--input"},
				metaVar = "<file>",
				required = false,
				usage = "input file to draw words from",
				handler = MultiPathOptionHandler.class)
		private List<Path> input;
		
		@Option(name = "-o",
				aliases = {"--output"},
				metaVar = "<file>",
				required = false,
				usage = "output file to write words to")
		private Path output;
		
		@Option(name = "-w",
				aliases = {"--words"},
				metaVar = "<file>",
				required = false,
				usage = "path to the Whitaker's Words executable")
		private Path exec;
		
		@Option(name = "-x",
				aliases = {"--exclude"},
				metaVar = "<file>",
				required = false,
				usage = "file of words to exlude from output",
				handler = MultiPathOptionHandler.class)
		private List<Path> excl;
		
		@Option(name = "-v",
				aliases = {"verbose"},
				required = false,
				usage = "turn on verbose error and loggin messages")
		private boolean verbose;
		
		@Option(name = "-c",
				aliases = {"--config"},
				required = false,
				forbids = {"-i", "-o", "-e", "-x"},
				usage = "enter configuaration mode")
		private boolean config;
		
		@Option(name = "-u",
				aliases = {"--user"},
				required = false,
				depends = {"-c"},
				usage = "edit user configuration")
		private boolean user;
		
		@Option(name = "-g",
				aliases = {"--global"},
				required = false,
				depends = {"-c"},
				usage = "edit global configuration")
		private boolean global;
		
		@Option(name = "-?",
				aliases = "--help",
				required = false,
				forbids = {"-i", "-o", "-e", "-x", "-c", "-u", "-g"},
				help = true,
				usage = "print usage instructions")
		private boolean help;

		@Argument
		private List<String> args = new ArrayList<String> ();
		
		private CLIArgs() { }

		public List<Path> getInput() {
			return input;
		}

		public Path getOutput() {
			return output;
		}

		public Path getExec() {
			return exec;
		}

		public List<Path> getExcl() {
			return excl;
		}

		public boolean isVerbose() {
			return verbose;
		}
		
		public boolean isConfig() {
			return config;
		}
		
		public boolean isUser() {
			return user;
		}

		public boolean isGlobal() {
			return global;
		}

		public boolean isHelp() {
			return help;
		}

		public List<String> getArgs() {
			return args;
		}
		
		public void validateOptions() {
			List<Path> input = this.getInput();
			if (input != null) {
				
				for (Path p : input) {
					if (!p.toFile().exists()) {
						System.err.println("Error: Files not found: " + p.toString());
						System.exit(1);
					}
					if (!Files.isReadable(p)) {
						System.err.println("Error: Cannot read input file: " + p.toString());
						System.exit(1);
					}
				}
				
			}
			
			List<Path> excludes = this.getExcl();
			if (excludes != null) {
				
				for (Path p : excludes) {
					if (!Files.isReadable(p)) {
						System.err.println("Error: Cannot read exclude file: " + p.toString());
						System.exit(1);
					}
				}
				
			}
			
			Path output = this.getOutput();
			if (output != null) {
				if (output.toFile().exists()) {
					if (!Files.isWritable(output)) {
						System.err.println("Error: Cannot overwrite file: " + output.toString());
						System.exit(0);
					} else {
						System.out.print(output.toString() + " already exists. Is it okay to overwrite [Y/N]: ");
						if (!System.console().readLine().toLowerCase().equals("y")) {
							System.exit(0);
						}
					}
				} else {
					
					try {
						output.toFile().createNewFile();
					} catch (IOException e) {
						System.err.println("Could not create output file: " + output.toString());
						System.err.println(e.getMessage());
						System.exit(1);
					}
					
				}
				
				
			}
			
			Path words = this.getExec();
			if (words != null) {
				if (!Files.isExecutable(words)) {
					System.err.println("Error: " + words.toString() + " is not an executable");
					System.exit(1);
				}
			}
		}
		
		public UserInput getUserInput() {
			return new UserInput(this.getInput(), this.getArgs());
		}
	}

