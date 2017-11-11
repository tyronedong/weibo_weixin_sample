import com.google.gson.*;
import org.tukaani.xz.XZInputStream;
import java.io.*;


public class weibo_sample {

    public static void main(String args[]) {
        int index = 2;
        //File f = new File("D:\\Data\\WWHistory\\weibo\\weibo-content\\weibo_freshdata.2017-06-18.7z");
        File f = new File("D:\\Data\\WeiboWechat\\Weibo1\\weibo\\12\\3.txt.xz");
        //File f1 = new File("./cpa/" + index + ".cpa.txt");
        JsonParser parser = new JsonParser();
        try
        {
            //OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f1), "UTF-8");
            //BufferedWriter bw = new BufferedWriter(osw);
            //FileInputStream is = new FileInputStream(f);
            FileInputStream is = new FileInputStream(f);
            XZInputStream gzin = new XZInputStream(is);
            //LZMA2InputStream _7zin = new LZMA2InputStream(is, 100);
            //BufferedReader bufr = new BufferedReader(new InputStreamReader(_7zin, "GBK"));
            BufferedReader bufr = new BufferedReader(new InputStreamReader(gzin, "GBK"));
            String line = null;
            //Date start = new Date();
            //Long start = 1507941000l;
            //int j = 0;
            while ((line = bufr.readLine()) != null) {
                JsonObject obj = parser.parse(line).getAsJsonObject();
                String weibo_content = obj.get("weibo_content").toString();
                System.out.println(weibo_content);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
