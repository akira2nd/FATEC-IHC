package br.com.crushakirabot;

import java.util.LinkedList;

public class DetalheMonstro{
	
	private String nome;
	private String urlImg;
	private String urlCompleta;
	private LinkedList<Drop> drops;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUrlImg() {
		return urlImg;
	}
	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}
	public LinkedList<Drop> getDrops() {
		return drops;
	}
	
	public void setDrops(LinkedList<Drop> drop) {
		this.drops = drop;
	}
	
	public String getUrlCompleta() {
		return urlCompleta;
	}
	public void setUrlCompleta(String urlCompleta) {
		this.urlCompleta = urlCompleta;
	}

}
