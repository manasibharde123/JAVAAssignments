import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitOutputStream {
	
	static BufferedOutputStream os;
	
	int buffer = 0, count = 0;
	public static void main(String args[]){
		try{
		os = new BufferedOutputStream(new FileOutputStream("compressed"));
		BitOutputStream bo = new BitOutputStream();
		bo.write("010101010");
		os.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeBit(boolean value){
		if(count == 32)
			writeInt();
		buffer = buffer<<1;
		if(value)			
			buffer = buffer | 1;
	}

	private void writeInt() {
		try {
			os.write(buffer);
			buffer = 0;
			count = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void write(String codes) {
		char codeValues[] = codes.toCharArray();
		for(int i=0; i<codes.length(); i++){
			if(codeValues[i]=='1')
				writeBit(true);
			else
				writeBit(false);
		}
		if(count<32)
			writeInt();
	}
}
