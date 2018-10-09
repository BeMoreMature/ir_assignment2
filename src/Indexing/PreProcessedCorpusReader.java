package Indexing;

import Classes.Path;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PreProcessedCorpusReader {
	private BufferedReader bufferedReader;
	
	
	public PreProcessedCorpusReader(String type) throws IOException {
		// This constructor opens the pre-processed corpus file, Path.ResultHM1 + type
		// You can use your own version, or download from http://crystal.exp.sis.pitt.edu:8080/iris/resource.jsp
		// Close the file when you do not use it any more
		File file = new File(Path.ResultHM1 + type);
		this.bufferedReader = new BufferedReader(new FileReader(file));
	}
	

	public Map<String, String> NextDocument() throws IOException {
		// read a line for docNo, put into the map with <"DOCNO", docNo>
		// read another line for the content , put into the map with <"CONTENT", content>
		String docNo, content;
		HashMap<String, String> map = new HashMap<>();
		if((docNo = bufferedReader.readLine()) != null){
			map.put("DOCNO", docNo);
			content = bufferedReader.readLine();
			map.put("CONTENT", content);

			return map;
		}else {
			bufferedReader.close();
			return null;
		}

	}

}
