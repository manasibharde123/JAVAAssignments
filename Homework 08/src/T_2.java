/** 
 * Program discusses how multi-threading can lead to different outputs
 * @author1    Manasi Sunil Bharde
 * @author2    Sri Praneeth Iyyapu
 * @version   1
 * Revisions: 1
 *      $Log$
 */

/*
 * Possible outcomes are
 * theValue = 1 theValue = 1 : Both run methods are called in random order after both print statements
 * theValue = 1 theValue = 2 : 1st Print statement -> thread 2's run method -> 2nd Print statement -> thread 2's run
 * theValue = 1 theValue = 3 : 1st Print statement -> thread 1's run -> 2nd Print statement -> thread 2's run
 * theValue = 2 theValue = 3 : thread 2's run method -> 1st print statement -> thread 1's run method -> 2nd print statement
 * theValue = 2 theValue = 2 : thread 2's run method -> 1st print statement -> 2nd print statement -> thread 1's run method
 * theValue = 3 theValue = 3 : thread 1's run method -> 1st print statement -> 2nd print statement -> thread 2's run method
 * 						or     thread 2's run method -> thread 1's run method -> 1st print statement -> 2nd print statement
 * theValue = 3 theValue = 2 : thread 1's run method -> 1st print statement -> thread 2's run method -> 2nd print statement
 * 
 */


public class T_2 extends Thread    {
    int id = 1;
    static String  theValue  = "1";
    T_2(int id)       {
        this.id = id;
    }
    public void run () {
        if ( id == 1 )
                theValue = "3";
        else
                theValue = "2";
    }      
        
    public static void main (String args []) {
        new T_2(1).start();;
        new T_2(2).start();;
            
        System.out.println("theValue = " + theValue );
        System.out.println("theValue = " + theValue );
    }       
}  