package utiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FileHandling {

	public static void writeArrayToFile(double[][] array, String pathname, DecimalFormat df) throws IOException {
		BufferedWriter file = new BufferedWriter(new FileWriter(pathname));
		for (int i = 0; i < array.length; ++i) {
			for (int j = 0; j < array[0].length; ++j) {
				file.write(" " + df.format(array[i][j]));
			}
			file.newLine();
		}
		file.flush();
		file.close();
	}

	public static double[][] readArrayFromFile(String pathname) throws IOException {
		int numOfRows = 0;
		int i = 0;
		int numOfCol = 0;
		int j = 0;
		double[] data = new double[25];
		// array list for conveniently appending the parsed rows
		// do not use a LinkedList here: bad performance
		ArrayList<double[]> al = new ArrayList<double[]>();
		// count lines that contain Tecplot key words
		BufferedReader br = new BufferedReader(new FileReader(pathname));
		int headerLines = -1;
		String line;
		do {
			line = br.readLine();
			headerLines += 1;
		} while (line.contains("TITLE") || line.contains("VARIABLES") || line.contains("ZONE"));
		br.close();
		br = new BufferedReader(new FileReader(pathname));
		// skip the header
		for (int h = 0; h < headerLines; h++)
			br.readLine();
		// configuring StreamTokenizer
		StreamTokenizer st = new StreamTokenizer(br);
		st.eolIsSignificant(true);
		st.resetSyntax();
		st.wordChars('0', '9');
		st.wordChars('-', '-');
		st.wordChars('+', '+');
		st.wordChars('e', 'e');
		st.wordChars('E', 'E');
		st.wordChars('.', '.');
		st.whitespaceChars(' ', ' ');
		st.whitespaceChars('\t', '\t');
		int type = -1;
		while ((type = st.nextToken()) != StreamTokenizer.TT_EOF) {
			switch (type) {
			case StreamTokenizer.TT_WORD:
				data[j] = Double.parseDouble(st.sval);
				j++;
				break;
			case StreamTokenizer.TT_EOL:
				// at the end of the line, the data is appended at the ArrayList
				// use clone() to copy the object and not just its reference
				al.add(data.clone());
				numOfRows++;
				numOfCol = j;
				j = 0;
				break;
			default:
				break;
			}
		}
		br.close();
		// copying the data from the ArrayList into a double-array
		double[][] array = new double[numOfRows][numOfCol];
		for (i = 0; i < numOfRows; ++i) {
			data = (double[]) al.get(i);
			System.arraycopy(data, 0, array[i], 0, numOfCol);
		}
		al.clear();
		return (array);
	}

}