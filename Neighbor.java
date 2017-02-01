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
//                      b. the otherNode partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     (name of your pair programming partner)
// Email:            (email address of your programming partner)
// CS Login:         (partner's login name)
// Lecturer's Name:  (name of your partner's lecturer)
// Lab Section:      (your partner's lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM otherNode THAN THEIR PARTNER //////
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
 *  Used by GraphNode class to keep a list of 'this' node's neighbors and the
 *  cost to reach each of those neighbors. Stores the neignbor node and the 
 *  cost to travel from this node to this neighbor node. Instances of this 
 *  class are created to establish an edge (path) from 'this' GraphNode to 
 *  the specified neighbor node.  
 */
public class Neighbor extends Object implements Comparable<Neighbor>
{
	private int cost;
	private GraphNode neighbor;
	private String nameOfNeighbor;


	public Neighbor(int cost, GraphNode neighbor) 
	{
		this.cost = cost;
		this.neighbor = neighbor;
		this.nameOfNeighbor = neighbor.getNodeName();
	}

	/** 
	 * Returns the Neighbor (node) that is at the otherNode 
	 * end of "this" node's edge.
	 * @return the neighbor node itself.
	 */
	public GraphNode getNeighborNode() 
	{
		return this.neighbor;
	}

	/** 
	 *  Returns the cost of traveling this edge to get to
	 *  the Neighbor at the otherNode end of this edge. 
	 * 
	 * @return cost
	 */
	public int getCost() {
		return this.cost;
	}
	
	/**
	 *  Returns a String representation of this Neighbor. The String that is 
	 *  returned shows an arrow (with the cost in the middle) 
	 *  and then the Neighbor node's name. 
	 *  Example:
	 * 
	 *  --1--> b 
	 *  indicates a cost of 1 to get to node b
	 */
	@Override
	public String toString()
	{
		String thisString;
		thisString = "--"+this.getCost()+"--> " + 
		this.getNeighborNode().getNodeName();
		return thisString;
	}

	@Override
	public int compareTo(Neighbor otherNode) 
	{
		return this.nameOfNeighbor.compareTo(otherNode.nameOfNeighbor);
	}

}
