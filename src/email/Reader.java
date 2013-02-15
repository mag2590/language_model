package email;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Reader {

	int uniLastIndex = -1;		// if both uni & bi has to be considered together, then only 1 lastIndex makes sense
	int biLastIndex  = -1;
	int authorLastIndex = -1;
	int termLastIndex = -1;
	boolean isStemmed = false;
	boolean isPunctuated = false;
	boolean isCapitalized = false;
	String term1 = "";
	String term2 = "";
	String unigram;
	String bigram;
	String author;
	
	HashMap<String,Integer> authors = new HashMap<String,Integer>();		//entity:ID
	HashMap<String,Integer> unigrams = new HashMap<String,Integer>();		//entity:ID
	HashMap<String,Integer> bigrams = new HashMap<String,Integer>();		//entity:ID
	HashMap<String,Integer> sentence_map = new HashMap<String,Integer>();	//entity:frequency
	
	//public String removePunctuations(String line);
	
	public String removeExtraSpaces(String line)
	{return null;} 
	
	
	public void readFile()
	{
		FileInputStream fstream;
		DataInputStream in;
		BufferedReader br;
		String str;
		FileWriter fw;
		
		try
		{
			fstream = new FileInputStream("train.txt");
			fw = new FileWriter("svmready_train.txt");
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            String[] temp;
            
            while ((str = br.readLine()) != null) 
            {
            	String line = str;//removeExtraSpaces(str);
            	temp = line.split(" ");
            	sentence_map = new HashMap<String, Integer>();
            	author = "";
            	
            	for(int c = 0 ; c < temp.length; c++)
            	{
            		if(c==0)
            		{
            			author = temp[0];
            			if(!authors.containsKey(author))
            			{
            				authorLastIndex++;
            				authors.put(author, authorLastIndex);
            			}
            			term1 = "";
            			term2 = "";
            			continue;
            		}
            		
            		term1 = term2;
            		term2 = temp[c];
//            		if(!term1.equals(""))
//            		{
            			bigram = term1 + "_" + term2;
            			if(!bigrams.containsKey(bigram))
            			{
//            				biLastIndex++;
//            				bigrams.put(bigram, biLastIndex);
            				termLastIndex++;
            				bigrams.put(bigram, termLastIndex);
            			}
//            		}
//            		if(!term2.equals(""))
//            		{
            			unigram = term2;
            			if(!unigrams.containsKey(unigram))
            			{
//            				uniLastIndex++;
//            				unigrams.put(unigram, uniLastIndex);
            				termLastIndex++;
            				unigrams.put(unigram, termLastIndex);
            			}
//            		}
            			
            		if(sentence_map.containsKey(unigram))
            		{
            			int count = sentence_map.get(unigram);
            			sentence_map.put(unigram, count+1);
            		}
            		else
            		{
            			sentence_map.put(unigram, 1);
            		}
            		
            		if(sentence_map.containsKey(bigram))
            		{
            			int count = sentence_map.get(bigram);
            			sentence_map.put(bigram, count+1);
            		}
            		else
            		{
            			sentence_map.put(bigram, 1);
            		}
            	
            	} // all words of sentence are seen
            	
            	// map sentence_map to SVM ready format
            	
            	Iterator itr = sentence_map.entrySet().iterator();
            	int id, freq;
            	String entity;
            	String svm_string = authors.get(author) + " ";
            	while(itr.hasNext())
            	{
            		Map.Entry me = (Map.Entry)itr.next();
            		entity = (String)me.getKey();
            		if(entity.contains("_"))
            			id = bigrams.get(entity);
            		else
            			id = unigrams.get(entity);
            		freq = (Integer)me.getValue();
            		
            		svm_string = svm_string + id + ":" + freq + " ";
            	}
            	
            	fw.write(svm_string);
            	
            } // processing for 1 sentence ends
            
            fw.close();
            
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		String temp[];
//		String sen = "haha ss dd  ff   yyy";
//		temp = sen.split(" ");
//		for(int i = 0; i < temp.length; i++)
//		{
//			System.out.println(i + ":" + temp[i]);
//		}
		Reader r = new Reader();
		r.readFile();
	}

}
