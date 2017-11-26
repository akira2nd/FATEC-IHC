package br.com.crushakirabot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UrlRaspagem {

	private final String divine = "https://www.divine-pride.net";

	public LinkedList<Monstro> buscaMonstro(String nomeMonstro) throws Exception {

		LinkedList<Monstro> lista = new LinkedList<>();

		Document doc = Jsoup.connect(divine + "/database/monster?Name=" + nomeMonstro.replace(" ", "%20")
				+ "&Map=&Item=&Scale=&Element=&Race=&minLevel=1&maxLevel=200&minFlee=1&maxFlee=800&minHit=1&maxHit=1600&Flag=&Page=1").header("Accept-Language", "pt")
				.get();

		Elements tabela = doc.select("table tbody a[href]");

		for (Element link : tabela) {
			lista.add(new Monstro(link.text() + " ID: " + link.attr("href").substring(18, 22), link.attr("href")));
		}

		return lista;
	}

	public DetalheMonstro buscaDetalhes(String urlDetalhe) throws Exception {

		DetalheMonstro detalhe = new DetalheMonstro();
		LinkedList<Drop> drops = new LinkedList<>();

		Document doc = Jsoup.connect(divine + urlDetalhe).header("Accept-Language", "pt").get();

		Elements tbMonstros = doc.select("div.widget-content");
		
		detalhe.setUrlCompleta(tbMonstros.get(0).baseUri());
		detalhe.setNome(tbMonstros.get(0).getElementsByClass("entry-title").text().split("iRO")[0]);
		detalhe.setUrlImg(tbMonstros.get(0).getElementsByAttributeValueContaining("src", "mobs").attr("src"));

		try {

			for (int i = 0; i < tbMonstros.get(0)
					.getElementsByClass("table table-bordered table-striped table-condensed").get(2)
					.getElementsByTag("img").size(); i++) {
				drops.add(
						new Drop(
								tbMonstros.get(0)
										.getElementsByClass("table table-bordered table-striped table-condensed").get(2)
										.getElementsByTag("a").get(i).text()
										+ " | Taxa de drop: "
										+ tbMonstros
												.get(0)
												.getElementsByClass(
														"table table-bordered table-striped table-condensed")
												.get(2).getElementsByTag("tr").get(i + 1).getElementsByTag("td").get(5)
												.text(),
								divine + "/img/items/collection/bRO/" + tbMonstros.get(0)
										.getElementsByClass("table table-bordered table-striped table-condensed").get(2)
										.getElementsByTag("tr").get(i + 1).getElementsByTag("td").get(0).text()
										.substring(1)));
			}
		} catch (Exception e) {
			System.out.println("sem tabela de drop");
		}
		detalhe.setDrops(drops);

		return detalhe;
	}

	public LinkedList<Item> buscaItem(String nomeItem) throws Exception {

		LinkedList<Item> lista = new LinkedList<>();

		Document doc = Jsoup.connect(divine + "/database/item?Name=" + nomeItem.replace(" ", "%20")
				+ "&function=&find=Busca").header("Accept-Language", "pt")
				.get();

		Elements tabela = doc.select("table tbody a[href]");

		for (Element link : tabela) {
			lista.add(new Item(link.text() + " ID: " + link.attr("href").split("/")[3],link.attr("href")));
		}

		return lista;
	}
	
	public DetalheItem detalheItem(String urlDetalhe) throws Exception {
		
		Document doc = Jsoup.connect(divine+urlDetalhe).header("Accept-Language", "pt").get();
		DetalheItem detalhe = new DetalheItem();
		LinkedList<String> lista = new LinkedList<>();
		Elements tbItem = doc.select("div.widget-content");
		
		tbItem.get(0).getElementsByClass("mon_table").get(0).select("br").append("\\n");
		
		
		detalhe.setUrlCompleta(tbItem.get(0).baseUri());
		detalhe.setNome(tbItem.get(0).getElementsByClass("entry-title").get(0).text().split("iRO")[0]);
		detalhe.setUrlImg(tbItem.get(0).getElementsByClass("mon_table").get(0).getElementsByTag("img").attr("src"));
		detalhe.setDescricao(tbItem.get(0).getElementsByClass("mon_table").get(0).text().replace("\\n", "\n"));
		
		try {
			detalhe.setUrlImg(tbItem.get(0).getElementById("item_sidebar").getElementsByTag("img").attr("src"));
		} catch (Exception e) {
			
		}
								
		for(int i = 1; i < tbItem.get(0).getElementsByClass("table table-bordered table-striped table-condensed fullwidth").get(1).getElementsByTag("tr").size();i++) {
			lista.add("Dropado por: "+tbItem.get(0).getElementsByClass("table table-bordered table-striped table-condensed fullwidth").get(1).getElementsByTag("tr").get(i)
					.getElementsByTag("td").get(1).text() + " Chance: "+tbItem.get(0).getElementsByClass("table table-bordered table-striped table-condensed fullwidth").get(1).getElementsByTag("tr").get(i)
					.getElementsByTag("td").get(13).text());
		}
		
		detalhe.setDrops(lista);	
		
		
		return detalhe;
	}
	
	
	

}
