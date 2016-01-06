/**
 2       * Handles writing of bits to File using BufferedOutputStream 
 3       * 
 5       * @version   1.1
 6       *
 7       * @author    msb4977 : Manasi Sunil Bharde
 8       * @author    Sri Praneeth Iyyapu
 9       * Revisions:
10       *      $Log$
11       */

import java.io.BufferedOutputStream;
import java.io.IOException;


public class BitOutputStream { 
	
	BufferedOutputStream os;	
	int buffer = 0, count = 0;
	
	public BitOutputStream(BufferedOutputStream os){
		this.os = os;
	}
	
	/**
	 * Writes bits to buffer 
	 * @param value input bit
	 */
	public void writeBit(boolean value){
		if(count == 8)
			writeInt();
		buffer = buffer<<1;
		if(value)			
			buffer = buffer | 1;
		count++;
	}

	/**
	 * Writes to file the buffer
	 */
	private void writeInt() {
		try {
			os.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		buffer = 0;
		count = 0;		
	}

	/**
	 * Writes the codes to file bitwise
	 * @param codes Code to write to file
	 */
	public void write(String codes) {
		for(int i=0; i<codes.length(); i++){
			if(codes.charAt(i)=='1')
				writeBit(true);
			else
				writeBit(false);
		}
		if(count<8)
			writeInt();
		try {
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
