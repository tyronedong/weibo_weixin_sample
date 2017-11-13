import com.google.gson.*;
import org.tukaani.xz.XZInputStream;
import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

public class weixin_sample_zip_multi extends Thread {
	public Integer dir;
	public Integer index;

	public weixin_sample_zip_multi() {

	}

	public weixin_sample_zip_multi(Integer _dir, Integer _index) {
		dir = _dir;
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
			//String filepath = String.format("D:\\Data\\WeiboWechat\\Weibo1\\weibo\\10\\%d.zip", index);
			//String filepath = String.format("D:\\github\\weibo_weixin_sample\\data\\%d.zip", index);
			String filepath = "D:\\Data\\WeiboWechat\\Weixin1\\page\\"+dir+"\\"+index+".zip";
			File curF = new File(filepath);
			if (!curF.exists())
				return;
			
			System.out.println(dir+"_"+index);
			
			OutputStreamWriter writer = new OutputStreamWriter(new
					 FileOutputStream(new
					 File("D:\\Program\\dong\\weixin\\"+dir+"_"+index+".txt")),
					 "utf-8");
//					OutputStreamWriter writer = new OutputStreamWriter(
//							new FileOutputStream(new File("D:\\Program\\dong\\weixin\\" + index + "_weixin_india.txt")), "gbk");
			
			ZipFile zf = new ZipFile(filepath);
			//Charset gbk = Charset.forName("utf-8");
			//ZipInputStream zin = new ZipInputStream(new FileInputStream(filepath), gbk);
			ZipInputStream zin = new ZipInputStream(new FileInputStream(filepath));
            ZipEntry entry;
            JsonParser parser = new JsonParser();
            while ((entry = zin.getNextEntry()) != null) {
//            	System.out.println(entry.getName());
//                System.out.println(entry.getComment());
//                System.out.println(entry.getExtra());
				
				String line;
				int t = 0;
				BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(entry)));
				while ((line = br.readLine()) != null) {
					String newStr = new String(line.getBytes(), "UTF-8");
					//System.out.println(newStr);
					//JsonObject obj = parser.parse(newStr).getAsJsonObject();
					//String title = obj.get("title").toString();
					//String content = obj.get("content").toString();
					if (IsRelate(newStr)) {
						writer.write(newStr);
						writer.write("\n");
					}
					if (t % 1000 == 0) {
						System.out.println(dir+"_"+index + " : " + t);
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

		for (int i = 54; i <= 55; i++) {
			for (int j = 0; j <= 999; j++){
				if(i==54&&j<490)
					continue;
				if(i==55&&j>714)
					continue;
				Thread t = new weixin_sample_zip_multi(i, j);
				t.start();
			}
		}
//		int index= 504;
//		try {
//			//String filepath = String.format("D:\\Data\\WeiboWechat\\Weibo1\\weibo\\10\\%d.zip", index);
//			String filepath = String.format("D:\\github\\weibo_weixin_sample\\data\\%d.zip", index);
//			File curF = new File(filepath);
//			if (!curF.exists())
//				return;
//			
//			System.out.println(index);
//			
//			OutputStreamWriter writer = new OutputStreamWriter(new
//					 FileOutputStream(new
//					 File("D:\\github\\weibo_weixin_sample\\data\\weixin_india.txt")),
//					 "gbk");
////					OutputStreamWriter writer = new OutputStreamWriter(
////							new FileOutputStream(new File("D:\\Program\\dong\\weixin\\" + index + "_weixin_india.txt")), "gbk");
//			
//			ZipFile zf = new ZipFile(filepath);
//			//Charset gbk = Charset.forName("utf-8");
//			//ZipInputStream zin = new ZipInputStream(new FileInputStream(filepath), gbk);
//			ZipInputStream zin = new ZipInputStream(new FileInputStream(filepath));
//            ZipEntry entry;
//            JsonParser parser = new JsonParser();
//            while ((entry = zin.getNextEntry()) != null) {
////            	System.out.println(entry.getName());
////                System.out.println(entry.getComment());
////                System.out.println(entry.getExtra());
//				
//				String line;
//				int t = 0;
//				BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(entry)));
//				while ((line = br.readLine()) != null) {
//					String newStr = new String(line.getBytes(), "UTF-8");
//					//System.out.println(newStr);
//					JsonObject obj = parser.parse(newStr).getAsJsonObject();
//					//String title = obj.get("title").toString();
//					String content = obj.get("content").toString();
//					if (IsRelate(content)) {
//						writer.write(newStr);
//						writer.write("\n");
//					}
//					if (t % 1000 == 0) {
//						System.out.println(index + " : " + t);
//					}
//					System.out.println(t);
//					t++;
//				}
//                System.out.println("finish");
//                zin.closeEntry();
//            }
//            zin.close();
//
//		} catch (Exception e) {
//			System.out.println(index + "  occur exception.");
//			e.printStackTrace();
//		}
	}
}
