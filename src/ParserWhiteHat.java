

public class ParserWhiteHat {

	public static void main(String[] args) {
		if(args.length < 2) {
			System.out.println("Enter paths for input folder and output folder");
			System.exit(1);
		}
		String inputPath = args[0];
		String outputFile = args[1]+"/results.csv";
		System.out.println("Parser running.....");
		LogFileparser.parseFolder(inputPath,outputFile);
		System.out.println("Parsing completed. Please check "+ outputFile);
	}
}
