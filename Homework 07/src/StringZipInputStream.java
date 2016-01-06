import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class StringZipInputStream {
	
	FileInputStream fs;
	int index = 0;
	byte[] ip;
	int fileLength;
	StringBuilder inBuffer, outBuffer = new StringBuilder();
	Vector<String> prefixes;
	
	// Creates a new input stream with a default buffer size.
		public StringZipInputStream(){
		}
		public StringZipInputStream(FileInputStream fileInputStream) {
			fs = fileInputStream;
			getPrefixes();
			readBytes();
			getCharacters();
		}
		// Reads data into a string. the method will block until some input can be read; otherwise, no bytes are read and null is returned.
		public String read() {
			return outBuffer.toString();
		}
		// Closes this input stream and releases any system resources associated with the stream.
		public void close() {
			try {
				fs.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void getPrefixes(){
				prefixes = new Vector<String>(); 
				BufferedInputStream br = new BufferedInputStream(fs);
				try {
					fileLength = (int) fs.getChannel().size();
					ip = new byte[fileLength];
					br.read(ip);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int count=0;
				StringBuilder lastPrefix =  new StringBuilder();
				while(count<256){
					if(((Byte)ip[index]).byteValue()==44){
						prefixes.addElement(lastPrefix.toString());
				//		System.out.println((char)(count)+" "+ lastPrefix);
						lastPrefix = new StringBuilder();
						count++;
						index++;
					}
					else
						lastPrefix.append(""+(char)ip[index++]);
				}
		}
		
		public void readBytes(){
			inBuffer = new StringBuilder();
			while(index<fileLength){
				inBuffer.append(getBinaryString((int)ip[index]));
				index++;
			}
		} 		
		
		public String getBinaryString(int number){
			String binary = "", bitPull = "00000000";
			
			int rem=0;
			if(number==0)
				return bitPull;
			if(number<0){
				number = 256 + number;
				}			
			while(number>0)
			{
				rem = number%2;
				binary = rem+binary;
				number = number/2;
			}			
			int startIndex = 8 - (8 - binary.length() ); 
			binary = bitPull.substring(startIndex)+binary;
			return binary;
		}
		
		public void getCharacters(){
			StringBuffer prefix = new StringBuffer();
			int i = 0;
			while(i<inBuffer.length()){
				prefix.append(inBuffer.charAt(i));
				i++;
				if(verifyPrefixExists(prefix.toString())){
					prefix = new StringBuffer();
				}
			}
		}
		
		private boolean verifyPrefixExists(String prefix) {

			for(int i=0; i<256;i++){
				if(prefixes.get(i).equals(prefix.toString())){
					outBuffer.append((char)i);
					return true;
				}			}
			return false;
		}

}
