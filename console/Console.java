package console;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Random;
import java.util.Scanner;

import ast.Program;
import ast.ProgramImpl;
import parse.Parser;
import parse.ParserFactory;
import world.*;

/** The console user interface for Assignment 5. */
public class Console {
    private Scanner scan;
    public boolean done;

    World world;

    public static void main(String[] args) {
        Console console = new Console();

        while (!console.done) {
            System.out.print("Enter a command or \"help\" for a list of commands.\n> ");
            console.handleCommand();
        }
    }

    /**
     * Processes a single console command provided by the user.
     */
    void handleCommand() {
        String command = scan.next();
        switch (command) {
        case "new": {
            newWorld();
            break;
        }
        case "load": {
            String filename = scan.next();
            loadWorld(filename);
            break;
        }
        case "critters": {
            String filename = scan.next();
            int n = scan.nextInt();
            loadCritters(filename, n);
            break;
        }
        case "step": {
            int n = scan.nextInt();
            advanceTime(n);
            break;
        }
        case "info": {
            worldInfo();
            break;
        }
        case "hex": {
            int c = scan.nextInt();
            int r = scan.nextInt();
            hexInfo(c, r);
            break;
        }
        case "help": {
            printHelp();
            break;
        }
        case "exit": {
            done = true;
            break;
        }
        default:
            System.out.println(command + " is not a valid command.");
        }
    }

    /**
     * Constructs a new Console capable of reading the standard input.
     */
    public Console() {
        scan = new Scanner(System.in);
        done = false;
    }

    /**
     * Starts new random world simulation.
     */
    private void newWorld() {
    	world = new World();
    	
    	int rows = world.getNumRows();
    	int cols = world.getNumColumns();
    	
    	Random rand = new Random();
    	
    	for (int i = 0; i < cols; i++) {
    		for (int j = 0; j < rows; j++) {
    			world.setHex(j, i, rand.nextFloat() < .15 ? new Rock() : new Food(0)); //15% chance of Rock
    		}
    	}
    }

    /**
     * Starts new simulation with world specified in filename.
     * @param filename
     */
    private void loadWorld(String filename) {
    	BufferedReader reader;
    	
        try {
			reader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			return;
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
	        		name = line.substring(6);
	        	} else {
	        		System.out.println("Invaid world file.");
        			return;
	        	}
	        	line = reader.readLine();
	        }
	        
	        // Get the dimensions
	        while (line != null && (rows == 0 && cols == 0)) {
	        	if (line.equals("") || line.substring(0, 2).equals("//")) {
	        		line = reader.readLine();
	        		continue;
	        	}
	        	if (line.substring(0, 6).equals("world ")) {
	        		line = line.substring(7);
	        		int div = line.indexOf(' ');
	        		
	        		if (div == -1) {
	        			System.out.println("Invaid world file.");
	        			return;
	        		}
	        		
	        		rows = Integer.parseInt(line.substring(0, div));
	        		cols = Integer.parseInt(line.substring(div+1));
	        	} else {
	        		System.out.println("Invaid world file.");
        			return;
	        	}
	        	line = reader.readLine();
	        }
	        
	        // Check if necessary stuff is missing
	        if (world == null || rows <= 0 || cols <= 0) {
	        	System.out.println("Invalid world file.");
	        	return;
	        }
	        
	        world = new World(rows, cols, name);
	        
	        while (line != null) {
	        	if (line.equals("") || line.substring(0, 2).equals("//")) {
	        		line = reader.readLine();
	        		continue;
	        	}
	        	if (line.substring(0, 5).equals("rock ")) {
	        		line = line.substring(6);
	        		int div = line.indexOf(' ');
	        		
	        		if (div == -1) {
	        			System.out.println("Invaid world file.");
	        			return;
	        		}
	        		
	        		int r = Integer.parseInt(line.substring(0, div));
	        		int c = Integer.parseInt(line.substring(div+1));
	        		
	        		world.setHex(r, c, new Rock());
	        	} else if (line.substring(0, 5).equals("food ")) {
	        		line = line.substring(6);
	        		int div = line.indexOf(' ');
	        		
	        		if (div == -1) {
	        			System.out.println("Invaid world file.");
	        			return;
	        		}
	        		
	        		String line2 = line.substring(div+1);
	        		int div2 = line2.indexOf(' ');
	        		
	        		if (div2 == -1) {
	        			System.out.println("Invalid world file.");
	        			return;
	        		}
	        		
	        		int r = Integer.parseInt(line.substring(0, div));
	        		int c = Integer.parseInt(line2.substring(0, div2));
	        		int amount = Integer.parseInt(line2.substring(div2+1));
	        		
	        		world.setHex(r, c, new Food(amount));
	        	} else if (line.substring(0, 8).equals("critter ")) {
	        		line = line.substring(9);
	        		
	        		String[] segments = line.split("\\s+"); // If there's time later, use split() in above cases.
	        		
	        		if (segments.length != 4) {
	        			System.out.println("Invalid world file.");
	        			return;
	        		}
	        		
	        		
	        		int r = Integer.parseInt(segments[1]);
	        		int c = Integer.parseInt(segments[2]);
	        		int dir = Integer.parseInt(segments[3]);
	        		
	        		BufferedReader critterReader = new BufferedReader(new FileReader(segments[0]));
	        		
	        		// Left off
	        	}
	        }
	        
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (IndexOutOfBoundsException|NumberFormatException e) {
        	System.out.println("Invaid world file.");
        }
    }
    
    private Program parseCritterRules(Reader r) {
		Parser parser = ParserFactory.getParser();
		
		return parser.parse(r);
    }
    
    private Critter createCritter(String filename) throws IOException, NumberFormatException {
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
    	
    	Program rules = parseCritterRules(fr);
    	
    	return new Critter(species, mem, new Random(), (ProgramImpl) rules, world);
    }

    /**
     * Loads critter definition from filename and randomly places 
     * n critters with that definition into the world.
     * @param filename
     * @param n
     */
    private void loadCritters(String filename, int n) {
        //TODO implement
    }

    /**
     * Advances the world by n time steps.
     * @param n
     */
    private void advanceTime(int n) {
    	world.advanceTime(n);
    }

    /**
     * Prints current time step, number of critters, and world
     * map of the simulation.
     */
    private void worldInfo() {
    	System.out.println(world.getInfo());
    }

    /**
     * Prints description of the contents of hex (c,r).
     * @param c column of hex
     * @param r row of hex
     */
    private void hexInfo(int c, int r) {
    	System.out.println(world.getHex(r, c).getHexInfo());
    }

    /**
     * Prints a list of possible commands to the standard output.
     */
    private void printHelp() {
        System.out.println("new: start a new simulation with a random world");
        System.out.println("load <world_file>: start a new simulation with "
                + "the world loaded from world_file");
        System.out.println("critters <critter_file> <n>: add n critters "
                + "defined by critter_file randomly into the world");
        System.out.println("step <n>: advance the world by n timesteps");
        System.out.println("info: print current timestep, number of critters "
                + "living, and map of world");
        System.out.println("hex <c> <r>: print contents of hex "
                + "at column c, row r");
        System.out.println("exit: exit the program");
    }
}
