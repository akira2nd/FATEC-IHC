package br.com.crushakirabot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UrlRaspagem extends Url{
	
	private Elements tbMonstros;
	private Elements linkImgMonstro;
	
	public ArrayList<Monstro> listarMonstros(Document doc){
		tbMonstros = doc.select("table tbody a[href]");
		ArrayList<Monstro> listaDMonstro = new ArrayList<>();
	    for (Element link : tbMonstros) {
	    	listaDMonstro.add(new Monstro(link.text()+" ID:"+link.attr("href").substring(18, 22), link.attr("href") ));
	        System.out.println("text : " + link.text());
	        System.out.println("link : " + link.attr("href"));
	    }
			
		return listaDMonstro;
	}
	
	public ArrayList<String> linkImg(Document doc){
		
		List<String> lista = new ArrayList<>();
		
		linkImgMonstro = doc
				.select("div.widget-content legend.entry-title + img[src], table tbody img[src], table tbody a[href]");
		
		for (Element link : tbMonstros) {
			if(!link.attr("src").isEmpty()) {
				
			}

		}
		
		
		return null;
	}

}
