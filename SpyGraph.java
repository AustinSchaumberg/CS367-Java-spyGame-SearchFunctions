///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Program 5
// Files:            SpyGraph.java
// Semester:         Spring 2016
//
// Author:           Austin Schaumberg
// Email:            schaumberg2@wisc.edu
// CS Login:         schaumberg
// Lecturer's Name:  Deb Deppeler
// Lab Section:      367-002 (lecture)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
//                  CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     (name of your pair programming partner)
// Email:            (email address of your programming partner)
// CS Login:         (partner's login name)
// Lecturer's Name:  (name of your partner's lecturer)
// Lab Section:      (your partner's lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but tutors, roommates, relatives, strangers, etc do.
//
//			NOT APPLICABLE
//
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.*;

/**
 * Stores all vertexes as a list of GraphNodes.  
 * Provides necessary graph operations as
 * need by the SpyGame class.
 * 
 * @author strominger
 *
 */
public class SpyGraph implements Iterable<GraphNode> 
{
	/** DO NOT EDIT -- USE THIS LIST TO STORE ALL GRAPHNODES */
	// Contains a list of all vertexes
	private List<GraphNode> vlist;
	// HashMaps used coordinate a string (node name) to a list of neighbors
	private HashMap<String, List<Neighbor>> myNeighborNodeLists; 
	// Links a graphnode to a list of graphnodes
	private HashMap<GraphNode, List<GraphNode>> myNeighborNodes;
	// links the name of a graphnode to the actual graphnode
	private HashMap<String, GraphNode> myGraphNodes;
	// links a name of a neighbor to the neighbor node itself to pull
	// for particular problems.
	private HashMap<String, Neighbor> myNeighNodeLables;
	// used in BFS to find path
	private ArrayList<Neighbor> shortestPath;

	/**
	 * Initializes an empty list of GraphNode objects
	 */
	public SpyGraph()
	{
		// TODO initialize data member(s)
		this.vlist = new  LinkedList<GraphNode>();
		myNeighNodeLables = new HashMap<String, Neighbor>();
		myNeighborNodeLists = new HashMap<String, List<Neighbor>>();
		myGraphNodes = new HashMap<String, GraphNode>();
		myNeighborNodes = new HashMap<GraphNode,  List<GraphNode>>();
		shortestPath = new ArrayList<Neighbor>();
	}

	/**
	 * Add a vertex with this label to the list of vertexes.
	 * No duplicate vertex names are allowed.
	 * 
	 * @param name The name of the new GraphNode to create and add to the list.
	 */
	public void addGraphNode(String name)
	{
		// TODO implement this method
		// checks for bunk name data
		if(name==null)
		{
			throw new IllegalArgumentException();
		}
		// conditional integer that checks if node name exists, if 
		// the name exists as a node, the integer is set to 1, if
		// the getNodeFromName() method returns null it sets the 
		// integer to 0, meaning it never found a corresponding name,
		// meaning we can add the node with the given name.
		int binaryBoolean = getNodeFromName(name) == null ? 0 : 1;
		if(binaryBoolean==1)
		{
			return;
		}
		if(binaryBoolean==0)
		{
			GraphNode n = new GraphNode(name);
			vlist.add(n);
			myGraphNodes.put(name, getNodeFromName(name));
			return;
		}
	}

