///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Program 5
// Files:            GraphNode.java
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

public class GraphNode implements Comparable<GraphNode>
{
    public static final int NOT_NEIGHBOR = Integer.MAX_VALUE;
    private String nameOfNode;
    // if you watch simpsons, hope you enjoyed this little quip :)
    private List<Neighbor> neighborinos;
    private boolean hasSpycam;

    /**
     * Represents a valid location in the game graph.
     * There can be a player, a spycam, or a spy at any graph node.
     * @param name The label that uniquely identifies this graph node.
     */
    public GraphNode(String name)
    {
    	if(name==null)
    	{
    		throw new IllegalArgumentException();
    	}
        this.nameOfNode = name;
        neighborinos = new ArrayList<Neighbor>();
        hasSpycam = false;
    }

    /**
     * @return name of node
     */
    public String getNodeName()
    {
        return nameOfNode;
    }

    /**
     * @return a list of neighbors
     */
    public List<Neighbor> getNeighbors() 
    {
        return neighborinos;
    }

    /**
     * Returns true if this node name is a neighbor of current node.
     * Can tell this by the cost.  If the cost to a potential neighbor is 
     * same as the GraphNode.NOT_NEIGHBOR constant, then the node is not a 
     * direct (one move) neighbor.
     * 
     * @param neighborName to look for
     * @return true if the node is an adjacent neighbor.
     */
    public boolean isNeighbor(String neighborName)
    {    
    	if(neighborName==null)
    	{
    		throw new IllegalArgumentException();
    	}
        try 
        {
            // If node neighbor = true,  will return costs
            // else exception thrown
            return this.getCostTo(neighborName) < GraphNode.NOT_NEIGHBOR;
        } 
        catch (NotNeighborException e ) 
        {
            return false;
        }
    }

    /**
     * Maintains sorted order of neighbors by neighbor name.
     * @param neighbor An adjacent node
     * @param cost The cost to move to that node 
     */
    public void addNeighbor(GraphNode neighbor, int cost)
    {
        if (! isNeighbor(neighbor.getNodeName())) 
        {
            neighborinos.add(new Neighbor(cost, neighbor));
            // sort Neighbors by title
            neighborinos.sort(null); 
        }
    }

    /**
     * Returns and iterator that can be used to find neighbors of  
     * this GraphNode.
     * @return An iterator of String node labels
     */
    public Iterator<String> getNeighborNames()
    {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < neighborinos.size();i++)
        {
        	result.add(neighborinos.get(i).getNeighborNode().getNodeName());
        }
        return result.iterator();
    }

    /**
     * @return true if the GraphNode has a spycam
     */
    public boolean getSpycam()
    {
    	return hasSpycam; 
    }

    /**
     * @param cam indicates whether the node now has a spycam
     */
    public void setSpycam(boolean cam)
    { 
    	hasSpycam = cam;
    }

    /**
     * @throws NotNeighborException if neighborName is not a neighbor
     * @param neighborName name of potential neighbor
     * @return cost to neighborName
     */
    public int getCostTo(String neighborName) throws NotNeighborException 
    {
    	if(neighborName==null)
    	{
    		throw new IllegalArgumentException();
    	}
        for ( Neighbor ned : neighborinos )
        {
            if ( neighborName.equals(ned.getNeighborNode().getNodeName())) 
            {
                return ned.getCost();
            }
        }
        throw new NotNeighborException();
    }

    /**
     * @throws NotNeighborException if name is not a neighbor of 
     * the GraphNode
     * @param name name of potential neighbor
     * @return the GraphNode associated with name that is a neighbor of the 
     * current node
     */
    public GraphNode getNeighbor(String name) throws NotNeighborException 
    {
    	if(name==null)
    	{
    		throw new IllegalArgumentException();
    	}
        for (Neighbor ned : neighborinos) 
        {
            if (ned.getNeighborNode().getNodeName().equals(name))
                return ned.getNeighborNode();
        }
        throw new NotNeighborException(); // or null?
    }

    /**
     * Print a list of neighbors and the cost of the edge to them
     */
    public void displayCostToEachNeighbor() 
    {
        for (Neighbor ned : neighborinos) 
        {
          System.out.println(ned.getCost() + " " 
        + ned.getNeighborNode().getNodeName());
        }
    }

    /** 
     * Return the results of comparing this node's name to the other node's name.
     * Allows collections of nodes to be sorted using the built-in sort methods.
     * @param otherNode Another node to compare names with this node.
     * @return the result of compareTo on the node names only.
     */
    @Override
    public int compareTo(GraphNode otherNode) 
    {
        return this.nameOfNode.compareTo(otherNode.nameOfNode);
    }
    
    /**
     * @return name of node
     */
    public String toString() 
    { 
        return nameOfNode;
    }

    /** 
     * Display's the node name followed by a list of neighbors to this node.
     * Example:  a's neighbors are: z m f p
     */
    public void printNeighborNames() 
    {
        for ( Neighbor ned : neighborinos ) 
        {
            System.out.println(ned.getCost() + " " 
        + ned.getNeighborNode().getNodeName());
        }
    }

}