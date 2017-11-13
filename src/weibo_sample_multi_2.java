import com.google.gson.*;
import org.tukaani.xz.XZInputStream;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

public class weibo_sample_multi_2 extends Thread {
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
					new FileOutputStream(new File("D:\\Program\\dong\\later\\" + index + "_weibo_india.txt")), "gbk");

			String filepath = String.format("D:\\Data\\WeiboWechat\\Weibo1\\weibo\\10\\%d.zip", index);
			File curF = new File(filepath);
			if (!curF.exists())
				return;
			
			System.out.println(index);
			
			ZipFile zf = new ZipFile(filepath);
			InputStream in = new BufferedInputStream(new FileInputStream(filepath));
			ZipInputStream zin = new ZipInputStream(in);
			ZipEntry ze;
			while ((ze = zin.getNextEntry()) != null) {
				if (ze.isDirectory()) {
				} else {
					//System.err.println("file - " + ze.getName() + " : " + ze.getSize() + " bytes");
					long size = ze.getSize();
					if (size > 0) {
						BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));
						String line;
						int t = 0;
						while ((line = br.readLine()) != null) {
							String[] fields = line.split("\t");
							String weibo_content = fields[9];
							if (IsRelate(weibo_content)) {
								writer.write(line);
								writer.write("\n");
							}
							if (t % 1000 == 0) {
								System.out.println(index + " : " + t);
							}
							t++;
							//System.out.println(line);
						}
						br.close();
					}
					//System.out.println();
				}
			}
			zin.closeEntry();

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
	}
}
