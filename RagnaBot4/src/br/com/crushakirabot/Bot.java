package br.com.crushakirabot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@SuppressWarnings("deprecation")
public class Bot extends TelegramLongPollingBot {

	private String procMonstro = "Procurar por Monstro";
	private String procItem = "Procurar por Item";
	private SendMessage sendMsg;
	private boolean vMonstro, vItem, selecionado;
	private LinkedList<Monstro> listaMonstro = new LinkedList<>();
	private LinkedList<Item> listaItem = new LinkedList<>();
	private UrlRaspagem raspa = new UrlRaspagem();
	/*
	 * Monstro monstro = new Monstro(); Item item = new Item();
	 */

	@Override
	public String getBotToken() {
		return "443450063:AAFagvHQjdeBGKjaghGXF4JZ30-R5imnle0";
	}

	public String startMsg(String nome) {
		return "Bem Vindo " + nome + "!\nEstou aqui para ajudá-lo a localizar estatus de itens e monstros "
				+ "do jogo Ragnarok Online de forma rápida e fácil, vamos começar?";
	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onUpdateReceived(Update update) {

		sendMsg = new SendMessage().setChatId(update.getMessage().getChatId());
		System.out.println(update.getMessage().getText());

		if (update.getMessage().getText().equals("/start")) {

			sendMsg.setText(startMsg(update.getMessage().getFrom().getFirstName()));

			try {
				sendMessage(sendMsg);

			} catch (TelegramApiException e) {
				e.printStackTrace();
			}

			sendCustomKeyboard(sendMsg);

			vMonstro = false;
			vItem = false;
			selecionado = false;

		} else if (!vMonstro && !vItem) {
			
			validaMsg(update.getMessage().getText(), sendMsg);
			
		} else if (vMonstro && selecionado) {
			for (Monstro mon : listaMonstro) {
				System.out.println(mon.getNome()+ '\n' + mon.getUrl());
				if (update.getMessage().getText().equals(mon.getNome())) {
					try {
						DetalheMonstro detalhes = raspa.buscaDetalhes(mon.getUrl());
						sendImageFromUrl(detalhes, sendMsg);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		} else if (vMonstro) {
			try {
				listaMonstro = raspa.buscaMonstro(update.getMessage().getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			exibeMonKeyboard(sendMsg);
			selecionado = true;
		
		}else if(vItem && selecionado) {
			for(Item item: listaItem) {
				System.out.println(item.getNome()+ '\n' + item.getUrl());
				if (update.getMessage().getText().equals(item.getNome())) {
					try {
						DetalheItem detalhe = raspa.detalheItem(item.getUrl());
						sendImageFromUrl(detalhe, sendMsg);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			
		} else if (vItem) {
			try {
				listaItem = raspa.buscaItem(update.getMessage().getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			exibeItemKeyboard(sendMsg);
			selecionado = true;
			
			
		} else {
			validaMsg(update.getMessage().getText(), sendMsg);
		}

	}


	private void exibeMonKeyboard(SendMessage message) {
		// SendMessage message = new SendMessage();
		// message.setChatId(chatId);
		// Create ReplyKeyboardMarkup object
		if (listaMonstro.size() == 0) {
			message.setText("Nenhum Monstro localizado com esse nome :(\n\nDigite novamente.");
		} else {
			message.setText("Ver detalhes de qual dos monstros?");
		}
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<>();

		for (Monstro mon : listaMonstro) {
			KeyboardRow row = new KeyboardRow();
			row.add(mon.getNome());
			keyboard.add(row);
		}

		keyboardMarkup.setKeyboard(keyboard).setResizeKeyboard(true);
		// Add it to the message
		message.setReplyMarkup(keyboardMarkup);

		try {
			// Send the message
			sendMessage(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	private void exibeItemKeyboard(SendMessage message) {
		// SendMessage message = new SendMessage();
		// message.setChatId(chatId);
		// Create ReplyKeyboardMarkup object
		if (listaItem.size() == 0) {
			message.setText("Nenhum Item localizado com esse nome :(\n\nDigite novamente.");
		} else {
			message.setText("Ver detalhes de qual Item?");
		}
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<>();

		for (Item item : listaItem) {
			KeyboardRow row = new KeyboardRow();
			row.add(item.getNome());
			keyboard.add(row);
		}

		keyboardMarkup.setKeyboard(keyboard).setResizeKeyboard(true);
		// Add it to the message
		message.setReplyMarkup(keyboardMarkup);

		try {
			// Send the message
			sendMessage(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	public void validaMsg(String text, SendMessage message) {

		ReplyKeyboardRemove remove = new ReplyKeyboardRemove();

		if (text.equals(procMonstro)) {
			vMonstro = true;
			message.setText("Ok!\nEscreva qual o nome do Monstro");
			message.setReplyMarkup(remove);
		} else if (text.equals(procItem)) {
			vItem = true;
			message.setText("Ok!\nEscreva qual o nome do Item");
			message.setReplyMarkup(remove);
		} else {
			message.setText("Por Favor escolha uma opção valida.");
		}

		try {
			sendMessage(message);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sendCustomKeyboard(SendMessage message) {
		// SendMessage message = new SendMessage();
		// message.setChatId(chatId);
		message.setText("Escolha oque quer encontrar Itens ou Monstros?");
		// Create ReplyKeyboardMarkup object
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		// Create the keyboard (list of keyboard rows)
		List<KeyboardRow> keyboard = new ArrayList<>();
		// Create a keyboard row
		KeyboardRow row = new KeyboardRow();
		// Set each button, you can also use KeyboardButton objects if you need
		// something else than text
		row.add(procItem);

		// Add the first row to the keyboard
		keyboard.add(row);
		// Create another keyboard row
		row = new KeyboardRow();
		// Set each button for the second line
		row.add(procMonstro);

		// Add the second row to the keyboard
		keyboard.add(row);

		// Set the keyboard to the markup
		keyboardMarkup.setKeyboard(keyboard).setResizeKeyboard(true);
		// Add it to the message
		message.setReplyMarkup(keyboardMarkup);

		try {
			// Send the message
			sendMessage(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}

	public void sendImageFromUrl(DetalheMonstro detalhe, SendMessage message) {
		// Create send method
		SendPhoto sendPhotoRequest = new SendPhoto();
		// Set destination chat id
		sendPhotoRequest.setChatId(message.getChatId().toString());
		// Set the photo url as a simple photo
		message.setText(detalhe.getNome());
		sendPhotoRequest.setPhoto(detalhe.getUrlImg());
		try {
			// Execute the method
			sendMessage(message);
			sendPhoto(sendPhotoRequest);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		
		for(Drop d : detalhe.getDrops()) {
			message.setText(d.getItem());
			sendPhotoRequest.setPhoto(d.getUrlItem());
			try {
				// Execute the method
				sendMessage(message);
				sendPhoto(sendPhotoRequest);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
		
		message.setText("Mais informações em:\n"+detalhe.getUrlCompleta());
		try {
			sendMessage(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void sendImageFromUrl(DetalheItem detalhe, SendMessage message) {
		// Create send method
		SendPhoto sendPhotoRequest = new SendPhoto();
		// Set destination chat id
		sendPhotoRequest.setChatId(message.getChatId().toString());
		// Set the photo url as a simple photo
		message.setText(detalhe.getNome() + "\n"+ detalhe.getDescricao());
		sendPhotoRequest.setPhoto(detalhe.getUrlImg());
		try {
			// Execute the method
			sendMessage(message);
			sendPhoto(sendPhotoRequest);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		
		for(String s : detalhe.getDrops()) {
			message.setText(s);
			try {
				// Execute the method
				sendMessage(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
		
		message.setText("Mais informações em:\n"+detalhe.getUrlCompleta());
		try {
			sendMessage(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
