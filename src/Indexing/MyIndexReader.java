package Indexing;

import Classes.Path;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MyIndexReader {
	//you are suggested to write very efficient code here, otherwise, your memory cannot hold our corpus...
	private String docNoIdMappingPath, postingPath;
	private HashMap <Integer, String> idDocNoMap;
	private int posting[][];

	public MyIndexReader( String type ) throws IOException {
		//read the index files you generated in task 1
		//remember to close them when you finish using them
		//use appropriate structure to store your index
		this.docNoIdMappingPath = (type.equals("trecweb")? Path.IndexWebDir:Path.IndexTextDir)+"docNoIdMapping";
		this.postingPath = (type.equals("trecweb")? Path.IndexWebDir:Path.IndexTextDir)+"Posting";
		BufferedReader idDocBufferedReader = new BufferedReader(new FileReader(docNoIdMappingPath));
		idDocNoMap = new HashMap<>();
		String line;
		while((line = idDocBufferedReader.readLine()) != null){
			String docId[] = line.split(",");
			idDocNoMap.put(Integer.parseInt(docId[0]),docId[1]);
		}
		idDocBufferedReader.close();
	}
	
	//get the non-negative integer dociId for the requested docNo
	//If the requested docno does not exist in the index, return -1
	public int GetDocid( String docno ) {
		for(Map.Entry entry : idDocNoMap.entrySet()){
			if(entry.getValue().equals(docno)){
				return (int)entry.getKey();
			}
		}
		return -1;
	}

	// Retrieve the docno for the integer docid
	public String GetDocno( int docid ) {
		if(idDocNoMap.containsKey(docid)){
			return idDocNoMap.get(docid);
		}
		return null;
	}
	
	/**
	 * Get the posting list for the requested token.
	 * 
	 * The posting list records the documents' docids the token appears and corresponding frequencies of the term, such as:
	 *  
	 *  [docid]		[freq]
	 *  1			3
	 *  5			7
	 *  9			1
	 *  13			9
	 * 
	 * ...
	 * 
	 * In the returned 2-dimension array, the first dimension is for each document, and the second dimension records the docid and frequency.
	 * 
	 * For example:
	 * array[0][0] records the docid of the first document the token appears.
	 * array[0][1] records the frequency of the token in the documents with docid = array[0][0]
	 * ...
	 * 
	 * NOTE that the returned posting list array should be ranked by docid from the smallest to the largest. 
	 * 
	 * @param token
	 * @return
	 */
	public int[][] GetPostingList( String token ) throws IOException {
		return posting;
	}

	// Return the number of documents that contains the token.
	public int GetDocFreq( String token ) throws IOException {
		BufferedReader postingBufferedReader = new BufferedReader(new FileReader(postingPath));
		HashMap<Integer,Integer> idFrequencyMap = new HashMap<>();
		String line;
		while((line = postingBufferedReader.readLine()) != null){
			String term[] = line.split("\t");
			if(term[0].equals(token)){
				for(int j = 1; j < term.length; j++){
					String docAndFrequency[] = term[j].split(",");
					idFrequencyMap.put(Integer.parseInt(docAndFrequency[0]), Integer.parseInt(docAndFrequency[1]));
				}
			}
		}
		int size = idFrequencyMap.size();
		int i = 0;
		posting = new int [size][2];
		if(size > 0){
			for(int key : idFrequencyMap.keySet()){
				posting[i][0] = key;
				posting[i][1] = idFrequencyMap.get(key);
				i++;
			}
		}
		postingBufferedReader.close();
		return size;
	}
	
	// Return the total number of times the token appears in the collection.
	public long GetCollectionFreq( String token ) throws IOException {
		int res = 0;
		for(int i = 0; i < posting.length; i++){
			res += posting[i][1];
		}
		return res;
	}
	
	public void Close() throws IOException {

	}
	
}