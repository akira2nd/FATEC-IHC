package br.com.crushakirabot;

public class Drop {
	
	private String item;
	private String urlItem;
		
	public Drop(String item, String urlItem) {
		this.item = item;
		this.urlItem = urlItem;
	}
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getUrlItem() {
		return urlItem;
	}
	public void setUrlItem(String urlItem) {
		this.urlItem = urlItem;
	}
}
