import com.google.gson.*;
import org.tukaani.xz.XZInputStream;
import java.io.*;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

public class weibo_sample {

    public static void main(String args[]) {
        File f = new File("D:\\GitHub\\weibo_weixin_sample\\data\\weibo_freshdata.2017-06-18.7z");

        try {
            for(int i=6; i<=8; i++) {
                for(int j=1; j<=31; j++) {
                    if(i==6 && j<18)
                        continue;
                    if(i==8 && j>28)
                        continue;

                    String filepath = String.format("D:\\GitHub\\weibo_weixin_sample\\data\\weibo_freshdata.2017-%02d-%02d.7z", i, j);

                    JsonParser parser = new JsonParser();
                    final SevenZFile sevenZFile = new SevenZFile(new File(filePath));
                    SevenZArchiveEntry entry = sevenZFile.getNextEntry();
                    while (entry != null) {
                        InputStream is = new InputStream() {
                            @Override
                            public int read() throws IOException {
                                return sevenZFile.read();
                            }

                            @Override
                            public int read(byte[] b, int off, int len) throws IOException {
                                return sevenZFile.read(b, off, len);
                            }
                        };
                        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            JsonObject obj = parser.parse(line).getAsJsonObject();
                            String weibo_content = obj.get("weibo_content").toString();
                            System.out.println(weibo_content);
                        }
                        entry = sevenZFile.getNextEntry();
                    }
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