	/**
	 * Adds v2 as a neighbor of v1 and adds v1 as a neighbor of v2.
	 * Also sets the cost for each neighbor pair.
	 *   
	 * @param v1name The name of the first vertex of this edge
	 * @param v2name The name of second vertex of this edge
	 * @param cost The cost of traveling to this edge
	 * @throws IllegalArgumentException if the names are the same
	 */
	public void addEdge(String v1name, String v2name, int cost) 
			throws IllegalArgumentException
	{
		// TODO implement this method
		GraphNode v1Node, v2Node;
		// catch unwanted null values and duplicates
		if(v1name==null||v2name==null||
				getNodeFromName(v1name)==null||getNodeFromName(v2name)==null)
		{
			throw new IllegalArgumentException();
		}
		// check for same names
		else if(v1name.equals(v2name))
		{
			throw new IllegalArgumentException();
		}
		// add necessary vertex's neighbor list along with cost, etc.
		// for Non-Hash Table lists
		// retrieves relevan information about objects
		v1Node = getNodeFromName(v1name);
		v2Node = getNodeFromName(v2name);
		v1Node.addNeighbor(v2Node, cost);
		v2Node.addNeighbor(v1Node, cost);
		// create neighbor objects
		Neighbor neighbor1 = new Neighbor(cost, v1Node);
		Neighbor neighbor2 = new Neighbor(cost, v2Node);
		// Hash Table List work Below
		if ((v1Node = getGraphNode(v1name)) == null)
		{
			v1Node = addGraphNodeToHash(v1name);
		}
		if ((v2Node = getGraphNode(v2name)) == null)
		{
			v2Node = addGraphNodeToHash(v2name);
		}
		// Hash Table List work Below
		// the if statements are there to make sure a if the list of 
		// neighbors does not exist yet, the method goes out and 
		// creates a new list for the given key.
		// the hashmaps themselves store a string key associated to
		// a list of nighbor nodes
		if (getList(v1name) == null)
		{
			addGraphNodeToHash2(v1name);
		}
		if (getList(v2name) == null)
		{
			addGraphNodeToHash2(v2name);
		}
		myNeighborNodeLists.get(v1name).add(neighbor2);
		myNeighborNodeLists.get(v2name).add(neighbor1);
		// Hash Table List work Below adds v1graphnode to a list of
		// v2 graphnodes and vise versa.
		if (getList2(v1Node) == null)
		{
			addGraphNodeToHash3(v1Node);
		}
		if (getList2(v2Node) == null)
		{
			addGraphNodeToHash3(v2Node);
		}
		myNeighborNodes.get(v1Node).add(v2Node);
		myNeighborNodes.get(v2Node).add(v1Node);
		// adds an associated string name label to a neighbor node for
		// both strings provided.
		myNeighNodeLables.put(v1name, neighbor1);
		myNeighNodeLables.put(v2name, neighbor2);

	}

	/**
	 * Return an iterator through all nodes in the SpyGraph
	 * @return iterator through all nodes in alphabetical order.
	 */
	public Iterator<GraphNode> iterator() 
	{
		return vlist.iterator();
	}

	/**
	 * Return Breadth First Search list of nodes on path 
	 * from one Node to another.
	 * @param start First node in BFS traversal
	 * @param end Last node (match node) in BFS traversal
	 * @return The BFS traversal from start to end node.
	 */
	public List<Neighbor> BFS( String start, String end ) 
	{
		if(start==null||end==null)
		{
			throw new IllegalArgumentException();
		}
		List<Neighbor> almostBFS = new ArrayList<Neighbor>();
		// uses auxilary method to compute the bfs, then removes the first
		// element b/c is redundant info about the current node the user 
		// is on.
		almostBFS = bfsAux(start, end);
		almostBFS.remove(0);
		// return compiled BFS node list.
		List<Neighbor> bfsResult = almostBFS;
		return bfsResult;
	}

	/**
	 * Finds the shortest path between two nodes (start and end) in a graph.
	 * @param graph the graph to be searched for the shortest path.
	 * @param start the source node of the graph specified by user.
	 * @param end the destination node of the graph specified by user.
	 * @return the shortest path stored as a list of nodes.
	 *         or null if a path is not found.
	 * Requires: start != null, end != null and must have a name (e.g.
	 * cannot be an empty string).
	 */
	private  List<Neighbor> bfsAux( String start, String end) 
	{
		// clears the list, to be sure it contains relevant shortest path.
		shortestPath.clear();
		// retrieve neighbor node objects from key strings using the 
		// neighbornode lables hashmap
		Neighbor nStart = myNeighNodeLables.get(start);
		Neighbor nEnd = myNeighNodeLables.get(end);
		// A list that stores the path of current iteration.
		List<Neighbor> path = new ArrayList<Neighbor>();
		// A queue to store the visited nodes.
		Queue<Neighbor> queue = new LinkedList<Neighbor>();
		// A queue to store the visited nodes.
		Queue<Neighbor> visited = new LinkedList<Neighbor>();
		// If the start point is the same as end point, terminate recursive
		// method. 
		if (nStart.equals(nEnd)) 
		{
			path.add(nStart);
			return path;
		}
		// add first neighbor node to queue of neighbor nodes.
		queue.add(nStart);
		// while the queue is not empty fetch the adjacent nodes and
		// begin to visit each node of adjacent to that vertex.
		while (!queue.isEmpty()) 
		{
			// the current node position
			Neighbor v = queue.remove();
			// mark as visited in list
			visited.add(v);
			// Search reachableList of N from retrieving the list of neighbors
			// from the hashmap
			List<Neighbor> reachableList 
			= myNeighborNodeLists.get(v.getNeighborNode().getNodeName());
			// iteration counter
			int i = 0;
			// while iteration variable is less than the list of neighbors
			while (i != reachableList.size()) 
			{
				// Extracts the next neighbor of v
				Neighbor neighbor = reachableList.get(i);
				//add neighbor node and current node to the path list
				path.add(neighbor);
				path.add(v);
				// if the neighbor node equals our end point, return the 
				// path to the auxiliary method which assists in 
				// computing the shortest path.
				if (neighbor.equals(nEnd)) 
				{
					// Process path
					return processPath(nStart, nEnd, path);
				}
				else 
				{
					// check if the visited list contains the neighbor node
					// from the list of neighbors from v
					if (!visited.contains(neighbor)) 
					{
						// if it has not been added, add to the queue
						queue.add(neighbor);
					}
				}
				// iterate the counter variable
				i++;
			}

		}
		// or return null otherwise
		return null;
	} 

