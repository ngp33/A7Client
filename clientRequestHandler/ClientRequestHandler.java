package clientRequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

import clientRequestHandler.BundleFactory.CritListBundle;
import clientRequestHandler.BundleFactory.CritPlacementBundle;
import clientRequestHandler.BundleFactory.Inhabitant;
import clientRequestHandler.BundleFactory.Placement;
import clientRequestHandler.BundleFactory.SpeciesAndIDs;
import gui.Controller;
import world.Critter;
import world.Food;
import world.Rock;

public class ClientRequestHandler {
	
	//Controller controller;
	BundleFactory bundleFactory = new BundleFactory();
	Gson gson = new Gson();
	final String serverURL = "http://inara.cs.cornell.edu:54345/CritterWorld/";
	
	public ClientRequestHandler(Controller c) {
		//controller = c;
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
	
	private Object basicGet(String URIEnding, Class<?> classOfT) {
		URL url;
		HttpURLConnection connection;
		try {
			url = new URL(serverURL + URIEnding);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			BufferedReader r = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			
			return gson.fromJson(r, classOfT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	//GETs
	
	public BundleFactory.CritListBundle getAllCritters(int sessionId) {
		String URIEnding = "critters?session_id=" + sessionId;
		return (CritListBundle) basicGet(URIEnding, CritListBundle.class);
	}
	
	public BundleFactory.Inhabitant getCritter(int id, int sessionId) {
		String URIEnding = "critter/" + id + "?session_id=" + sessionId;
		return (Inhabitant) basicGet(URIEnding, BundleFactory.Inhabitant.class);
	}
	
	public BundleFactory.WorldBundle getWorldState(int sessionId) {
		String URIEnding = "world?session_id=" + sessionId;
		return (BundleFactory.WorldBundle) basicGet(URIEnding, BundleFactory.WorldBundle.class);
	}
	
	public BundleFactory.WorldBundle getWorldState(int updateSince, int sessionId) {
		String URIEnding = "world?update_since=" + updateSince + "&session_id=" + sessionId;
		return (BundleFactory.WorldBundle) basicGet(URIEnding, BundleFactory.WorldBundle.class);
	}
	
	public BundleFactory.WorldBundle getWorldSubsectionState(int rowMin, int rowMax,
			int colMin, int colMax, int sessionId) {
		String URIEnding = "world?from_row=" + rowMin
				+ "&to_row=" + rowMax
				+ "&from_col=" + colMin
				+ "&to_col=" + colMax
				+ "&session_id=" + sessionId;
		return (BundleFactory.WorldBundle) basicGet(URIEnding, BundleFactory.WorldBundle.class);
	}
	
	public BundleFactory.WorldBundle getWorldSubsectionState(int updateSince, int rowMin,
			int rowMax, int colMin, int colMax, int sessionId) {
		String URIEnding = "world?update_since=" + updateSince
				+ "&from_row=" + rowMin
				+ "&to_row=" + rowMax
				+ "&from_col=" + colMin
				+ "&to_col=" + colMax
				+ "&session_id=" + sessionId;
		return (BundleFactory.WorldBundle) basicGet(URIEnding, BundleFactory.WorldBundle.class);
	}
	
	
	//POSTs
	
	/**Sends a login request to the server. Returns -1 if login failed. Else, returns session ID
	 * given by the server.
	 */
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
	
	/** Tells the server to add c at positions specified in pos*/
	public SpeciesAndIDs makeCritter(Critter c, Placement [] pos, int sessionId) {
		URL url;
		HttpURLConnection connection;
		try {
			url = new URL(serverURL + "critters?session_id=" + sessionId);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			connection.setDoOutput(true); // send a POST message
			connection.setRequestMethod("POST");
			PrintWriter w = new PrintWriter(connection.getOutputStream());
			
			BundleFactory.CritPlacementBundle body = new BundleFactory.CritPlacementBundle(c, pos);
			String bodyJSON = gson.toJson(body, BundleFactory.CritPlacementBundle.class);
			
			w.println(bodyJSON);
			w.flush();
			
			BufferedReader r = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String responseBodyText = gatherResponse(r);
			
			return gson.fromJson(responseBodyText, BundleFactory.SpeciesAndIDs.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/** Tells server to add num number of c at random positions.*/
	public SpeciesAndIDs makeCritter(Critter c, int num, int sessionId) {
		URL url;
		HttpURLConnection connection;
		try {
			url = new URL(serverURL + "critters?session_id=" + sessionId);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			connection.setDoOutput(true); // send a POST message
			connection.setRequestMethod("POST");
			PrintWriter w = new PrintWriter(connection.getOutputStream());
			
			BundleFactory.CritPlacementBundle body = new BundleFactory.CritPlacementBundle(c, num);
			String bodyJSON = gson.toJson(body, BundleFactory.CritPlacementBundle.class);
			
			w.println(bodyJSON);
			w.flush();
			
			BufferedReader r = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String responseBodyText = gatherResponse(r);
			
			return gson.fromJson(responseBodyText, BundleFactory.SpeciesAndIDs.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void makeNewWorld(BufferedReader worldFileReader, int sessionId) {
		URL url;
		HttpURLConnection connection;
		try {
			url = new URL(serverURL + "world?session_id=" + sessionId);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			connection.setDoOutput(true); // send a POST message
			connection.setRequestMethod("POST");
			PrintWriter w = new PrintWriter(connection.getOutputStream());
			
			w.println("{");
			w.println("\"description\":");
			String worldFileLine = worldFileReader.readLine();
			while (worldFileLine != null) {
				w.println(worldFileLine);
			}
			w.println("}");
			w.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void makeEntity(Food f, int sessionId) {
		URL url;
		HttpURLConnection connection;
		try {
			url = new URL(serverURL + "world/create_entity?session_id=" + sessionId);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			connection.setDoOutput(true); // send a POST message
			connection.setRequestMethod("POST");
			PrintWriter w = new PrintWriter(connection.getOutputStream());
			
			BundleFactory.Inhabitant body = new BundleFactory.Inhabitant(f);
			String bodyJSON = gson.toJson(body, BundleFactory.Inhabitant.class);
			
			w.println(bodyJSON);
			w.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void makeEntity(Rock r, int sessionId) {
		URL url;
		HttpURLConnection connection;
		try {
			url = new URL(serverURL + "world/create_entity?session_id=" + sessionId);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			connection.setDoOutput(true); // send a POST message
			connection.setRequestMethod("POST");
			PrintWriter w = new PrintWriter(connection.getOutputStream());
			
			BundleFactory.Inhabitant body = new BundleFactory.Inhabitant(r);
			String bodyJSON = gson.toJson(body, BundleFactory.Inhabitant.class);
			
			w.println(bodyJSON);
			w.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void worldStep(int sessionId) {
		URL url;
		HttpURLConnection connection;
		try {
			url = new URL(serverURL + "step?session_id=" + sessionId);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			connection.setDoOutput(true); // send a POST message
			connection.setRequestMethod("POST");
			PrintWriter w = new PrintWriter(connection.getOutputStream());
			
			w.println("{ }");
			w.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void worldStep(int count, int sessionId) {
		URL url;
		HttpURLConnection connection;
		try {
			url = new URL(serverURL + "step?session_id=" + sessionId);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			connection.setDoOutput(true); // send a POST message
			connection.setRequestMethod("POST");
			PrintWriter w = new PrintWriter(connection.getOutputStream());
			
			w.println("{ \"count\": " + count + " }");
			w.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void runContinuously(float rate, int sessionId) {
		URL url;
		HttpURLConnection connection;
		try {
			url = new URL(serverURL + "step?session_id=" + sessionId);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			connection.setDoOutput(true); // send a POST message
			connection.setRequestMethod("POST");
			PrintWriter w = new PrintWriter(connection.getOutputStream());
			
			w.println("{ \"rate\": " + rate + " }");
			w.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//DELETE
	
	public void removeCritter(int critterId, int sessionId) {
		URL url;
		HttpURLConnection connection;
		try {
			url = new URL(serverURL + "critter/" + critterId + "?session_id=" + sessionId);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true); // send a POST message
			connection.setRequestMethod("DELTE");
			connection.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
