/**
* Test 1: Programming Fundamentals.
*
* @author Areaf
* @date (13-02-2023)
*/

public class BluePrint
{
	private String name;
	private int age;
	

	/* Default constructor
	*/
	public BluePrint()
	{	
	}

	/* Constructor that takes 2 parameters/arguments
	*/
	public BluePrint(String name, int age)
	{
		this.name = name;
		this.age = age;
	}

	/* 2 setters
	*/
	public void setterForName(String name)
	{
		this.name = name;
	}

	public void setterForAge(int age)
	{
		this.age = age;
	}

	/* 2 getters
	*/

	public String getterForName()
	{
		return name;
	}

	public int getterForAge()
	{
		return age;
	}
}


/* A second test harness class
*/
class TestHarness
{
	public static void main (String[] args)
	{
		BluePrint obj1 = new BluePrint("Areaf", 19);	
		System.out.println("Hey " + obj1.getterForName() + ", you are " + obj1.getterForAge() + " years old");

		// New object
		BluePrint obj2 = new BluePrint();
		obj2.setterForName("Also Areaf");
		obj2.setterForAge(19);
		System.out.println("Hey again " + obj2.getterForName());
	}
}

