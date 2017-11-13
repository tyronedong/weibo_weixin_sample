import com.google.gson.*;
import org.tukaani.xz.XZInputStream;
import java.io.*;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

public class weibo_sample_multi extends Thread {
	public Integer dir;
	public Integer index;

	public weibo_sample_multi() {

	}

	public weibo_sample_multi(Integer _dir, Integer _index) {
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
			// OutputStreamWriter writer = new OutputStreamWriter(new
			// FileOutputStream(new
			// File("D:\\github\\weibo_weixin_sample\\data\\weibo_india.txt")),
			// "gbk");
			OutputStreamWriter writer = new OutputStreamWriter(
					new FileOutputStream(new File("D:\\Program\\dong\\" + dir + "_" + index + "_weibo_india.txt")),
					"gbk");

			// String filepath =
			// String.format("D:\\github\\weibo_weixin_sample\\data\\weibo_freshdata.2017-%02d-%02d.7z",
			// i, j);
			String filepath = String
					.format("D:\\Data\\WWHistory\\weibo\\weibo-content\\weibo_freshdata.2017-%02d-%02d.7z", dir, index);
			File curF = new File(filepath);
			if (!curF.exists())
				return;

			System.out.println(dir + ":" + index);

			JsonParser parser = new JsonParser();
			final SevenZFile sevenZFile = new SevenZFile(curF);
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
				int t = 0;
				while ((line = br.readLine()) != null) {
					// JsonObject obj = parser.parse(line).getAsJsonObject();
					// String weibo_content =
					// obj.get("weibo_content").toString();
					String[] fields = line.split("\t");
					String weibo_content = fields[9];
					// System.out.println(weibo_content);

					if (IsRelate(weibo_content)) {
						writer.write(line);
						writer.write("\n");
						// System.out.println("relate:"+weibo_content);
					}
					if (t % 1000 == 0) {
						System.out.println(dir + ", " + index + " : " + t);
					}
					t++;
				}
				entry = sevenZFile.getNextEntry();
			}

		} catch (Exception e) {
			System.out.println(dir + ", " + index + "  occur exception.");
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {

		for (int i = 6; i <= 8; i++) {
			for (int j = 1; j <= 31; j++) {
				if (i == 6 && j < 18)
					continue;
				if (i == 8 && j > 28)
					continue;

				Thread t = new weibo_sample_multi(i, j);
				t.start();
			}
		}
	}
}
