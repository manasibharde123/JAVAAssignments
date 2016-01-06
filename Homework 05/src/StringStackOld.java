// this implementation implements the methods,
// but the methods are null methods;
public class StringStackOld implements StackInterfaceOld {
    
    public void push( Object item )	{	       }
    public Object pop() 		{ return null; }
    public Object peek() 		{ return "hi"; }
    public boolean isEmpty() 		{ return true; }

    public static void main(String args[])	{
	StackInterfaceOld aStackInterfaceOld = new StringStackOld();
	//There is no warning in the line below because String is an object of type 
	// Object. Method call does not complain if method implementation is null
	aStackInterfaceOld.push("hello");	// why is here no warning?
	//In below line pop method returns object where as aString variable is 
	//of type String. Hence compiler error is displayed 
	//String aString = aStackInterfaceOld.pop();
    }
/*
javac StringStackOld.java			// explain this error
StringStackOld.java:11: incompatible types	// explain what a cast would do
found   : java.lang.Object			// regarding possible compiler error detection
required: java.lang.String
	String aString = aStackInterfaceOld.pop();
	                                       ^
1 error

*/

}