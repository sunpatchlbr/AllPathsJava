import java.io.*;
import java.util.*;

//Graph with two dimensional weights using nested linked lists
public class Graph
{
    //Node uses linked list of nodes as adjacencies
    //this class is used to represent individual nodes in the graph class
    class Node {
        public String name;
        public Node parent = null;
        
        // this list represents the adjacenies
        public LinkedList<Node> adjacencies = new LinkedList<Node>();
        
        
        // these are initialized to zero, for the node list in the graph list/
        // but in the adjacency list, this class is extended to represent adjacencies by giving time and cost values
        // representing the weighted edges
        public int time = 0;
        public int cost = 0;
        
        public Node(String name) {
            this.name = name;
            //adjacencies = new LinkedList<Node>();
        }
        
        //constructor for nodes used as adjacencies to a node in the graph
        // which are used in path creation in Path() with their weights and costs
        public Node(String name, int t, int c) {
            this.name = name;
            time = t;
            cost = c;
        }
        
        //Time and cost to get to adjacency from parent Node
        public void addAdjacency(String v, int time, int cost) {
            if(findAdjacency(v) == null) {
                adjacencies.add(new Node(v,time,cost));
            }
        }
        
        public Node findAdjacency(String name) {
            Node a;
            for(int i = 0; i < adjacencies.size() ;i++) {
                a = adjacencies.get(i);
                if ( a.name == name ) {
                    return a;
                }
            }
            return null;
        }
    }
    
    public LinkedList<Node> nodes = new LinkedList<Node>();
    
    public Graph()
    {
        //empty constructor
    }
    
    public int getSize() {
        return nodes.size();
    }
    
    public Node findNode(String name) {
        Node n;
        //System.out.println("Searching for " + name);
        for(int i = 0; i < nodes.size(); i++) {
            n = nodes.get(i);
            //System.out.println("Checking: " + n.name);
            if ( n.name.equals(name) ) {
                //System.out.println("found " + n.name);
                return n;
            }
        }
        //System.out.println("didn't find " + name);
        return null;
    }
    
    public Node addNode(String v) {
        Node n = findNode(v);
        if (n == null) {
            n = new Node(v);
            //System.out.println("Created " + n.name);
            nodes.add(n);
        }
        return n;
    }
    
    public void addEdge(String u,String v,int time,int cost) {
        //System.out.println("Adding edge for " + u + v);
        Node n = addNode(u);
        n.addAdjacency(v,time,cost);
        //System.out.println("done");
    }
    
    // this function was used mostly in testing
    // outputs the graph
    public String toString() {
        String s = "";
        for(int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            s += n.name + ": ";
            for(int j = 0; j < n.adjacencies.size(); j++) {
                Node a = n.adjacencies.get(j);
                s += a.name + ", ";
            }
            s += "\n";
        }
        return s;
    }
}