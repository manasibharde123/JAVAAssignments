import javax.swing.*;
import java.awt.*;

public class SwingGridLayout {
    public static void main( String args[] ) {
	JFrame window = new JFrame( "My First GUI Program" );

	window.getContentPane().setLayout( new GridLayout(2,2) );
	//window.getContentPane().setLayout( new GridLayout(0,8) );
	//window.getContentPane().setLayout( new GridLayout(4,4) );

	for ( int i = 0; i < 5; i++ )
	    window.getContentPane().add( 
		new JButton( String.valueOf( i ) ) );

	window.pack();
	window.show();
    }
} // SwingFrame