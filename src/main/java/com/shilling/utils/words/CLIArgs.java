import org.kohsuke.args4j.Option;

public class CLIArgs {
		@Option(name = "-i", aliases = {"--input"}, metaVar = "FILE", usage = "input file")
		private String input;
		
		@Option(name = "-o", aliases = {"--output"}, metaVar = "FILE", usage = "output file")
		private String output;
		
		@Option(name = "-e", aliases = {"--exec"}, metaVar = "FILE", usage = "exec path")
		private String exec;
		
		public void setInput(String val) {
			input = new String (val);
		}
		
		public String getInput() {
			return input;
		}
		
		public void setOutput(String val) {
			output = new String (val);
		}
		
		public String getOutput() {
			return output;
		}
		
		public void setExec(String val) {
			exec = new String (val);
		}
		
		public String getExec() {
			return exec;
		}
	}

