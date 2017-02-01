///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Program 5
// Files:            Player.java
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
public class Player 
{
	private String name;
	private int budget;
	private int spycams;
	private GraphNode currLocation;
	private ArrayList<String> spycamLocationList;

	/**
	 * Constructor 
	 * 
	 * @param name - Player's name
	 * @param budget - Player budget to spend on moves
	 * @param spycams - the node the player starts on
	 * @param startnode - the node the player starts on
	 */
	public Player(String name, int budget, int spycams, GraphNode startnode) 
	{
		if(name==null)
		{
			throw new IllegalArgumentException();
		}
		// TODO Auto-generated constructor stub
		this.name = name;
		this.budget = budget;
		this.spycams = spycams;
		this.currLocation = startnode;
		spycamLocationList = new ArrayList<String>();
	}

	/**
	 * Method that decreases player's budget of moves.
	 * 
	 * @param dec - number to decrease budget by
	 */
	public void decreaseBudget(int dec)
	{
		int currBudget = budget;
		if(dec>budget)
		{
			System.out.println("Not enough money cost is " 
					+ dec + " budget is " + currBudget);
			return;
		}
		else
		budget = budget - dec;

	}

	/**
	 * If there are no remaining spycams to drop, display 
	 * "Not enough spycams" and return false. Otherwise: 
	 * If there is not a spy cam at the player's current location:
	 * drop a spycam (here) D decrement the remaining spycam 
	 * count if there was not already a spycam
	 * @return true if a spycam is dropped
	 */
	public boolean dropSpycam()
	{
		// if the list of spycam location s does not contain the 
		// current node desired to drop, then drop the camera on the
		// node, and remove a spycam from the user's inventory.
		if(!spycamLocationList.contains(currLocation.getNodeName()) 
				&& spycams > 0)
		{
			currLocation.setSpycam(true);
			// adds location name of where spycam is dropped to list
			spycamLocationList.add(currLocation.getNodeName());
			System.out.println("Droped a Spy Cam at "
					+ currLocation.getNodeName());
			spycams -= 1;
			return true;
		}
		// otherwise return false and print error message.
		else
			System.out.println("Not enough spycams");
		return false;
	}

	/**
	 * @return remaining budget
	 */
	public int getBudget()
	{
		return this.budget;
	}

	/**
	 * Returns the node where the player is currently located.
	 * 
	 * @return player's current node
	 */
	public GraphNode getLocation()
	{
		return this.currLocation;
	}

	/**
	 * Return currLocation's actual name
	 * 
	 * @return node label for the current location of the player
	 */
	public String getLocationName()
	{
		return this.currLocation.getNodeName();
	}

	/**
	 * @return name of player
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * If pickupSpyCam is true, increment the number of spy cams remaining.
	 * @param pickupSpycam
	 */
	public void getSpycamBack(boolean pickupSpycam)
	{	
		if(pickupSpycam)
		{
			spycams= spycams +1;
		}
		else
			return;
	}

	/**
	 * @return number of spycams remaining
	 */
	public int getSpycams()
	{
		return this.spycams;
	}

	/** 
	 * @param name - Neighboring node to move to
	 * @return - true if the player successfully
	 *  moves to this node if the cost is greater
	 *  than 1, decrement budget by that amount
	 */ 
	public boolean move(String name)
	{
		try{
			// conditional to check
			if( !currLocation.isNeighbor(name) )
			{
				throw new NotNeighborException();
			}
			// if the cost of moving to the node is greater than one,
			if( currLocation.getCostTo(name) > 1 ) 
			{
				// process if the move to the new location can occur.
				int currBudget = budget;
				// decrease the budget by the movement cost to that location
				decreaseBudget(currLocation.getCostTo(name));
				// if the budget is less than the original player budget
				// and the budget is greater than 0
				// move player to desired node. 
				if(budget < currBudget && budget > 0) 
				{
					currLocation = currLocation.getNeighbor(name);	
					return true;
				} 
				// otherwise return false.
				else 
				{
					return false;
				}
			}
			// if the cost of moving to the desired location is less than 
			// 1 move the player to that new location.
			else
			{
				currLocation = currLocation.getNeighbor(name);
				return true;
			}
			// catch the NotNeighborException and throw the prompt message 
			// to the console
		}catch(NotNeighborException e)
		{
			System.out.println
			(name+ " is not a neighbor of your current lcoation");
			return false;
		}
	}

	/**
	 * Check the node to see if there is a spycam. 
	 * If there is a spycam at that node, remove 
	 * the spycam from that node. Also, remove the
	 * spycam name from the Player's list of spycam
	 * names. Otherwise, return false.
	 * 
	 * @param node
	 * @return
	 */
	public boolean pickupSpycam(GraphNode node)
	{
		String nodeName = node.getNodeName();
		// if a node exists within the given graphnode, then
		// if the node in question is within the list of
		// locations of spy cameras, remove the location's name
		// from that list
		if(spycamLocationList.contains(nodeName))
		{
			// picks up the spycam at location and adds it to
			// player's repository
			getSpycamBack(true);
			// sets the boolean showing camera present in the graphnode's 
			// location to false
			node.setSpycam(false);
			spycamLocationList.remove(nodeName);
			// return's true
			return true;
		}
		return false;
	}

	/**
	 * Display the names of the locations where
	 * Spy Cams were dropped (and are still there).
	 */
	public void printSpyCamLocations()
	{
		for(int i =0; i<=spycamLocationList.size()-1; i++)
		{
			System.out.println("Spy Cam at " + spycamLocationList.get(i));
		}

	}
}
