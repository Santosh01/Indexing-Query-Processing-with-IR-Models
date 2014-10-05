import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class MyQueryProcessor_new {

	private BufferedReader oBufferedReader = null;
	private BufferedWriter oBufferedWriter = null;
	Pattern oSpecialCharMatcher = Pattern.compile("[^A-Za-z0-9]");
	HashMap<String, Integer> stopwordsMap = new HashMap<String, Integer>();
	Hashtable<String, Double> oTermVsTFIDF = new Hashtable<String, Double>();
	int totalDocs = 0;

	public static void main(String[] args) {
		MyQueryProcessor_new oMQP = new MyQueryProcessor_new();
		try {
			String stopwords[] = { "a", "about", "above", "after", "again",
					"against", "all", "am", "an", "and", "any", "are",
					"aren't", "as", "at", "be", "because", "been", "before",
					"being", "below", "between", "both", "but", "by", "can't",
					"cannot", "could", "couldn't", "did", "didn't", "do",
					"does", "doesn't", "doing", "don't", "down", "during",
					"each", "few", "for", "from", "further", "had", "hadn't",
					"has", "hasn't", "have", "haven't", "having", "he", "he'd",
					"he'll", "he's", "her", "here", "here's", "hers",
					"herself", "him", "himself", "his", "how", "how's", "i",
					"i'd", "i'll", "i'm", "i've", "if", "in", "into", "is",
					"isn't", "it", "it's", "its", "itself", "let's", "me",
					"more", "most", "mustn't", "my", "myself", "no", "nor",
					"not", "of", "off", "on", "once", "only", "or", "other",
					"ought", "our", "ours", "ourselves", "out", "over", "own",
					"same", "shan't", "she", "she'd", "she'll", "she's",
					"should", "shouldn't", "so", "some", "such", "than",
					"that", "that's", "the", "their", "theirs", "them",
					"themselves", "then", "there", "there's", "these", "they",
					"they'd", "they'll", "they're", "they've", "this", "those",
					"through", "to", "too", "under", "until", "up", "very",
					"was", "wasn't", "we", "we'd", "we'll", "we're", "we've",
					"were", "weren't", "what", "what's", "when", "when's",
					"where", "where's", "which", "while", "who", "who's",
					"whom", "why", "why's", "with", "won't", "would",
					"wouldn't", "you", "you'd", "you'll", "you're", "you've",
					"your", "yours", "yourself", "yourselves", "relevant",
					"document", "documents", "must", "will", "also", "may",
					"using", "can", "without", "like", "use", "used", "uses",
					"information", "contain", "identify", "include",
					"specific", "involved", "discuss", "discussing",
					"reference", "references" + "" + ".000" + "." };
			for (String i : stopwords) {
				oMQP.stopwordsMap.put(i, 1);
			}
			oMQP.processQueries(
					"F:\\MS Degree Course\\Semester - I\\Information retrieval\\Project-2\\trec_eval_latest\\trec-demo-master\\test-data\\topics.301-450",
					"F:\\MS Degree Course\\Semester - I\\Information retrieval\\Project-2\\trec_eval_latest\\trec-demo-master\\result\\topics.301-450_narr.out");
			oMQP.mergeFiles(
					"F:\\MS Degree Course\\Semester - I\\Information retrieval\\Project-2\\trec_eval_latest\\trec-demo-master\\test-data\\title-queries.301-450",
					"F:\\MS Degree Course\\Semester - I\\Information retrieval\\Project-2\\trec_eval_latest\\trec-demo-master\\result\\topics.301-450_narr.out");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void processQueries(String strInputFilePath, String strOutputFilePath)
			throws IOException {

		oBufferedReader = new BufferedReader(new FileReader(strInputFilePath));
		oBufferedWriter = new BufferedWriter(new FileWriter(strOutputFilePath));
		calcuateTFIDF(oBufferedReader);
		oBufferedReader.close();
		oBufferedReader = new BufferedReader(new FileReader(strInputFilePath));
		String strLine = null;
		String strNumber = null;
		Hashtable<String, Double> oTempTermVsTFIDF = new Hashtable<String, Double>();
		while ((strLine = oBufferedReader.readLine()) != null) {
			if (strLine.trim().length() == 0)
				continue;
			if (strLine.trim().equalsIgnoreCase("</top>")) {
				writeTermsToFile(strNumber, oTempTermVsTFIDF);
			} else if (strLine.trim().startsWith("<num>")) {
				strNumber = strLine.substring(strLine.indexOf(":") + 1).trim();
			} else if (strLine.trim().startsWith("<title>")
					|| !strLine.trim().startsWith("<")) {
				if (strLine.startsWith("<title>"))
					strLine = strLine.substring(8).trim().toLowerCase();

				for (String strTerm : strLine.split(" ")) {
					if (oTermVsTFIDF.containsKey(strTerm))
						oTempTermVsTFIDF
								.put(strTerm, oTermVsTFIDF.get(strTerm));

				}

			}
		}
		System.out.println(oTempTermVsTFIDF);
		oBufferedReader.close();
		oBufferedWriter.close();
	}

	public void calcuateTFIDF(BufferedReader oBufferedReader)
			throws IOException {
		Hashtable<String, Integer> oTermVsTF = new Hashtable<String, Integer>();
		String strLine = null;
		while ((strLine = oBufferedReader.readLine()) != null) {
			if (strLine.trim().startsWith("<top>"))
				totalDocs++;
			if (strLine.startsWith("<") || strLine.trim().length() == 0)
				continue;
			String[] strArr = strLine.toLowerCase().split(" ");
			for (int i = 0; i < strArr.length; ++i) {
				if (strArr[i].matches("[a-zA-Z]+")
						&& !stopwordsMap.containsKey(strArr[i])) {
					if (oTermVsTF.containsKey(strArr[i]))
						oTermVsTF.put(strArr[i], oTermVsTF.get(strArr[i]) + 1);
					else
						oTermVsTF.put(strArr[i], 1);
				}
			}
		}
		for (String strKey : oTermVsTF.keySet()) {
			double tfidf = oTermVsTF.get(strKey)
					* Math.log(totalDocs / oTermVsTF.get(strKey));
			oTermVsTFIDF.put(strKey, tfidf);
		}
	}

	public void writeTermsToFile(String strNumber,
			Hashtable<String, Double> oTermVsTFIDF) throws IOException {
		String strKey = null;
		Double maxValue = Double.MIN_VALUE;
		String strTerms = "";
		int len = oTermVsTFIDF.size() < 5 ? oTermVsTFIDF.size() : 5;

		for (int i = 0; i < len; ++i) {
			for (Map.Entry<String, Double> entry : oTermVsTFIDF.entrySet()) {
				if (entry.getKey().length() > 2
						&& entry.getValue() > maxValue
						&& !stopwordsMap.containsKey(entry.getKey()
								.toLowerCase())
						&& entry.getKey().matches("[a-zA-Z]+")) {
					maxValue = entry.getValue();
					strKey = entry.getKey();
				}
			}
			strTerms = strTerms + " " + strKey;
			oTermVsTFIDF.remove(strKey);
			strKey = null;
			maxValue = Double.MIN_VALUE;
		}
		oBufferedWriter.write(strNumber + " " + strTerms.trim() + "\n");
		System.out.println(strTerms);
	}

	public void mergeFiles(String strTitleFilePath, String strQPOutputFileName)
			throws IOException {
		HashMap<Integer, String> oQPOutputHashMap = loadFile(strQPOutputFileName);
		BufferedWriter oWriter = new BufferedWriter(new FileWriter(
				strTitleFilePath + "_Merged"));
		BufferedReader oTitleReader = new BufferedReader(new FileReader(
				strTitleFilePath));
		String strLine = "";
		while ((strLine = oTitleReader.readLine()) != null) {
			String[] Arr = strLine.split(" ");
			int number = Integer.parseInt(Arr[0]);
			HashSet<String> oHash = new HashSet<String>();
			for (int i = 1; i < Arr.length; ++i)
				if (Arr[i] != null && !Arr[i].trim().equals("")
						&& Arr[i].trim().length() > 2)
					oHash.add(Arr[i]);
			if (oQPOutputHashMap.containsKey(number)) {
				Arr = oQPOutputHashMap.get(number).split(" ");
				for (String str : Arr)
					if (str != null && !str.trim().equals("")
							&& str.trim().length() > 2)
						oHash.add(str);
				oQPOutputHashMap.remove(number);
			}
			if (oHash.size() > 0) {
				String strTitle = "";
				Iterator<String> oIter = oHash.iterator();
				while (oIter.hasNext())
					strTitle += " " + oIter.next();
				oWriter.write(number + " " + strTitle.trim() + "\n");
			}
		}
		if (oQPOutputHashMap.size() > 0) {
			for (Map.Entry<Integer, String> entry : oQPOutputHashMap.entrySet())
				oWriter.write(entry.getKey() + " " + entry.getValue().trim()
						+ "\n");
		}
		oTitleReader.close();
		oWriter.close();
	}

	public HashMap<Integer, String> loadFile(String strFilePath)
			throws IOException {
		HashMap<Integer, String> oHashMap = new HashMap<Integer, String>();
		BufferedReader oFileReader = new BufferedReader(new FileReader(
				strFilePath));
		String strLine = "";
		while ((strLine = oFileReader.readLine()) != null) {
			int number = Integer.parseInt(strLine.split(" ")[0]);
			if (!oHashMap.containsKey(number)) {
				oHashMap.put(number,
						strLine.substring(strLine.split(" ")[0].length()));
			}
		}
		return oHashMap;
	}
}