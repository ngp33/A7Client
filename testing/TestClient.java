package testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TestClient {
	
	public static void main(String[] args) {
		URL url;
		HttpURLConnection connection = null;
		
		try {
			url = new URL("http://localhost:8080/CritterWorld/eer");
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			BufferedReader r = new BufferedReader(new InputStreamReader(
					connection.getInputStream())); //This is where the GET magic happens.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}
	
}
