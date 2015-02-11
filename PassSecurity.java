import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Java program to test the password generator and make sure the passwords are
 * acceptable. 
 * The password is valid if it contains
 *     1. At least one vowel. 
 *     2. No three consecutive vowels or three consecutive consonants. 
 *     3. No two consecutive occurrences of the same letter, except for 'ee' or 'oo'.
 * 
 * Input: A file with one or more potential passwords, one per
 * line, followed by a line containing only the word 'end' that signals the end
 * of the file.
 * 
 * Output: A file saying whether or not each password in the input is acceptable
 * 
 * @author anusha
 *
 */
public class ValidPassword {

	/**
	 * Checks for the occurrence of at least one vowel in the password
	 * 
	 * @param input
	 * @return true if the input string has at least one vowel
	 */
	public boolean containsOneVowel(String input) {
		for (char ch : input.toCharArray()) {
			if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u')
				return true;
		}
		return false;
	}

	/**
	 * Checks for the presence of three consecutive vowels or three consecutive
	 * consonants in the input password. Keeps two counters for vowels and
	 * consonants. Checks if a particular character is a vowel and increments
	 * vowel counter if it is; else increments consonant counter if it is not a
	 * vowel
	 * 
	 * @param password
	 * @return true if the consonant and vowel counters value is 3
	 */
	public boolean threeConsecVorC(String input) {
		int vowel = 0;
		int cons = 0;
		boolean prevIsVowel = true;
		for (char ch : input.toCharArray()) {
			if (isVowel(ch)) {
				if (prevIsVowel) {
					vowel++;
				} else {
					vowel = 1;
					prevIsVowel = true;
				}
			} else {
				if (prevIsVowel) {
					cons = 1;
					prevIsVowel = false;
				} else {
					cons++;
				}
			}

			if (vowel == 3 || cons == 3) // return true if the consecutive vowel
				// or consecutive consonant count is
				// 3
				return true;
		}
		return false;
	}

	/**
	 * Checks if a particular character is a vowel
	 * 
	 * @param c
	 * @return true if that character is a vowel
	 */
	public static boolean isVowel(char ch) {
		return "AEIOUaeiou".indexOf(ch) != -1;
	}

	/**
	 * Checks for two consecutive occurrences of the same letter, except 'ee' or
	 * 'oo'
	 * 
	 * @param input
	 * @return true if two consecutive occurrences of the same letter found
	 */
	public boolean sameLetter(String input) {
		char prevL;
		char nextL;
		if (input.length() < 2)
			return false;
		for (int i = 0; i < input.length() - 1; i++) {
			prevL = input.charAt(i);
			nextL = input.charAt(i + 1);
			if (prevL == nextL && !check(prevL, nextL))
				return true;
		}
		return false;
	}

	/**
	 * To allow "ee" and "oo" in the passwords
	 * 
	 * @param prevL
	 * @param nextL
	 * @return true if appending previous and next letters makes "ee" or "oo"
	 */
	private boolean check(char prevL, char nextL) {		StringBuffer twoConsec = new StringBuffer();
		twoConsec.append(prevL);
		twoConsec.append(nextL);
		if (twoConsec.toString().equals("ee")
				|| twoConsec.toString().equals("oo"))
			return true;
		return false;
	}

	public static void main(String[] args) throws FileNotFoundException,
			UnsupportedEncodingException {

		ValidPassword passwordVerify = new ValidPassword();

		File file = new File("say.in.txt");
		PrintWriter writer = new PrintWriter("say.out.txt", "UTF-8");

		Scanner reader = new Scanner(new FileReader(file));
		ArrayList<String> words = new ArrayList<String>();
		String theWord;
		while (reader.hasNext()) {
			theWord = reader.next();
			if (theWord.equals("end")) {
				break;
			}
			words.add(theWord);
		}

		for (String input : words) {
			if (passwordVerify.containsOneVowel(input)
					&& !passwordVerify.threeConsecVorC(input)
					&& !passwordVerify.sameLetter(input))
				writer.println("<" + input + ">" + " is acceptable\n");
			else
				writer.println("<" + input + ">" + " is not acceptable\n");
		}
		reader.close();
		writer.close();
	}
}