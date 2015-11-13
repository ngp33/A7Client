package gui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Random;

import ast.Program;
import ast.ProgramImpl;
import parse.Parser;
import parse.ParserFactory;
import world.Critter;
import world.Food;
import world.Hex;
import world.Rock;
import world.World;

public class WorldCritterLoader {
	
	public static World loadWorld(String filename) {
		BufferedReader reader;
		World world = null;
    	
        try {
			reader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			return null;
		}
        
        try {
	    	String line = reader.readLine();
	    	String name = null;
	    	int rows = 0;
	    	int cols = 0;
	    	
	    	// Get the name
	        while (line != null && name == null) {
	        	if (line.equals("") || line.substring(0, 2).equals("//")) {
	        		line = reader.readLine();
	        		continue;
	        	}
	        	if (line.substring(0, 5).equals("name ")) {
	        		name = line.substring(5);
	        	} else {
	        		System.out.println("Invalid world file: Name line malformed.");
        			return null;
	        	}
	        	line = reader.readLine();
	        }
	        
	        // Get the dimensions
	        while (line != null && (rows == 0 && cols == 0)) {
	        	if (line.equals("") || line.substring(0, 2).equals("//")) {
	        		line = reader.readLine();
	        		continue;
	        	}
	        	if (line.substring(0, 5).equals("size ")) {
	        		line = line.substring(5);
	        		int div = line.indexOf(' ');
	        		
	        		if (div == -1) {
	        			System.out.println("Invalid world file: Size line malformed.");
	        			return null;
	        		}
	        		
	        		cols = Integer.parseInt(line.substring(0, div));
	        		rows = Integer.parseInt(line.substring(div+1));
	        	} else {
	        		System.out.println("Invalid world file: Size line malformed.");
        			return null;
	        	}
	        	line = reader.readLine();
	        }
	        
	        // Check if necessary stuff is missing
	        if (name == null || rows <= 0 || cols <= 0) {
	        	System.out.println("Invalid world file.");
	        	return null;
	        }
	        
	        world = new World(rows, cols, name);
	        System.out.println("World created.");
	        
	        while (line != null) {
	        	if (line.equals("") || line.substring(0, 2).equals("//")) {
	        		line = reader.readLine();
	        		continue;
	        	}
	        	if (line.substring(0, 5).equals("rock ")) {
	        		line = line.substring(5);
	        		int div = line.indexOf(' ');
	        		
	        		if (div == -1) {
	        			System.out.println("Invalid world file: Rock line malformed.");
	        			return null;
	        		}
	        		
	        		int c = Integer.parseInt(line.substring(0, div));
	        		int r = Integer.parseInt(line.substring(div+1));
	        		
	        		world.setHex(r, c, new Rock());
	        		System.out.println("Rock added at (" + r + ", " + c + ")");
	        	} else if (line.substring(0, 5).equals("food ")) {
	        		line = line.substring(5);
	        		int div = line.indexOf(' ');
	        		
	        		if (div == -1) {
	        			System.out.println("Invalid world file: Food line malformed.");
	        			return null;
	        		}
	        		
	        		String line2 = line.substring(div+1);
	        		int div2 = line2.indexOf(' ');
	        		
	        		if (div2 == -1) {
	        			System.out.println("Invalid world file: Food line malformed.");
	        			return null;
	        		}
	        		
	        		int c = Integer.parseInt(line.substring(0, div));
	        		int r = Integer.parseInt(line2.substring(0, div2));
	        		int amount = Integer.parseInt(line2.substring(div2+1));
	        		
	        		world.setHex(r, c, new Food(amount));
	        		System.out.println("Food added at (" + r + ", " + c + ")");
	        	} else if (line.substring(0, 8).equals("critter ")) {
	        		line = line.substring(8);
	        		
	        		String[] segments = line.split("\\s+"); // If there's time later, use split() in above cases.
	        		
	        		if (segments.length != 4) {
	        			System.out.println("Invalid world file: Critter line malformed.");
	        			return null;
	        		}
	        		
	        		
	        		int c = Integer.parseInt(segments[1]);
	        		int r = Integer.parseInt(segments[2]);
	        		int dir = Integer.parseInt(segments[3]);
	        		
	        		Critter critter = createCritter(segments[0], world);
	        		critter.direction = dir;
	        		
	        		world.setHex(r, c, critter);
	        		world.addCritter(critter);
	        		System.out.println("Critter added at (" + r + ", " + c + ")");
	        	} else {
	        		System.out.println("Invalid world file.");
	        		return null;
	        	}
	        	line = reader.readLine();
	        }
	        
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (IndexOutOfBoundsException|NumberFormatException e) {
        	System.out.println("Invalid world file.");
        }
        
        return world;
	}
	
	public static Critter createCritter(String filename, World world) throws IOException, NumberFormatException {
    	Reader fr = new FileReader(filename);
    	BufferedReader critterReader = new BufferedReader(fr);
    	
    	String species;
    	int[] mem;
    	
    	String line;
    	String[] arguments;
    	
    	line = critterReader.readLine();
    	arguments = line.split(":\\s+");
    	if (arguments.length != 2) {
    		System.out.println("Invalid world file.");
    		return null;
    	}
    	
    	species = arguments[1];
    	
    	line = critterReader.readLine();
    	arguments = line.split(":\\s+");
    	if (arguments.length != 2) {
    		System.out.println("Invalid world file.");
    		return null;
    	}
    	
    	int memSize = Integer.parseInt(arguments[1]);
    	mem = new int[memSize];
    	mem[0] = memSize;
    	
    	line = critterReader.readLine();
    	arguments = line.split(":\\s+");
    	if (arguments.length != 2) {
    		System.out.println("Invalid world file.");
    		return null;
    	}
    	
    	mem[1] = Integer.parseInt(arguments[1]);
    	
    	line = critterReader.readLine();
    	arguments = line.split(":\\s+");
    	if (arguments.length != 2) {
    		System.out.println("Invalid world file.");
    		return null;
    	}
    	
    	mem[2] = Integer.parseInt(arguments[1]);
    	
    	line = critterReader.readLine();
    	arguments = line.split(":\\s+");
    	if (arguments.length != 2) {
    		System.out.println("Invalid world file.");
    		return null;
    	}
    	
    	mem[3] = Integer.parseInt(arguments[1]);
    	
    	line = critterReader.readLine();
    	arguments = line.split(":\\s+");
    	if (arguments.length != 2) {
    		System.out.println("Invalid world file.");
    		return null;
    	}
    	
    	mem[4] = Integer.parseInt(arguments[1]);
    	
    	line = critterReader.readLine();
    	arguments = line.split(":\\s+");
    	if (arguments.length != 2) {
    		System.out.println("Invalid world file.");
    		return null;
    	}
    	
    	mem[7] = Integer.parseInt(arguments[1]);
    	
    	//System.out.println(fr.read());
    	//System.out.println(critterReader.read());
    	Program rules = parseCritterRules(critterReader);
    	return new Critter(species, mem, new Random(), (ProgramImpl) rules, world);
    }
	
	private static Program parseCritterRules(Reader r) {
		Parser parser = ParserFactory.getParser();
		
		return parser.parse(r);
    }
	
	public static void loadCrittersOntoWorld(String filename, int n, World world) {
	        Critter model;
	        Random rnd = new Random();
	        
	        try {
	        	model = createCritter(filename, world);
			} catch (NumberFormatException | IOException e) {
				System.out.println("Invalid critter file.");
				return;
			}
	        
	        Hex[] emptyHexes = world.getEmptyHexes();
	        
	        if (emptyHexes.length < n) {
	        	System.out.println("Not enough room for " + n + " critters.");
	        	return;
	        }
	        
	        for (int i = emptyHexes.length - 1; i > 0; i--) {
	        	int index = rnd.nextInt(i + 1);
	        	Hex temp = emptyHexes[index];
	        	emptyHexes[index] = emptyHexes[i];
	        	emptyHexes[i] = temp;
	        }
	        
	        for (int i = 0; i < n; i++) {
	        	Critter toAdd = model.copy();
	        	world.swap(emptyHexes[i], toAdd);
	        	world.addCritter(toAdd);
	        }
	    }

}
