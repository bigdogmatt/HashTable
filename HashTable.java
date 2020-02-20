//Matthew Kleman
//10/19/18
//COMP 2100

public class HashTable {

	private static class Node 
	{
		//Nodes have a key and a value
		public String key;
		public String value;
	}
	
	//A hashtable has an array of nodes called table and a size
	private Node[] table = new Node[101];	
	private int size = 0;
	
	/*
	 * a new method called hash that finds and returns that hash value of a string for 
	 * the current table by first calling hash code on the key. The hashcode value is
	 * then anded with an int that is all 1s except the most significant bit. This makes
	 * sure the number is positive before moding it with the table length to get the final
	 * hash value.
	 */
	private int hash(String key) 
	{
		int code = key.hashCode();
		code = code & 0x7FFFFFFF;
		return code % table.length;
		
	}	
	/*
	 * a new method called step that calculates the step size using a double-hashing approach that is 
	 * used when searching for an unused bucket. We first get the hashcode of the given string and make
	 * sure it's not negative by using the same trick as the hash method. We then mod this number by
	 * 97 and add one to get the step size and return that value. 
	 */
	private int step(String key) 
	{
		int code = key.hashCode();
		code = code & 0x7FFFFFFF;
		return (code % 97) + 1;
	}
	
	/*
	 * a new method called nextPrime that uses the method isPrime to get the prime number that 
	 * comes after the number given. This is used when we want to resize the array because the 
	 * array size must be prime when using a double hashing approach. This value is then returned.
	 */
	private int nextPrime(int length) 
	{
		while(!isPrime(length))
		{
			length += 2;
		}
		return length;
	}	
	
	/*
	 * a new method called isPrime that us used to help find the next prime number in nextPrime.
	 * This method returns true is the given number is prime and false if it is not prime. It does
	 * this by means of a for loop that runs from 2 (because we are modding by i and modding by
	 * 0 and 1 doesnt do much for us) until i is <= to the square root of the number. We use the 
	 * square root because 
	 */
	private boolean isPrime(int number)
	{
		int sqrt = (int)Math.sqrt(number);
		for(int i = 2; i <= sqrt; i++)
		{
			if(number % i == 0)
				return false;
		}
		return true;
	}
	
	public boolean containsKey(String key) 
	{
		int location = hash(key);
		int step = step(key);
		
		while(table[location] != null)
		{
			if(table[location].key.equals(key))
				return true;
			else
			{
				location = ((location + step) % table.length);
			}
		}
		return false;

	}	
	
	public boolean put( String key, String value ) 
	{
		if(size >= 0.75 * table.length)
		{
			Node[] temp = table;
			table = new Node[nextPrime((table.length * 2) + 1)];
			size = 0;
			
			for(int i = 0; i < temp.length; i++)
			{
				if(temp[i] != null)
					put(temp[i].key, temp[i].value);
			}
		}
		
		int location = hash(key);
		int step = step(key);
		
		while(table[location] != null)
		{
			if(table[location].key == key)
			{
				table[location].value = value;
				return false;
			}
			else
			{
				location = ((location + step) % table.length);
			}
		}
		table[location] = new Node();
		table[location].key = key;
		table[location].value = value;
		return true;
	}
	
	public String get( String key ) 
	{
		int location = hash(key);
		int step = step(key);
		
		while(table[location] != null)
		{
			if(table[location].key.equals(key))
				return table[location].value;
			else
			{
				location = ((location + step) % table.length);
			}
		}
		return null;
	}
}
