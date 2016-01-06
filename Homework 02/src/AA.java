/**
 * In this examples child class constructor does not use any parameterized constructor of parent class 
 * to update variables of parent class.
 * So by default only non parameterized constructor of parent class is called which sets value of aInt to 11.
 * Child class's constructor also initializes its (duplicate) variable aInt's value to 11 initially.
 * Overridden methods always access instance variable of child class if names are same unless specified the parent class's variable
 * by super keyword. 
 * @author1 Manasi Sunil Bharde
 * @author2 Praneeth Iyyapu
 *
 */
public class AA extends A{

	  int aInt = 1;		

	  AA() {
		aInt = 11-2;
	  }
	  public int intPlusPlus()	{
		return ++aInt;
	  }
	  public String toString()      {
        return this.getClass().getName() + ": " + aInt;
	  }

	  public static void main(String args[]) {
		AA aAA = new AA();
		//aA is assigned an object of child class AA
		AA   aA = (AA)aAA;
		//method intPlusPlus which is overridden for class AA is called which uses instance variable aInt of object AA
		//aInt = 12
		aAA.intPlusPlus();
		//method intPlusPlus which is overridden for class AA is called since aA now refers to object of type AA
		//aInt = 13
		aA.intPlusPlus();
		//toString method for class AA is called which uses instance variable aInt of object AA
		// and value of aInt = 13 is printed
		System.out.println(aA);
		//toString method for class AA is called and value of aInt = 13 is printed
		System.out.println(aAA);
		//aInt of class A is printed
		System.out.println("aA: " + (aA.aInt+2));
	  }
	}

class A {

  int aInt = 1;		

  A() {
	aInt = 11;
  }
  public int intPlusPlus()	{
	return ++aInt;
  }
  public String toString()	{
	  System.out.println("A to string");
	return this.getClass().getName() + ": " + aInt;
  }

  public static void main(String args[]) {
	AA aA = new AA();
	// aInt is incremented and value is 12 now
	aA.intPlusPlus();
	// A.toString method is called since aA is object of type A
	//Value of aInt = 12 is printed
	System.out.println(aA);
  }
}


class AAA extends AA {

	  int aInt = 1;		

	  AAA() {
		aInt = 11;
	  }
	  public int intPlusPlus()	{
		return ++aInt;
	  }

	  public static void main(String args[]) {
		AAA aAAA = new AAA();
		//since new object of AA is not created in below statement, 
		// variable aAA points to assigned value aAAA
		//aAAA.aInt = 11, AA.aInt = 11, A.aInt = 11
		AA   aAA = (AA)aAAA;
		//since new object of A is not created in below statement, 
		//variable aA points to assigned value aAA which in turn points to aAAA  
		AA     aA = (AA)aAA;
		// Therefore all three statements below execute the overridden method intPlusPlus of AAA class
		// and increment the instance variable aAAA.aInt
		aAAA.intPlusPlus();
		aAA.intPlusPlus();
		aA.intPlusPlus();
		//aAAA.aInt = 14 after 3 increments
		// method toString is not overridden by AAA class
		// Hence parent class AA's method toString is called.
		// which uses AA.aInt = 11 and 11 is printed.
		System.out.println("aA:        "  + aA);
		System.out.println("aAA:       " + aAA);
		System.out.println("aAAA:      " + aAAA);
		//since aAAA is object of type AAA, aAAA.aint is printed
		// which is 14
		System.out.println("aAAA:.aInt " + aAAA.aInt);
	  }
	}