	/**
	 * Adds the nodes involved in the shortest path.
	 * @param start - the source node.
	 * @param end - the destination node.
	 * @param path - the path that has nodes and their neighbors.
	 * @return the shortest path.
	 */
	private  List<Neighbor> processPath(Neighbor start, 
			Neighbor end, List<Neighbor> path) 
			{
		// Finds out where the destination node directly comes from.
		int i = path.indexOf(end);
		Neighbor newEnd = path.get(i + 1);
		// Adds the destination node to the shortestPath.
		shortestPath.add(0, end);
		if (newEnd.equals(start)) 
		{
			// The original source node is found.
			shortestPath.add(0, start);
			return shortestPath;
		} 
		else 
		{
			// Find where the start node of the end node 
			// comes from.
			// Set the start node to be the end node.
			return processPath(start, newEnd, path);
		}
	}

	/**
	 * @param name Name corresponding to node to be returned
	 * @return GraphNode associated with name, null if no such node exists
	 */
	public GraphNode getNodeFromName(String name)
	{
		if(name==null)
		{
			throw new IllegalArgumentException();
		}
		for ( GraphNode n : vlist ) 
		{
			if (n.getNodeName().equalsIgnoreCase(name))
				return n;
		}
		return null;
	}

	/**
	 * Return Depth First Search list of nodes on path 
	 * from one Node to another.
	 * 
	 * @param start First node in DFS traversal
	 * @param end Last node (match node) in DFS traversal
	 * @return The DFS traversal from start to end node.
	 * @throws NotNeighborException 
	 */
	public List<Neighbor> DFS(String start, String end) 
	{
		// TODO implement this method
		// may need and create a companion method
		// catch bad input
		if(start == null || end == null)
		{
			throw new IllegalArgumentException();
		}
		// Lists which will retain the nodes visited during the Depth
		// First Search.
		List<GraphNode> visitedNodes = new ArrayList<GraphNode>();
		// List which will retain the path which  will recursively return
		// until the method's completion
		List<Neighbor> dfsResult = new ArrayList<Neighbor>();
		// create GraphNodes of the start/end of DFS search
		GraphNode startNode = getNodeFromName(start);
		GraphNode endNode = getNodeFromName(end);
		// visit the first/original/starting node of the path which 
		// will be taken.
		visitedNodes.add(startNode);
		// continue to recursively take the path from the given startNode
		// until the end node is reached, then return the dfsResult, which
		// will return the path taken by the DFS search.
		DFS(startNode, endNode, dfsResult, visitedNodes);
		return dfsResult;
	}

