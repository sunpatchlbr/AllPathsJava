import java.io.*;
import java.util.*;

//class that handles input and output
public class FlightSystem
{
    BufferedReader reader;
    FileWriter outputFile;
    
    Graph flightGraph = new Graph();
    
    String flightDataFileName;
    String requestedFlightsFileName;
    String outputFileName;
    
    public FlightSystem(String flightDataFileName, String requestedFlightsFileName, String outputFileName)
    {
        this.flightDataFileName = flightDataFileName;
        this.requestedFlightsFileName = requestedFlightsFileName;
        this.outputFileName = outputFileName;
        
        readFlightData();
        
        try { // create output file and process input
            outputFile = new FileWriter(outputFileName);
            processInputPaths(); // process input and create flight paths
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public void readFlightData()
    {
        //System.out.println("Reading Flight data: ");
        try {
			reader = new BufferedReader(new FileReader(flightDataFileName));
			
			int linecount = Integer.parseInt(reader.readLine()); // get line count on first line of input file
			
			String line = reader.readLine(); // first input path with number of lines

			for (int i = 0; line != null && i < linecount; i++ ) {
				//System.out.println(line);
				
				String u = parseUntil(line,'|');
				line = line.substring(u.length()+1);
				
				String v = parseUntil(line,'|');
				line = line.substring(v.length()+1);
				
				String time = parseUntil(line,'|');
				int c = Integer.parseInt(time);
				line = line.substring(time.length()+1);
				
				String cost = parseUntil(line,'|');
				int t = Integer.parseInt(cost);
				
				flightGraph.addEdge(u,v,t,c);
				flightGraph.addEdge(v,u,t,c); //graph is undirected, or two way, in rubric, and addEdge() adds a one way/directed edge, so it must be called twice must go both ways
				
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println();
    }
    
    public void processInputPaths()
    {
        //System.out.println("Reading requested paths: ");
        try {
			reader = new BufferedReader(new FileReader(requestedFlightsFileName));
			
			int number = 1;
			String line = reader.readLine(); // first line with number of lines
			line = reader.readLine();

			while (line != null) {
				//System.out.println(line);
				// read next line
				
				String u = parseUntil(line,'|');
				line = line.substring(u.length()+1);
				
				String v = parseUntil(line,'|');
				line = line.substring(v.length()+1);
				
				String weight = parseUntil(line,'|');
				char c = weight.charAt(0);
				
				boolean time = false;
				
				String w = "";
				
				// get the desired weight to sort by
				if (c == 'T') {
				    time = true;
				    w = "Time";
				} else {
				    w = "Cost";
				}
				
				if(flightGraph.findNode(u) == null) {
				    outputFile.write(
				        "Flight " + number + ": " + u + ", " + v + " (" + w + ")" + "\n" 
				        + "Sorry, the requested source \"" + u + "\" does not exist in the graph\n" );
				    line = reader.readLine();
				    continue; 
				}
				
				if(flightGraph.findNode(v) == null) {
				    outputFile.write(
				        "Flight " + number + ": " + u + ", " + v + " (" + w + ")" + "\n" 
				        + "Sorry, the requested destination \"" + v + "\" does not exist in the graph\n" );
				    line = reader.readLine();
				    continue;
				}
				 
				//create an AllPaths object, which contains the all paths algorithm and a list of paths    
				AllPaths ap = new AllPaths(flightGraph,u,v);
				ap.findAllPaths();
				
				//System.out.println(ap.printPaths());
				
				// create new paths object using the top n paths in previous object, to use the print paths () in the next few lines
				AllPaths topPaths = ap.topNPathsIn(ap.allPaths,3,time);
				
				String wr = "Flight " + number + ": " 
			    	+ u + ", " + v + " (" + w + ")" + "\n" +
			    	topPaths.printPaths();
			    
			    outputFile.write(wr);

				line = reader.readLine();
				number++;
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println();
    }
    
    private String parseUntil(String s, char c) {
        String n = "";
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == c)
                break;
            n += s.charAt(i);
        }
        //System.out.println("Parsed \"" + n + "\"");
        return n;
    }
}