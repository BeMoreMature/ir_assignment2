package Indexing;

import Classes.Path;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MyIndexWriter {
	// I suggest you to write very efficient code here, otherwise, your memory cannot hold our corpus...
	private HashMap<Integer, String> idDocNoMap;
	private HashMap<String, HashMap<Integer,Integer>> tokenIdFrequencyMap;
	private int index = 1;
	private  int blockNo = 1;
	private String type;
	private BufferedWriter idDocBufferedWriter;

	public MyIndexWriter(String type) throws IOException {
		// This constructor should initiate the FileWriter to output your index files
		// remember to close files if you finish writing the index
		this.type = type;
		this.idDocNoMap = new HashMap<>();
		this.tokenIdFrequencyMap = new HashMap<>();
		this.idDocBufferedWriter = new BufferedWriter(new FileWriter((type.equals("trecweb")? Path.IndexWebDir:Path.IndexTextDir)+"docNoIdMapping"));
	}
	
	public void IndexADocument(String docno, String content) throws IOException {
		// you are strongly suggested to build the index by installments
		// you need to assign the new non-negative integer docId to each document, which will be used in MyIndexReader
		idDocNoMap.put(index, docno);
		String [] temp = content.split(" ");
		HashMap<Integer,Integer> posting = new HashMap<>();

		for(String token : temp){
			if(tokenIdFrequencyMap.containsKey(token)){
				posting = tokenIdFrequencyMap.get(token);
				if(posting.containsKey(index)){
					posting.put(index, posting.get(index)+1);
				}else{
					posting.put(index, 1);
				}
			}
			else{
				posting = new HashMap<>();
				posting.put(index, 1);
				tokenIdFrequencyMap.put(token, posting);
			}
		}

		index = index + 1;
		if(index % 60000 == 0){
			saveBlock();
		}
	}
	
	public void Close() throws IOException {
		// close the index writer, and you should output all the buffered content (if any).
		// if you write your index into several files, you need to fuse them here.

		saveBlock();
		fuse();
		idDocBufferedWriter.close();
	}

	public void saveBlock() throws IOException {
		for(Integer key : idDocNoMap.keySet()){
			idDocBufferedWriter.write(key+","+idDocNoMap.get(key)+"\n");
		}

		BufferedWriter tempBufferedWriter = new BufferedWriter(new FileWriter((type.equals("trecweb")? Path.IndexWebDir:Path.IndexTextDir)+".temp"+blockNo));
		for(String key : tokenIdFrequencyMap.keySet()){
			tempBufferedWriter.write(key + "\t");
			HashMap<Integer, Integer> map = tokenIdFrequencyMap.get(key);
			for(Map.Entry entry : map.entrySet()){
				tempBufferedWriter.write(entry.getKey()+","+entry.getValue()+"\t");
			}
			tempBufferedWriter.write("\n");
		}
		blockNo++;
		idDocNoMap.clear();
		tokenIdFrequencyMap.clear();
		tempBufferedWriter.close();
	}

	public void fuse() throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter((new FileWriter((type.equals("trecweb")? Path.IndexWebDir:Path.IndexTextDir)+"Posting")));
		BufferedWriter termColFrequencybufferedWriter = new BufferedWriter(new FileWriter((type.equals("trecweb")? Path.IndexWebDir:Path.IndexTextDir)+"Dictionary Term"));
		for(int i = 1; i < blockNo; i++){
			BufferedReader bufferedReader = new BufferedReader(new FileReader((type.equals("trecweb")? Path.IndexWebDir+".temp":Path.IndexTextDir+".temp")+i));
			String line;
			while((line = bufferedReader.readLine()) != null){
				bufferedWriter.write(line+"\n");

				String term[] = line.split("\t");
				String key = term[0];
				int count = 0;
				for(int j = 1; j < term.length; j++){
					String docAndFrequency[] = term[j].split(",");
					count += Integer.parseInt(docAndFrequency[1]);
				}
				termColFrequencybufferedWriter.write(key+" "+count+"\n");
			}
			bufferedReader.close();
			File file = new File((type.equals("trecweb")? Path.IndexWebDir+".temp":Path.IndexTextDir+".temp")+i);
			file.delete();
		}
		bufferedWriter.close();
		termColFrequencybufferedWriter.close();
	}
}
