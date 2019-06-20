package amata1219.amachat.bungee;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LineReader {

	public static List<String> readAll(BufferedReader reader){
		List<String> texts = new ArrayList<>();
		String line = "";
		try {
			while((line = reader.readLine()) != null)
				texts.add(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texts;
	}

}
