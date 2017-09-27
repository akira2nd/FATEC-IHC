package br.com.crushakirabot;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Url {

	private final String url = "https://www.divine-pride.net";

	public Document connection(String url) throws IOException {

		Connection con = (Connection) Jsoup.connect(url).userAgent(
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
				.timeout(10000);
		Connection.Response resp = con.execute();
		Document doc = null;
		if (resp.statusCode() == 200) {
			// doc = Jsoup.connect(url).userAgent("Mozilla").get();
			doc = con.get();
		}

		return doc;
	}

	public String procurarMonstro(String nomeMonstro) {
		String urlMonstro = url + "/database/monster?Name=" + nomeMonstro.replace(" ", "%20")
				+ "&Map=&Item=&Scale=&Element=&Race=&minLevel=1&maxLevel=200&minFlee=1&maxFlee=800&minHit=1&maxHit=1600&Flag=&Page=1";
		return urlMonstro;
	}
	
	public String urlDetalhe(String url) {
		return this.url + url;
	}

}
/*
 * System.out.println("exibindo: " + deAte.get(0).text().replace("of", "de"));
 * 
 * for(Element link: paginas) {
 * 
 * System.out.println("text : " +link.text());
 * System.out.println("link : "+link.attr("href"));
 * 
 * }
 * 
 * Document doc = Jsoup.connect(
 * "https://www.divine-pride.net/database/monster?Name=&Map=&Item=&Scale=&Element=&Race=&minLevel=1&maxLevel=200&minFlee=1&maxFlee=800&minHit=1&maxHit=1600&Flag=&Page=1"
 * ).get(); Elements tbMonstros = doc.select("table tbody a[href]"); Elements
 * deAte = doc.select("div.pagination-centered span"); Elements paginas =
 * doc.select("div.pagination-centered ul.pagination li a");
 * 
 * for (Element link : tbMonstros) { System.out.println("text : " +
 * link.text()); System.out.println("link : " + link.attr("href")); }
 * 
 * 
 * System.out.println("exibindo: " + deAte.get(0).text().replace("of", "de"));
 * 
 * for(Element link: paginas) { System.out.println("pagina " + link.text());
 * System.out.println("link href: " + link.attr("href")); } }
 */
