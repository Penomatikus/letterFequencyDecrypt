package decrpt_dud;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public final class Fileloder {
	
	public static String load(String path) throws IOException{
		File file = new File(path);
		StringBuilder builder = new StringBuilder();
		if ( file.exists() ){
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			while((line = reader.readLine()) != null)
				builder.append(line);
			reader.close();
		} else {
			System.err.println("File not found." + path);
		}
		return builder.toString();
	}
	
	public static List<String> load(String[] paths) throws IOException{
		List<String> tmp = new LinkedList<>();
		for ( int i = 0; i < paths.length; i++ ) 
			tmp.add(load(paths[i]));
		return tmp;		
	}
}
