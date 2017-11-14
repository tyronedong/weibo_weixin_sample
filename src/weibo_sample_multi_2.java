import com.google.gson.*;
import org.tukaani.xz.XZInputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

public class weibo_sample_multi_2 extends Thread {
	//处理非历史zip数据

	public Integer index;

	public weibo_sample_multi_2() {

	}

	public weibo_sample_multi_2(Integer _index) {
		index = _index;
	}

	public static boolean IsRelate(String content) {

		if (content.contains("中印") && content.contains("对峙"))
			return true;

		if (content.contains("中国") && content.contains("印度") && content.contains("对峙"))
			return true;

		if (content.contains("洞朗") && content.contains("对峙"))
			return true;

		if (content.contains("洞朗") && content.contains("印度"))
			return true;

		if (content.contains("边境对峙") && content.contains("印度"))
			return true;

		return false;
	}

	public void run() {
		try {
			// OutputStreamWriter writer = new OutputStreamWriter(new
			// FileOutputStream(new
			// File("D:\\github\\weibo_weixin_sample\\data\\weibo_india.txt")),
			// "gbk");
			OutputStreamWriter writer = new OutputStreamWriter(
					new FileOutputStream(new File("D:\\Program\\dong\\zip\\" + index + "_weibo_india.txt")), "utf-8");

			String filepath = String.format("D:\\Data\\WeiboWechat\\Weibo1\\weibo\\10\\%d.zip", index);
			File curF = new File(filepath);
			if (!curF.exists())
				return;
			
			System.out.println(index);

			ZipFile zf = new ZipFile(filepath);
			//Charset gbk = Charset.forName("utf-8");
			//ZipInputStream zin = new ZipInputStream(new FileInputStream(filepath), gbk);
			ZipInputStream zin = new ZipInputStream(new FileInputStream(filepath));
			ZipEntry entry;
			JsonParser parser = new JsonParser();
			while ((entry = zin.getNextEntry()) != null) {
				String line;
				int t = 0;
				BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(entry)));
				while ((line = br.readLine()) != null) {
					try {
						String newStr = new String(line.getBytes(), "UTF-8");
						JsonObject obj = parser.parse(line).getAsJsonObject();
						//String title = obj.get("title").toString();
						List<String> strs = new ArrayList<String>();
						strs.add(obj.get("weibo_id").toString());    //0
						strs.add(obj.get("crawler_time").toString());    //1
						strs.add(obj.get("crawler_time_stamp").toString());    //2
						strs.add(obj.get("is_retweet").toString());    //3
						strs.add(obj.get("user_id").toString());    //4
						strs.add(obj.get("nick_name").toString());    //5
						strs.add(obj.get("tou_xiang").toString());    //6
						strs.add(obj.get("user_type").toString());    //7
						strs.add(obj.get("_id").toString());    //8
						strs.add(obj.get("weibo_content").toString());    //9
						strs.add(obj.get("zhuan").toString());    //10
						strs.add(obj.get("ping").toString());    //11
						strs.add(obj.get("zhan").toString());    //12
						strs.add(obj.get("url").toString());    //13
						strs.add(obj.get("device").toString());    //14
						strs.add(obj.get("locate").toString());    //15
						strs.add(obj.get("time").toString());    //16
						strs.add(obj.get("time_stamp").toString());    //17
						strs.add(obj.get("r_user_id").toString());    //18
						strs.add(obj.get("r_nick_name").toString());    //19
						strs.add(obj.get("r_user_type").toString());    //20
						strs.add(obj.get("r_weibo_id").toString());    //21
						strs.add(obj.get("r_weibo_content").toString());    //22
						strs.add(obj.get("r_zhuan").toString());    //23
						strs.add(obj.get("r_ping").toString());    //24
						strs.add(obj.get("r_zhan").toString());    //25
						strs.add(obj.get("r_url").toString());    //26
						strs.add(obj.get("r_device").toString());    //27
						strs.add(obj.get("r_location").toString());    //28
						strs.add(obj.get("r_time").toString());    //29
						strs.add(obj.get("r_time_stamp").toString());    //30
						strs.add(obj.get("pic_content").toString());    //31
						for (int i = 0; i < 32; i++) {
							strs.set(i, strs.get(i).substring(1, strs.get(i).length() - 1));
						}
						//System.out.println(String.join("\t", strs));
						if (IsRelate(strs.get(9))) {
							writer.write(String.join("\t", strs));
							writer.write("\n");
						}

					}catch (Exception e){
						//System.out.println("bad weibo");
					}
					if (t % 10000 == 0) {
						System.out.println(index + " : " + t);
						writer.flush();
					}
					//System.out.println(t);
					t++;
				}
				System.out.println("finish");
				zin.closeEntry();
			}
			zin.close();

		} catch (Exception e) {
			System.out.println(index + "  occur exception.");
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {

		for (int i = 357; i <= 729; i++) {
			Thread t = new weibo_sample_multi_2(i);
			t.start();
		}

		/*
		int index = 357;
		try {
			// OutputStreamWriter writer = new OutputStreamWriter(new
			// FileOutputStream(new
			// File("D:\\github\\weibo_weixin_sample\\data\\weibo_india.txt")),
			// "gbk");
			OutputStreamWriter writer = new OutputStreamWriter(
					new FileOutputStream(new File("D:\\GitHub\\weibo_weixin_sample\\data\\" + index + "_weibo_india.txt")), "utf-8");

			String filepath = String.format("D:\\GitHub\\weibo_weixin_sample\\data\\%d.zip", index);
			File curF = new File(filepath);
			if (!curF.exists())
				return;

			System.out.println(index);

			ZipFile zf = new ZipFile(filepath);
			//Charset gbk = Charset.forName("utf-8");
			//ZipInputStream zin = new ZipInputStream(new FileInputStream(filepath), gbk);
			ZipInputStream zin = new ZipInputStream(new FileInputStream(filepath));
			ZipEntry entry;
			JsonParser parser = new JsonParser();
			while ((entry = zin.getNextEntry()) != null) {
				String line;
				int t = 0;
				BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(entry)));
				while ((line = br.readLine()) != null) {
					String newStr = new String(line.getBytes(), "UTF-8");
					JsonObject obj = parser.parse(line).getAsJsonObject();
					//String title = obj.get("title").toString();
					List<String> strs = new ArrayList<String>();
					strs.add(obj.get("weibo_id").toString());	//0
					strs.add(obj.get("crawler_time").toString());	//1
					strs.add(obj.get("crawler_time_stamp").toString());	//2
					strs.add(obj.get("is_retweet").toString());	//3
					strs.add(obj.get("user_id").toString());	//4
					strs.add(obj.get("nick_name").toString());	//5
					strs.add(obj.get("tou_xiang").toString());	//6
					strs.add(obj.get("user_type").toString());	//7
					strs.add(obj.get("_id").toString());	//8
					strs.add(obj.get("weibo_content").toString());	//9
					strs.add(obj.get("zhuan").toString());	//10
					strs.add(obj.get("ping").toString());	//11
					strs.add(obj.get("zhan").toString());	//12
					strs.add(obj.get("url").toString());	//13
					strs.add(obj.get("device").toString());	//14
					strs.add(obj.get("locate").toString());	//15
					strs.add(obj.get("time").toString());	//16
					strs.add(obj.get("time_stamp").toString());	//17
					strs.add(obj.get("r_user_id").toString());	//18
					strs.add(obj.get("r_nick_name").toString());	//19
					strs.add(obj.get("r_user_type").toString());	//20
					strs.add(obj.get("r_weibo_id").toString());	//21
					strs.add(obj.get("r_weibo_content").toString());	//22
					strs.add(obj.get("r_zhuan").toString());	//23
					strs.add(obj.get("r_ping").toString());	//24
					strs.add(obj.get("r_zhan").toString());	//25
					strs.add(obj.get("r_url").toString());	//26
					strs.add(obj.get("r_device").toString());	//27
					strs.add(obj.get("r_location").toString());	//28
					strs.add(obj.get("r_time").toString());	//29
					strs.add(obj.get("r_time_stamp").toString());	//30
					strs.add(obj.get("pic_content").toString());	//31
					for (int i=0; i<32; i++) {
						strs.set(i, strs.get(i).substring(1, strs.get(i).length()-1));
					}
					//System.out.println(String.join("\t", strs));
					if (IsRelate(strs.get(9))) {
						writer.write(String.join("\t", strs));
						writer.write("\n");
					}
					if (t % 1000 == 0) {
						System.out.println(index + " : " + t);
					}
					//System.out.println(t);
					t++;
				}
				System.out.println("finish");
				zin.closeEntry();
			}
			zin.close();

		} catch (Exception e) {
			System.out.println(index + "  occur exception.");
			e.printStackTrace();
		}
		*/
	}

	/*
	public static void ss(){

		String[] strs=  line.split("\t");
		if(strs.length < 32) {
			log.error("Wrong line : " + line);
			return;
		}
		//weibo id
		doc.setId(strs[0]);
		record.setDocId(strs[0]);
		try {
			Date fetchTime = formatter.parse(strs[1]);
			doc.setTime(fetchTime);
			record.setFetchTime(fetchTime);
		}
		catch(Exception e) {
			log.error(month + "," + day + ", fetch time : " + strs[1]  ,e);
		}

		String html = strs[9];
		record.setHtml(html);
		String content = Jsoup.parse(html).text();
		record.setPntData(getMapPntData(html, content));
		//id
		record.setId(strs[8]);
		doc.setDocId(strs[8]);
		record.setText(content);
		String url = "https://weibo.com/" + strs[4] + "/" + strs[13];
		record.setUrl(url);
		try {
			Date time = formatter.parse(strs[16]);
			record.setTime(time);

		}
		catch(Exception e) {
			log.error(e.getMessage());
		}
		record.setDate(strs[16].split(" ")[0]);
:
		dataS.put("DataS_crawler_time_stamp", strs[2]);
		dataS.put("DataS_is_retweet", strs[3]);
		dataS.put("DataS_user_id", strs[4]);
		dataS.put("DataS_nick_name", strs[5]);
		dataS.put("DataS_tou_xiang", strs[6]);
		dataS.put("DataS_user_type", strs[7]);
		String zhuan = strs[10];
		Integer zhuanInt = strToInt(zhuan);
		doc.setZhuan(zhuanInt);
		meta.put("Meta_zhuan", zhuanInt);
		String ping = strs[11];
		Integer pingInt = strToInt(ping);
		doc.setPing(pingInt);
		meta.put("Meta_ping", pingInt);
		String zhan = strs[12];
		Integer zhanInt = strToInt(zhan);
		doc.setZhan(zhanInt);
		meta.put("Meta_zhan", zhanInt);
		dataS.put("DataS_device", strs[14]);
		String locate = strs[15];
		if(locate!=null && !(locate.contains("alt")|| locate.contains("发行") || locate.contains("榜")
				|| locate.contains(":") || locate.contains("："))) {
			dataS.put("DataS_locate", strs[15]);
		}
		dataS.put("DataS_time_stamp", strs[17]);
		dataS.put("DataS_r_user_id", strs[18]);
		dataS.put("DataS_r_nick_name", strs[19]);
		dataS.put("DataS_r_user_type", strs[20]);
		dataS.put("DataS_r_weibo_id", strs[21]);
		dataS.put("DataS_r_weibo_content", strs[22]);
		String r_zhuan = strs[23];
		Integer r_zhuan_int = strToInt(r_zhuan);
		doc.setRZhuan(r_zhuan_int);
		meta.put("Meta_r_zhuan", r_zhuan_int);
		String r_ping = strs[24];
		Integer r_ping_int = strToInt(r_ping);
		doc.setRPing(r_ping_int);
		meta.put("Meta_r_ping", r_ping_int);
		String r_zhan = strs[25];
		Integer r_zhan_int = strToInt(r_zhan);
		doc.setRZhan(r_zhan_int);
		meta.put("Meta_r_zhan", r_zhan_int);
		dataS.put("DataS_r_url", strs[26]);
		dataS.put("DataS_r_device", strs[27]);
		dataS.put("DataS_r_location", strs[28]);
		dataS.put("DataS_r_time", strs[29]);
		dataS.put("DataS_r_time_stamp", strs[30]);
		dataS.put("DataS_pic_content", strs[31]);
		Long user_id = new Long(strs[4]);
	}
	*/
}
