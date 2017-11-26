package br.com.crushakirabot;

import java.util.LinkedList;

public class DetalheItem {

	private String nome;
	private String urlImg;
	private String descricao;
	private String urlCompleta;
	private LinkedList<String> monCarry;
	
	
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
	public LinkedList<String> getDrops() {
		return monCarry;
	}
	
	public void setDrops(LinkedList<String> drop) {
		this.monCarry = drop;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getUrlCompleta() {
		return urlCompleta;
	}
	public void setUrlCompleta(String urlCompleta) {
		this.urlCompleta = urlCompleta;
	}

}
