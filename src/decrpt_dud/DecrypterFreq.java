package decrpt_dud;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Provides an object to decrypt a string via language specific letter frequency,<br>
 * which was encrypted with another alphabet,  <br>
 * Requirement: 
 * <li> The encrypted string stores a German language conform text
 * <li> The encrypted string do only have upper case letters
 * <li> The encrypted string do only user [A-Z]
 * @author Stefan Jagdmann <br> <a href="https://github.com/Penomatikus>[at]GitHub</a>
 *
 */
public class DecrypterFreq {
	
	/**
	 * Stores the frequency of German letters
	 */
	private Map<String, Double> frequency = new TreeMap<>();
	/**
	 * Holds the translation table 
	 */
	private Map<String, String> translateMap = new TreeMap<>();
	/**
	 * Stores the frequency of the letters in the encrypted file
	 */
	private Map<String, Double> crypted = new TreeMap<>();
	/**
	 * The German alphabet ( without: ä,ö,ü,ß )
	 */
	private String[] alphabetInit = new String[]{ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	/**
	 * The German alphabet ( without: ä,ö,ü,ß ) as list
	 */
	private List<String> alphabetList = new ArrayList<>(Arrays.asList(alphabetInit));
	/**
	 * The encrypted file as String
	 */
	private String file;
	/*
	 * Amount of letters within the encrypted file
	 */
	private int actualSize;
	/*
	 * String to write into a file, holding the decrypted file
	 */
	private String out;
	/**
	 * Path to store decrypted file
	 */
	private String outputPath;
	
	/**
	 * Provides an object to decrypt a string via language specific letter frequency,<br>
	 * which was encrypted with another alphabet,  <br>
	 * Requirement: 
	 * <li> The encrypted string stores a German language conform text
	 * <li> The encrypted string do only have upper case letters
	 * <li> The encrypted string do only user [A-Z]
	 */
	public DecrypterFreq(String fileAsString, String outputPath) {
		this.file = fileAsString;
		this.out = "Encrypted: \n" + file + "\n";
		this.outputPath = outputPath;
		fillFreq();
		letterCountOnCrypt();
		buildTranslateTable();
		decrypt();
	}
	
	/**
	 * Fills {@link DecrypterFreq#frequency}<br>
	 * SRC: <a href="http://www.sttmedia.de/buchstabenhaeufigkeit-deutsch">sttmedia.de</a>
	 */
	private void fillFreq(){
		frequency.put("A", 5.58);  frequency.put("B", 1.96);  frequency.put("C", 3.16); frequency.put("D", 4.98); 
		frequency.put("E", 16.93); frequency.put("F", 1.49);  frequency.put("G", 3.02); frequency.put("H", 4.98); 
		frequency.put("I", 8.02);  frequency.put("J", 0.24);  frequency.put("K", 1.32); frequency.put("L", 3.60); 
		frequency.put("M", 2.55);  frequency.put("N", 10.53); frequency.put("O", 2.24); frequency.put("P", 0.67); 
		frequency.put("Q", 0.02);  frequency.put("R", 6.89);  frequency.put("S", 6.42); frequency.put("T", 5.79);
		frequency.put("U", 3.83);  frequency.put("V", 0.84);  frequency.put("W", 1.78); frequency.put("X", 0.05);
		frequency.put("Y", 0.05);  frequency.put("Z", 1.21); 
	}

	/**
	 * Fills {@link DecrypterFreq#crypted}<br>
	 * Every character in the encrypted file, which appears also in {@link DecrypterFreq#alphabetList},
	 * will be added to {@link DecrypterFreq#crypted} with 1.0 as value. If the character is already part of the map,
	 * its value will be increased by one. Moreover, {@link DecrypterFreq#actualSize} will be increased by one, if
	 * the current character is part of {@link DecrypterFreq#alphabetList}. <br>
	 * At the end, every letter of the German alphabet, which is not in  {@link DecrypterFreq#crypted} by now, will be 
	 * added with 0.0 as value. 
	 */
	private void letterCountOnCrypt(){
		String tmp = "";
		int size = file.length();
		for(int i = 0; i < size; i++){
			tmp = String.valueOf(file.charAt(i));
			if ( alphabetList.contains(tmp) ){
				if (!crypted.containsKey(tmp)) {
					crypted.put(tmp, 1.0);
				} else {
					double newVAl = crypted.get(tmp) + 1.0;
					crypted.put(tmp, newVAl);
				}
				actualSize++;
			}
		}
		//adding letters, which were not in the encrypted file
		for ( int i = 0; i < alphabetList.size(); i++)
			if (!crypted.containsKey(alphabetList.get(i)))
				crypted.put(alphabetList.get(i), 0.0);
	}
	
	/**
	 * Sorts a given map by value from less to high. <br>
	 * Example: <br>
	 * Map unsorted: (A,10), (B,8), (C, 19), (D, 1) <br>
	 * Map sorted: (D, 1), (B,8), (A,10), (C, 19)
	 * @param map	The map to sort 
	 * @return	A new sortD map
	 */
	private Map<String, Double> sortD(Map<String, Double> map){
		// sort values from map via Collections ( with new list of freqs entry set )
		List<Map.Entry<String, Double>> listTmp = new LinkedList<>(map.entrySet());
		Collections.sort(listTmp, new Comparator<Map.Entry<String, Double>>() {
			@Override
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
		// convert the list of sorted entry sets  into a new map 
		Map<String, Double> sorted = new LinkedHashMap<>();
		for ( Iterator<Map.Entry<String, Double>> iterator = listTmp.iterator(); iterator.hasNext();){
			Map.Entry<String, Double> entry = iterator.next();
			sorted.put(entry.getKey(), entry.getValue());			
		}
		return sorted;
	}
	
	//calculate frequency of letters from crypt
	/**
	 * Calculates the letter frequency of a given map. <br>
	 * ( At least just from {@link DecrypterFreq#crypted} because 
	 * {@link DecrypterFreq#actualSize} is just for this map )
	 * @param map	The map to calculate the letter frequency of its keys
	 * @return	a new map holding the letter frequency
	 */
	@Deprecated
	private Map<String, Double> frequencyCrypt(Map<String, Double> map){
		Map<String, Double> percentage = new LinkedHashMap<>();
		for(String key : map.keySet())
			percentage.put(key, ((double)map.get(key) / (double)actualSize)*100);
		return percentage;
	}
	
	/**
	 * Builds {@link DecrypterFreq#translateMap}<br>
	 * There for it generates two {@link List}s.<br>
	 * The first list will get the entry set of a sorted list with the added letter frequency of {@link DecrypterFreq#crypted} and <br>
	 * The second list will get the entry set of a sorted list of {@link DecrypterFreq#frequency} <br>
	 * Then for every index of the first list, the {@link DecrypterFreq#translateMap} will be filled with the current index of
	 * list one as key and the current index of list two as value
	 */
	private void buildTranslateTable(){
		List<Entry<String, Double>> tmp1 = new LinkedList<>(frequencyCrypt(sortD(crypted)).entrySet());
		List<Entry<String, Double>> tmp2 = new LinkedList<>(sortD(frequency).entrySet());
		int size = tmp1.size();
		for( int i = 0; i < size; i++)
			translateMap.put(tmp1.get(i).getKey(), tmp2.get(i).getKey());
	}
	
	/**
	 * Generates a table view of {@link DecrypterFreq#translateMap} for {@link DecrypterFreq#out}
	 */
	public void table(){
		out = out.concat(
			  "\nCrypted:| A | B | C | D | E | F | G | H | I | J | K | L | M | N | O | P | Q | R | S | T | U | V | W | X | Y | Z |\n"+
			  "-----------------------------------------------------------------------------------------------------------------\n"+
			  "Normal: | " + 
			  translateMap.get("A") + " | " + translateMap.get("B") + " | " + translateMap.get("C") + " | " + translateMap.get("D") + " | " +
			  translateMap.get("E") + " | " + translateMap.get("F") + " | " + translateMap.get("G") + " | " + translateMap.get("H") + " | " +
			  translateMap.get("I") + " | " + translateMap.get("J") + " | " + translateMap.get("K") + " | " + translateMap.get("L") + " | " +
			  translateMap.get("M") + " | " + translateMap.get("N") + " | " + translateMap.get("O") + " | " + translateMap.get("P") + " | " +
			  translateMap.get("Q") + " | " + translateMap.get("R") + " | " + translateMap.get("S") + " | " + translateMap.get("T") + " | " +
			  translateMap.get("U") + " | " + translateMap.get("V") + " | " + translateMap.get("W") + " | " + translateMap.get("X") + " | " +
			  translateMap.get("Y") + " | " + translateMap.get("Z") + " |\n");
	}
	
	/**
	 * Prints the output of the decryption and also adds it to {@link DecrypterFreq#out}
	 */
	public void output(){
		table();
		System.out.println(out);
	}
	
	/**
	 * Decrypts the file encrypted file with use of the {@link DecrypterFreq#translateMap} <br>
	 * Creates a tmp string of {@link DecrypterFreq#file}, takes the size of {@link DecrypterFreq#alphabetList}, <br>
	 * loops for that size and for every loop, it replaces all letters of the tmp string with the current index of  
	 * {@link DecrypterFreq#alphabetList} with also {@link DecrypterFreq#alphabetList} as key of the 
	 * {@link DecrypterFreq#translateMap} ( to lower case );
	 * @throws IndexOutOfBoundsException
	 */
	private void decrypt() throws IndexOutOfBoundsException {
		String tmp = file;
		int size = alphabetList.size();
		for(int i = 0; i < size; i++){
			String letter = alphabetList.get(i);
			tmp = tmp.replaceAll(letter, translateMap.get(letter).toLowerCase());
		}
		out = out.concat("\n\nDecrypted: \n" + tmp + "\n\nTranslate table: ");

	}
	
	/**
	 * Safes the output on a NSA server
	 * @throws IOException
	 */
	public void toFile() throws IOException{
		FileWriter fw = new FileWriter(outputPath + ".txt", true);
		fw.write(out);
		fw.close();
	}
	
	/**
	 * Gives the user the ability to make changes to {@link DecrypterFreq#translateMap} and <br>
	 * re-run the decryption.
	 * @throws IOException
	 */
	public void changes() throws IOException{
		String tmp = file;
		String exit = "";
		while(!exit.equals("exit")){
			System.out.println("\n------------\nDo you want to make changes to the table? ( help|exit )>\n");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			exit = br.readLine();
			if ( exit.toLowerCase().equals("help"))
				help();
			else {
				out = "Encrypted: \n" + tmp;
				update(exit);
			}
		}	
	}
	
	/**
	 * Updates the {@link DecrypterFreq#translateMap} with new values. <br>
	 * Takes the first char of the given String as key for {@link DecrypterFreq#translateMap}, 
	 * safes the value of it into a new String "newVal". Then searching the entry set of 
	 * {@link DecrypterFreq#translateMap} for a key with the second char of the input string
	 * as value. At least, replaces this value with "newVal" and replaces the value of the
	 * first key with the second char of the input string. 
	 * 
	 * @param change	String-identifier to get changing information from
	 * @throws IOException	any io error
	 */
	private void update(String change) throws IOException{
		String newVal1 = translateMap.get(String.valueOf(change.charAt(0)));
		for(Entry<String, String> entry : translateMap.entrySet())
			if(entry.getValue().equals(String.valueOf(change.charAt(1)))){
				translateMap.get(entry.getKey());
				translateMap.put(entry.getKey(), newVal1);
			}
		translateMap.put(String.valueOf(change.charAt(0)), String.valueOf(change.charAt(1)));
		System.out.println("\n\n\n");
		decrypt();
		output();
		toFile();
	}
	
	/**
	 * Prints the help dialog
	 */
	private void help(){
		System.out.println("To replace a letter in the \"Normal:\" row use the following "
					     + "syntax: BA<enter-key>\n\nTL;DR:\tFirst letter is the key in the \"Crypted\" row\n"
					     + "\tSecond letter is the the new value for that key(\"Normal\" row).\n\t(But how? - Take a look at the example )"
					     + "\n\nExample:\nThis would change the following table:\n"
					     + "A | B | C | D | E | F | G | H | I | J | K | L | M | N | O | ...\n"
					     + "------------------------------------------------------------\n"
					     + "A | B | H | D | E | F | G | C | I | J | L | K | M | O | N | ...\n------\n\n...to\n\n"
					     + "A | B | C | D | E | F | G | H | I | J | K | L | M | N | O | ...\n"
					     + "------------------------------------------------------------\n"
					     + "B | A | H | D | E | F | G | C | I | J | L | K | M | O | N | \n------\n\n"
					     + "In case we have the decrypted word \"CBKKN\", the first table\ncould encrypt it "
					     + "in to \"HBLLO\". However, we changed the\ntable with BA, the program can encrypt \"CBKKN\""
					     + "in to \"HELLO\"\n\n");
	}
}
