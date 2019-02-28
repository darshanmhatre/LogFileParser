import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogFileparser {
	public static List<ScanDetails> parseLog(String inputFile) {
		List<ScanDetails> scanDetails = new ArrayList<ScanDetails>();

		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			String line;

			while ((line = br.readLine()) != null) {

				if (line.contains("Total Elapsed Time:")) {
					ScanDetails sd = new ScanDetails();

					String pattern1 = "Total Elapsed Time:(.*)";
					Pattern r1 = Pattern.compile(pattern1);
					Matcher m1 = r1.matcher(line);
					while (m1.find()) {
						sd.setTime(m1.group(1));
					}
					while ((line = br.readLine()) != null && !line.contains("Validating XML")) {
						if (line.contains("Scan Memory Usage:")) {
							Metrics metrics = new Metrics();

							if (line.contains("Total LOC:")) {
								String pattern2 = "Total LOC: (.[^\\\\,]*)";
								Pattern r2 = Pattern.compile(pattern2);
								Matcher m2 = r2.matcher(line);
								while (m2.find()) {
									metrics.setTotalLOC(Integer.parseInt(m2.group(1)));
									metrics.setExecutableLOC(Integer.parseInt(m2.group(1)));
									metrics.setChargableLOC(Integer.parseInt(m2.group(1)));
								}
							}
							if (line.contains("Executable LOC:")) {
								String pattern2 = "Executable LOC: (.[^\\\\,]*)";
								Pattern r2 = Pattern.compile(pattern2);
								Matcher m2 = r2.matcher(line);
								while (m2.find()) {
									metrics.setExecutableLOC(Integer.parseInt(m2.group(1)));
									metrics.setChargableLOC(Integer.parseInt(m2.group(1)));
								}
							}
							if (line.contains("Chargeable LOC:")) {
								String pattern2 = "Chargeable LOC: (.[^\\\\,]*)";
								Pattern r2 = Pattern.compile(pattern2);
								Matcher m2 = r2.matcher(line);
								while (m2.find()) {
									metrics.setChargableLOC(Integer.parseInt(m2.group(1)));
								}
							}
							sd.setMetrics(metrics);

						}
						if (line.contains("Serializing to")) {

							String pattern3 = "(.*)\\/(.*)\\/(.*)\\/results\\/(.[^\\.]*)\\.";
							Pattern r3 = Pattern.compile(pattern3);
							Matcher m3 = r3.matcher(line);
							while (m3.find()) {
								sd.setLanguage(m3.group(4));
								sd.setInstanceId(m3.group(3));
								sd.setAppID(m3.group(2));
							}
						}
					}
					scanDetails.add(sd);
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("Invalid input path");
			System.exit(2);
		} catch (IOException e) {
			System.out.println("Error occured while trying to read input.");
			System.exit(3);
		}
		return scanDetails;
	}

	public static int minutesCalculation(String time) {
		String pattern = "(.*)Hours(.*)Minutes(.*)Seconds";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(time);
		int totalTime = 0;
		while (m.find()) {
			totalTime = Integer.parseInt(m.group(1).trim()) * 60 + Integer.parseInt(m.group(2).trim());
		}

		return totalTime;
	}

	public static void parseFolder(String folderPath, String outputPath) {
		File[] files = new File(folderPath).listFiles();
		if(files ==null || files.length==0) {
			System.out.println("No files found");
			System.exit(4);
		}
		StringBuilder sb = new StringBuilder();
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(outputPath));

		} catch (FileNotFoundException e) {
			
			System.out.println("Invalid output path.");
			System.exit(5);
		}
		sb.append("appId,instanceId,language,totalLOC,executableLOC,chargableLOC,time(minutes)");
		sb.append('\n');
		
		for (File file : files) {

			{

				List<ScanDetails> sc = parseLog(file.getAbsolutePath());
				for (ScanDetails sd : sc) {
					sb.append(sd.getAppID() + "," + sd.getInstanceId() + "," + sd.getLanguage() + ","
							+ sd.getMetrics().getTotalLOC() + "," + sd.getMetrics().getExecutableLOC() + ","
							+ sd.getMetrics().getChargableLOC() + "," + minutesCalculation(sd.getTime()));
					sb.append('\n');
				}

			}

		}
		writer.write(sb.toString());
		writer.close();
	}
}
