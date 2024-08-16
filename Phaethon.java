import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Phaethon {
	public static void main(String[] args) throws Exception {
		System.out.println("欢迎使用本图包下载器！\n\033[31m----注意事项----\033[0m\n本程序只适用于下载www.acgnbus.com中的图片，如想要下载https://www.acgnbus.com/cos/\033[4m447043\033[0m.html中的所有图片，图包ID请输入\033[4m447043\033[0m。");
		System.out.print("请输入图包ID：");
		Scanner pic = new Scanner(System.in);
		String pic_String = pic.nextLine();
		URL url = new URL("https://www.acgnbus.com/cos/" + pic_String + ".html");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 12; Pixel 6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Mobile Safari/537.36");
		BufferedReader init = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String webLine;
		String title = null;
		int picCount = 1;
		String m1 = null;
		while ((webLine = init.readLine()) != null) {
			try {
				title = webLine.substring(webLine.indexOf("<h1>") + 4, webLine.indexOf("</h1>"));
				Pattern p = Pattern.compile("(\\[\\d*P\\])");
				Matcher m = p.matcher(title);
				if (m.find()) {
					m1 = m.group(0);
					break;
				}
			} catch (Exception ignore) { }
		}
		picCount = Integer.valueOf(m1.substring(m1.indexOf("[") + 1, m1.indexOf("P]")));
		System.out.println("识别到图包名称：" + title);
		System.out.println("识别到图片数量：" + picCount);
		File d = new File("." + title);
		if (!d.exists()) {
			boolean dr = d.mkdirs();
			System.out.println("\033[32m" + "已创建目录." + title + "\033[0m");
		} else {
			System.out.println("\033[33m" + "已存在目录." + title + "\033[0m");
		}
		for (int i = 1; i <= picCount; i++) {
			URL url1 = new URL("https://www.acgnbus.com/cos/" + pic_String + "-" +  i + ".html");
			System.out.println("图片网址：" + url1);
			HttpURLConnection cTemp = (HttpURLConnection) url1.openConnection();
			cTemp.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 12; Pixel 6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Mobile Safari/537.36");
			BufferedReader reader = new BufferedReader(new InputStreamReader(cTemp.getInputStream()));
			String l;
			String url2 = null;
			while ((l = reader.readLine()) != null) {
				try {
					Pattern p = Pattern.compile("<img(.*?)/>");
					Matcher m = p.matcher(l);
					if (m.find()) {
						url2 = m.group(0).substring(m.group(0).indexOf("src=\"") + 5, m.group(0).indexOf(".webp\"") + 5);
						break;
					}
				} catch (Exception ignore) { }
			}
			System.out.println("图片地址：" + url2);
			URLConnection picConnect = new URL(url2).openConnection();
			InputStream inputStream = picConnect.getInputStream();
			FileOutputStream outputStream = new FileOutputStream(new File("." + title + "/" + i + ".webp"));
			int bytesRead;
			byte[] buffer = new byte[1024];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			inputStream.close();
			outputStream.close();
			System.out.println("\033[32m" + "已下载并写入磁盘：." + title + "/" + i + ".jpg" + "\033[0m");
			reader.close();
		}
	}
}

