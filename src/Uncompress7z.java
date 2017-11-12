import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

public class Uncompress7z {

	
	public static void main(String[] args) {
		String filePath = "./test.7z";
		if(args.length>0) {
			filePath = args[0];
		}
		try {
			final SevenZFile sevenZFile = new SevenZFile(new File(filePath));
			SevenZArchiveEntry entry = sevenZFile.getNextEntry();
			while(entry!=null) {
				InputStream is = new InputStream() {
					@Override
					public int read() throws IOException{
						return sevenZFile.read();
					}
					@Override
					public int read(byte[] b, int off, int len) throws IOException{
						return sevenZFile.read(b,off,len);
					}
				};
				BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
				String line = null;
				while((line = br.readLine())!=null){
					System.out.println(line);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