	/**
	 * Campanion method for DFS. Fills the dfsResult List with 
	 * Neighbors of the starting node given in the primary method in 
	 * Depth First Search ordering, which then returns the list dfsResult
	 * filled with the proper information.
	 * 
	 * @param currNode
	 * @param endNode
	 * @param dfsResult
	 * @param visitedNodes
	 */
	private void DFS(GraphNode currNode, GraphNode endNode,
			List<Neighbor> dfsResult, List<GraphNode> visitedNodes) 
	{
		// check to make sure the current node is equal to the end node
		// or if the ending node of the path has already been visited 
		// by the DFS search.
		if(currNode.equals(endNode) || visitedNodes.contains(endNode))
		{
			return;
		}
		// visit each unvisited neighbor of the current node
		for(Neighbor neighbor : currNode.getNeighbors())
		{
			// if the neighbor is not contained within the visited nodes list,
			// or the dfsResult listing, add it to each of them
			if( !visitedNodes.contains(neighbor.getNeighborNode()) && 
					!dfsResult.contains(neighbor) &&
					!visitedNodes.contains(endNode) )
			{
				// add the current Neighbor Node to the dfsResult List
				dfsResult.add(neighbor);
				visitedNodes.add(neighbor.getNeighborNode());
				// check each neighbor of the node to see if 
				// the end has been reached by DFS or to continue. 
				DFS(neighbor.getNeighborNode(), endNode,
						dfsResult, visitedNodes);
				// when the recursive method has reached its conclusion,
				// if the end node wasn't added remove the neighbor
				// (within conditional) from the dfsResult list.
				if(!visitedNodes.contains(endNode))
				{
					dfsResult.remove(neighbor);	
				}
			}
		}

	}

	/**
	 * OPTIONAL: Students are not required to implement Dijkstra's ALGORITHM
	 *
	 * Return Dijkstra's shortest path list of nodes on path 
	 * from one Node to another.
	 * @param start First node in path
	 * @param end Last node (match node) in path
	 * @return The shortest cost path from start to end node.
	 */
	public List<Neighbor> Dijkstra(String start, String end)
	{
		// TODO: implement Dijkstra's shortest path algorithm
		// may need and create a companion method
		// returns empty list.
		List<Neighbor> list = new ArrayList<Neighbor>();
		list.clear();
		return list;
	}

	/**
	 * DO NOT EDIT THIS METHOD 
	 * @return a random node from this graph
	 */
	public GraphNode getRandomNode() 
	{
		if (vlist.size() <= 0 ) 
		{
			System.out.println
			("Must have nodes in the graph before randomly choosing one.");
			return null;
		}
		int randomNodeIndex = Game.RNG.nextInt(vlist.size());
		return vlist.get(randomNodeIndex);
	}

//////////////////////////Start of Methods that are/////////////////////////////
///////////////// Extraneous Helper Methods, barely used///////////////////////
//////////////////////////but semi-relevant //////////////////////////////////
	/**
	 * Returns the GraphNode Matching the name
	 * @param name a String name of a GraphNode that may be in this Graph
	 * @return the GraphNode with a name that matches gnode or null
	 * if no such GraphNode exists in this Graph
	 * 
	 */
	private GraphNode getGraphNode(String name)
	{
		return myGraphNodes.get(name);
	}

	/**
	 * Add a new vertex name with no neighbors (if vertex does not yet exist)
	 * 
	 * @param name
	 *            vertex to be added
	 */
	private GraphNode addGraphNodeToHash(String name) {
		GraphNode n;
		n = myGraphNodes.get(name);
		if (n == null) 
		{
			n = new GraphNode(name);
			myNeighborNodeLists.put(name, new ArrayList<Neighbor>());
		}
		return n;
	}

	/**
	 * Returns the GraphNode Matching the name
	 * @param name a String name of a GraphNode that may be in this Graph
	 * @return the GraphNode with a name that matches gnode or null
	 * if no such GraphNode exists in this Graph
	 * 
	 */
	private List<Neighbor> getList(String name)
	{
		return myNeighborNodeLists.get(name);
	}

	private List<Neighbor> addGraphNodeToHash2(String name) {
		List<Neighbor> n;
		n = myNeighborNodeLists.get(name);
		if (n == null) 
		{
			n = new ArrayList<Neighbor>();
			myNeighborNodeLists.put(name, n);
		}
		return n;
	}

	private List<GraphNode> getList2(GraphNode name)
	{
		return myNeighborNodes.get(name);
	}

	private List<GraphNode> addGraphNodeToHash3(GraphNode name) {
		List<GraphNode> n;
		n = myNeighborNodes.get(name);
		if (n == null) 
		{
			n = new ArrayList<GraphNode>();
			myNeighborNodes.put(name, n);
		}
		return n;
	}
////////////////////////// End of Methods that are/////////////////////////////
///////////////////Extraneous Helper Methods, barely used//////////////////////
////////////////////////// but semi-relevant //////////////////////////////////
}