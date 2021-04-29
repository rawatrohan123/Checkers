import java.util.ArrayList;
import java.util.Scanner;

public class Human extends Player implements Comparable<Human> {
	private String name;
	private String password;
	private int totalWins;
	private int totalLosses;

	
	

	/**
	 * Compares two human objects. Return 0 if both are equal. If they are not equal, first compares name, then password,
	 * then totalWins, and then totalLosses.
	 *
	 *@param Human h
	 */
	@Override
	public int compareTo(Human h) {
		if (this.equals(h)) {
			return 0;
		} else if (!(name.equals(h.name))) {
			return name.compareTo(h.name);
		} else if (!(password.equals(h.password))) {
			return password.compareTo(h.password);
		} else if (!(totalWins == h.totalWins)) {
			return Integer.compare(totalWins, h.totalWins);
		} else {
			return Integer.compare(totalLosses, h.totalLosses);
		}
	}

	
	/**
	 * Checks to see if one Human object is equal to the Object that is entered as a parameter.
	 *
	 *@param Object o
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (!(this instanceof Human)) {
			return false;
		} else {
			Human h = (Human) o;
			return name.equals(h.name) && password.equals(h.password) && totalWins == h.totalWins
					&& totalLosses == h.totalLosses;
		}
	}

	/**
	 * Searches for a user's email and password match of those currently stored in
	 * the account ArrayList
	 * 
	 * @param email    the email that was input
	 * @param password the password input
	 * @param account  the ArrayList storing users on file Returns an integer for
	 *                 ArrayList object position
	 */
	public int linearSearch(String name, String password, ArrayList<Human> account) {
		for (int i = 0; i < account.size(); i++) {
			if (account.get(i).getName().equals(name) && account.get(i).getPassword().equals(password)) {
				return i;
			} else if (account.get(i).getName().equals(name) && !account.get(i).getPassword().equals(password)) {
				return -5;
			}
		}
		return -1;
	}

	/**
	 * Searches an ArrayList of Human objects for the name and password of an
	 * account. If the account exists, it will welcome the user.
	 * 
	 * @param accounts the ArrayList of Human objects
	 * @param Human    h the human to update and add to the ArrayList of Humans
	 * @return input the Scanner to prompt the user for their name and password
	 *         Returns an updated ArrayList f Human objects
	 */

	public ArrayList<Human> accountSearchAndCreation(Scanner input, Human h, ArrayList<Human> accounts) {
		String name, password;
		boolean check = true;
		System.out.print("Enter your name: ");
		name = input.nextLine();
		System.out.print("Enter your password: ");
		password = input.nextLine();
		int pos = h.linearSearch(name, password, accounts);
		while (pos == -5) {
			System.out.println("Incorrect Password! " + "Please call 778-330-2389 to recover your account.\n");
			System.out.print("Enter your name: ");
			name = input.nextLine();
			System.out.print("Enter your password: ");
			password = input.nextLine();
			pos = h.linearSearch(name, password, accounts);
		}
		if ((pos != -1) && (pos != -5)) {
			System.out.println("\nWelcome Back" + accounts.get(pos).getName() + "!");
		} else {
			System.out.println("\nSorry! We don't have an account on file. \n" + "Let's create an account for you!\n");
			h = new Human();
			h.setName(name);
			h.setPassword(password);
			accounts.add(h);

			System.out.println("Thank you, " + h.getName() + "!\n" + "Your account has been created. \n");
		}
		//System.out.println(accounts.size());
		return accounts;
	}

	public Human() {
		this("Unknown Player", "No Password", 0, 0, false, false);

	}

	public Human(String name, String password, int totalWins, int totalLosses, boolean white, boolean movesUp) {
		super(white, movesUp);
		this.name = name;
		this.password = password;
		this.totalWins = totalWins;
		this.totalLosses = totalLosses;
	}

	public Human(boolean asterisk, boolean movesUp) {
		super(asterisk, movesUp);
		this.name = "Unknown Player";
		this.password = "No Password";
		this.totalWins = 0;
		this.totalLosses = 0;
	}

	/**
	 * To use for not only account verification, but also to see if they're within
	 * the list of accounts read in via file input.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Password to be used to find out if the user exists within a passed in set of
	 * scores/accounts via a file input.
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Set player's total wins with a passed variable via a file input
	 * 
	 * @param totalWins
	 */
	public void setTotalWins(int totalWins) {
		this.totalWins = totalWins;
	}

	/**
	 * Set player's total losses with a passed variable via a file input
	 * 
	 * @param totalLosses
	 */
	public void setTotalLosses(int totalLosses) {
		this.totalLosses = totalLosses;
	}

	/**
	 * Retrieve the users name for account verification, as well as to display/use
	 * in game.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retrieve the users password for account verification.
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Add a win upon a successful game.
	 */
	public void addWin() {
		totalWins++;
	}
	
	/**
	 * Return the total number of wins.
	 */
	public int getTotalWins() {
		return totalWins;
	}

	/**
	 * Add a loss upon losing a game.
	 */
	public void addLoss() {
		totalLosses++;
	}

	/**
	 * Return the total number of losses.
	 */
	public int getTotalLosses() {
		return totalLosses;
	}

	/**
	 * To primarily be shown for statistics after a game climaxes. DO NOT PRINT OUT
	 * THE PASSWORD!
	 */
	public String toString() {
		return "Name: " + name + "\n" + "Total Wins: " + totalWins + "\n" + "Total Losses: " + totalLosses + "\n";
	}

}



