package br.com.crushakirabot;


public class Item {
	
	private String nome;
	private String urlImg;
	
	public Item(String nome, String url) {
		this.nome = nome;
		this.urlImg = url;
	}

	public String getNome() {
		return nome;
	}

	public String getUrl() {
		return urlImg;
	}
	
	
	
}
