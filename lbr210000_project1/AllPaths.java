import java.io.*;
import java.util.*;

//class that contains the allpaths from src to dest, and contains a list of path objects that is output from that algorithm
public class AllPaths {
    
    Graph g = null;
    String src = "";
    String dest = "";
    
    // nested class Path, uses a linked list of graph.nodes to represent a path, and also has a function to calculate its weights
    public class Path {
        LinkedList<Graph.Node> path = new LinkedList<Graph.Node>();
        public int time = 0;
        public int cost = 0;
        public Path(LinkedList<Graph.Node> p) {
            path = (LinkedList<Graph.Node>) p.clone();
            int[] weights = calcWeights();
            time = weights[0];
            cost = weights[1];
            //System.out.println("new path created: " + printPath());
            
        }
        public int[] calcWeights() {
            int t = 0;
            int c = 0;
            for (Graph.Node a : path) {
                t += a.time;
                c += a.cost;
            }
            //System.out.println("calculated weights to be " + t + " " + c + " from " + path.getFirst().name + " to " + path.getLast().name);
        return new int[]{t,c};
        }
        public String printPath() {
            String s = path.getFirst().name;
            for(int i = 1; i < path.size(); i++) {
                s+=(" -> " + path.get(i).name);
            }
            s+=(". Time: " + time + " Cost: " + cost + "\n");
            //System.out.println("print path " + path.size());
            return s;
        }
    }
    
    LinkedList<Path> allPaths = new LinkedList<Path>();
    
    public AllPaths (Graph g, String src, String dest) {
        this.g = g;
        this.src = src;
        this.dest = dest;
    }
    
    public AllPaths (LinkedList<Path> ap, String src, String dest) {
        this.src = src;
        this.dest = dest;
        allPaths = (LinkedList<Path>)ap.clone();
        //System.out.println("\nalt constructor,\n"+printPaths());
    }
    
    public void findAllPaths() {
        //uses a linked list like a stack within these functions for backtracking purposes
        //and because its easier to put into my nest linked list "allPaths"
        //System.out.println("find all paths called");
        LinkedList<Graph.Node> currentPath = new LinkedList<Graph.Node>();
        currentPath.add(g.findNode(src));
        findAllPathsRecursive(g.findNode(src),g.findNode(dest),currentPath);
    }
    
    //recursive helper function
    public void findAllPathsRecursive(
        Graph.Node u, Graph.Node v,
        LinkedList<Graph.Node> currentPath
        ) {
        //System.out.println("find " + u.name + " " + v.name);
        if(u.name.equals(v.name)) {
            // destination found 
            // add current path to all paths
            //System.out.println("found path to " + v.name);
            //Path z = new Path(currentPath);
            //System.out.println(z.printPath());
            allPaths.add(new Path(currentPath));
            return; // return to higher function call in stack
        }
        
        // actualU is the node inside the outer linked list,
        Graph.Node actualU = g.findNode(u.name);
        
        // however, actualU's adajencies are added to the stack,
        // because the adajencies contains the nodes and weights to those nodes that are adjacent to u, like edges
        for(Graph.Node n : actualU.adjacencies) {
            //System.out.println("checking " + n.name);
            if(!searchPath(currentPath, n.name)) {
                //System.out.println("pushing " + n.name);
                currentPath.add(n); // push to list
                findAllPathsRecursive(n,v,currentPath);
                currentPath.removeLast(); // pop from list, back track to find other paths
            }
        }
            
    }
    
    // check if name exists in linked list
    public boolean searchPath(LinkedList<Graph.Node> list, String name) {
        for(Graph.Node n : list) {
            if(n.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public AllPaths topNPathsIn(LinkedList<Path> lists, int n, boolean byTime) { // get the top n paths
    
        //System.out.println("top n paths called");
    
        LinkedList<Path> topPaths = new LinkedList<Path>();
        
        for (int j = 0; j < n; j++) {
            //System.out.println("j is " + j + " n is " + n);
            if (lists != null && !lists.isEmpty()) {
                Path currentMin = lists.getFirst();
                for(int i = 1; i < lists.size()-1; i++) {
                    Path currentPath = lists.get(i);
                    //System.out.println("current path: " + currentPath.printPath());
                    if(byTime) {
                        if (currentPath.time < currentMin.time) {
                            currentMin = currentPath;
                        }
                    } else {
                        if (currentPath.cost < currentMin.cost) {
                            currentMin = currentPath;
                        }
                    }
                }
                topPaths.add(currentMin);
                lists.remove(currentMin);
            }
        }
        
        AllPaths newAP = new AllPaths(topPaths,src,dest);
        
        return newAP;
    }
    
    public String printPaths() {
        //System.out.println("print paths called" + allPaths.size());
        String s = "";
        if(allPaths.size() > 0) {
            for(int i = 0; i < allPaths.size(); i++) {
                if(allPaths.get(i) != null) {
                    s+=("Path " + (i+1) + ": " + allPaths.get(i).printPath());
                }
            }
        } else {
            s+=("Sorry, there are no flights that connect " + src + " to " + dest + ".\n");
        }
        s+="\n";
        return s;
    }
}