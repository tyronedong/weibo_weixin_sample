import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class deduplicate_weibo {

    public static void main(String args[]) {

        try {
            HashMap<String, Date> dedupDict = new HashMap<String, Date>();

            for (int i = 6; i <= 8; i++) {
                for (int j = 1; j <= 31; j++) {
                    if (i == 6 && j < 18)
                        continue;
                    if (i == 8 && j > 28)
                        continue;
                    InputStreamReader reader = new InputStreamReader(new FileInputStream(new File("D:\\GitHub\\weibo_weixin_sample\\data\\weibo\\" + i + "_" + j + "_weibo_india.txt")), "gbk");
                    BufferedReader br = new BufferedReader(reader);
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        String[] strs = line.split("\t");
                        if(strs.length < 32) {
                            System.out.println("wrong line: "+line);
                            continue;
                        }
                        String weibo_id = strs[0];
                        String dateStr = strs[1];
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date crawlTime = formatter.parse(dateStr);
                        if(dedupDict.containsKey(weibo_id)){
                            if(dedupDict.get(weibo_id).before(crawlTime)) {
                                dedupDict.put(weibo_id, crawlTime);
                                System.out.println(dedupDict.get(weibo_id).toString());
                                System.out.println(crawlTime.toString());
                            }
                        }else{
                            dedupDict.put(weibo_id, crawlTime);
                        }
                    }
                }
            }

            for(int i = 357; i< 873; i++){
                InputStreamReader reader = new InputStreamReader(new FileInputStream(new File("D:\\GitHub\\weibo_weixin_sample\\data\\weibo\\" + i + "_weibo_india.txt")), "utf-8");
                BufferedReader br = new BufferedReader(reader);
                String line = null;
                while ((line = br.readLine()) != null) {
                    String[] strs = line.split("\t");
                    if(strs.length < 32) {
                        System.out.println("wrong line: "+line);
                        continue;
                    }
                    String weibo_id = strs[0];
                    String dateStr = strs[1];
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date crawlTime = formatter.parse(dateStr);
                    if(dedupDict.containsKey(weibo_id)){
                        if(dedupDict.get(weibo_id).before(crawlTime)) {
                            dedupDict.put(weibo_id, crawlTime);
                            System.out.println(dedupDict.get(weibo_id).toString());
                            System.out.println(crawlTime.toString());
                        }
                    }else{
                        dedupDict.put(weibo_id, crawlTime);
                    }
                }
            }

            System.out.println("finish");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
