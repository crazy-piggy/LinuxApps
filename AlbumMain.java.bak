import java.net.URL;
import java.net.URLConnection;
import java.io.*;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println("欢迎使用本图包下载器！\n\033[31m----注意事项----\033[0m\n本程序只适用于下载www.mhgirl.com中的图片，如想要下载https://www.mhgirl.com/tuji/\033[4m68798\033[0m.html中的所有图片，请输入\033[4m68798\033[0m。");
		System.out.print("请输入图包ID：");
		Scanner pic = new Scanner(System.in);
		String pic_String = pic.nextLine();
		URL url = new URL("https://www.mhgirl.com/tuji/" + pic_String + ".html");
		BufferedReader init = new BufferedReader(new InputStreamReader(url.openStream()));
		String webLine;
		String title = null;
		int picCount = 1;
		while ((webLine = init.readLine()) != null) {
			try {
				title = webLine.substring(webLine.indexOf("<h1>") + 4, webLine.indexOf("</h1>"));
			} catch (Exception ignore) { }
			try {
				picCount = Integer.valueOf(webLine.substring(webLine.indexOf("<p>图片数量： ") + 9, webLine.indexOf("张</p>")));
				if (picCount != 1) break;
			} catch (Exception ignore) {
			}
		}
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
			URL url1 = new URL("https://www.mhgirl.com/tuji/" + pic_String + "_" +  i + ".html");
			System.out.println("图片网址" + url1);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url1.openStream()));
			String l;
			while ((l = reader.readLine()) != null) {
				try {
					String url2 = l.substring(l.indexOf("<img src=\"") + 10, l.indexOf("\"><div id=\"page\">"));
					System.out.println("图片地址：" + url2);
					URLConnection connection = new URL(url2).openConnection();
					InputStream inputStream = connection.getInputStream();
					FileOutputStream outputStream = new FileOutputStream(new File("." + title + "/" + i + ".jpg"));
					int bytesRead;
					byte[] buffer = new byte[1024];
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						    outputStream.write(buffer, 0, bytesRead);
					}
					inputStream.close();
					outputStream.close();
					System.out.println("\033[32m" + "已下载并写入磁盘：." + title + "/" + i + ".jpg" + "\033[0m");
				} catch (Exception ignore) { }
			}
			reader.close();
		}
	}
}

