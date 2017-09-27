package br.com.crushakirabot;

import java.io.IOException;
import java.util.ArrayList;
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
public class Bot extends TelegramLongPollingBot{
	
	private String procMonstro = "Procurar por Monstro";
	private String procItem = "Procurar por Item";
	SendMessage sendMsg;
	private boolean vMonstro = false;
	private boolean vItem = false;
	private UrlRaspagem urlRasp = new UrlRaspagem();
	private ArrayList<Monstro> lista = new ArrayList<Monstro> ();
	/*
	Monstro monstro = new Monstro();
	Item item = new Item();
	*/
	
	@Override
	public String getBotToken() {
		return "443450063:AAFagvHQjdeBGKjaghGXF4JZ30-R5imnle0";
	}
		
	public String startMsg(String nome) {
		return "Bem Vindo "+nome+"!\nEstou aqui para ajudá-lo a localizar estatus de itens e monstros "+ 
				"do jogo Ragnarok Online de forma rápida e fácil, vamos começar?";
	}
	
	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void onUpdateReceived(Update update){
		
		sendMsg = new SendMessage().setChatId(update.getMessage().getChatId());
		System.out.println(update.getMessage().getText());
		
		if(update.getMessage().getText().equals("/start")) {

			sendMsg.setText(startMsg(update.getMessage().getFrom().getFirstName()));
			
			try {
				sendMessage(sendMsg);
				
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
			sendCustomKeyboard(sendMsg);
			
			setvItem(false);
			setvMonstro(false);
			
		}else {
								
			if(isvMonstro()) {
				for(Monstro mon : lista) {
					if(update.getMessage().getText().equals(mon.getNome())) {
						System.out.println(true);
						System.out.println(mon.getUrl());
						//sendImageFromUrl(, sendMsg.getChatId());
					}
				}
				try {
					String procura = urlRasp.procurarMonstro(update.getMessage().getText());
					lista = urlRasp.listarMonstros(urlRasp.connection(procura));
				} catch (IOException e) {
					e.printStackTrace();
				}
				exibeMonKeyboard(sendMsg);
				
			}else if(isvItem()) {
				
			}else {
				validaMsg(update.getMessage().getText(), sendMsg);				
			}
			

		}
		
	}

		
	private void exibeMonKeyboard(SendMessage message) {
		//SendMessage message = new SendMessage();
        //message.setChatId(chatId);
        // Create ReplyKeyboardMarkup object
		if(lista.size() == 0) {
			message.setText("Nenhum Monstro localizado com esse nome :(\n\nDigite novamente.");
		}else {
			message.setText("Ver detalhes de qual dos monstros?");
		}
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        
        for(Monstro mon: lista) {
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

	public void validaMsg(String text, SendMessage message) {
		
		ReplyKeyboardRemove remove = new ReplyKeyboardRemove();
		
		if(text.equals(procMonstro)) {
			setvMonstro(true);
			message.setText("Ok!\nEscreva qual o nome do Monstro");
			message.setReplyMarkup(remove);
		}else if(text.equals(procItem)) {
			setvItem(true);
			message.setText("Ok!\nEscreva qual o nome do Item");
			message.setReplyMarkup(remove);
		}else {
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
        //SendMessage message = new SendMessage();
        //message.setChatId(chatId);
        message.setText("Escolha oque quer encontrar Itens ou Monstros?");

        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
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
	
    public void sendImageFromUrl(String url, String chatId) {
        // Create send method
        SendPhoto sendPhotoRequest = new SendPhoto();
        // Set destination chat id
        sendPhotoRequest.setChatId(chatId);
        // Set the photo url as a simple photo
        sendPhotoRequest.setPhoto(url);
        try {
            // Execute the method
            sendPhoto(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
	

	public boolean isvMonstro() {
		return vMonstro;
	}

	public void setvMonstro(boolean vMonstro) {
		this.vMonstro = vMonstro;
	}

	public boolean isvItem() {
		return vItem;
	}

	public void setvItem(boolean vItem) {
		this.vItem = vItem;
	}
	

}
