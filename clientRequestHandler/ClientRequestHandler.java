package clientRequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

import clientRequestHandler.BundleFactory.CritPlacementBundle;
import gui.Controller;

public class ClientRequestHandler {
	
	Controller controller;
	BundleFactory bundleFactory = new BundleFactory();
	Gson gson = new Gson();
	final String serverURL = "http://private-anon-06e36470a-cs2112fall2015.apiary-mock.com/CritterWorld/";
	
	public ClientRequestHandler(Controller c) {
		controller = c;
	}
	
	
	/**Returns a single string containing all the stuff r can read.*/
	private String gatherResponse(BufferedReader r) throws IOException {
		StringBuilder result = new StringBuilder();
		String l = r.readLine();
		
		while (l != null) {
			result.append(l);
			result.append('\n');
			l = r.readLine();
		}
		
		return result.toString();
	}
	
	//GETs
	
	public BundleFactory.CritListBundle getCritter(int id) {
		//TODO
		return null;
	}
	
	
	//POSTs
	
	public int login(String level, String password) {
		URL url;
		HttpURLConnection connection;
		try {
			url = new URL(serverURL + "login");
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			connection.setDoOutput(true); // send a POST message
			connection.setRequestMethod("POST");
			PrintWriter w = new PrintWriter(connection.getOutputStream());
			
			AdminBundles.login loginBody = new AdminBundles.login(level, password);
			String loginBodyText = gson.toJson(loginBody, AdminBundles.login.class);
			
			w.println(loginBodyText);
			w.flush();
			
			int status = connection.getResponseCode();
			if (status == 401) {
				return -1;
			}
			
			BufferedReader r = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String responseBodyText = gatherResponse(r);
			AdminBundles.SessID responseBody = gson.fromJson(responseBodyText, AdminBundles.SessID.class);
			
			return responseBody.session_id;
		} catch (IOException e) {
			return -1;
		}
	}

}
