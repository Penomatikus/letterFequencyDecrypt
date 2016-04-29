package decrpt_dud;

import java.io.IOException;

public class dud_main {

	public static void main(String[] args) {
		if ( args.length != 2)
			System.out.println("Usage (without < > ): java -jar LetterFreqency.jar <path to encrypted file> <path to store decrypted file>");
		else
			rockOn(args[0], args[1]);
	}
	
	private static void rockOn(String in, String out){
		try {
			DecrypterFreq encrypter = new DecrypterFreq(Fileloder.load(in), out);
			encrypter.output();
			encrypter.toFile();
			encrypter.changes();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
