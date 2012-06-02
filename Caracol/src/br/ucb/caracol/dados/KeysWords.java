package br.ucb.caracol.dados;

import java.util.ArrayList;

public class KeysWords {
	
	private ArrayList<String> keysWords;

	public KeysWords() {
		
		setKeysWords(new ArrayList<String>());
		getKeysWords().add("el_programa");
		getKeysWords().add("la_funcion");
		getKeysWords().add("el_final");
		getKeysWords().add("comienza");
		getKeysWords().add("int");
		getKeysWords().add("vetor");
		getKeysWords().add("si");
		getKeysWords().add("entonces");
		getKeysWords().add("si_no");
		getKeysWords().add("repeticion");
		getKeysWords().add("exploracion");
		getKeysWords().add("letra");
		getKeysWords().add("hasta");
		getKeysWords().add("o");
		getKeysWords().add("y");
		getKeysWords().add("no");
	}

	public ArrayList<String> getKeysWords() {
		return keysWords;
	}

	public void setKeysWords(ArrayList<String> keysWords) {
		this.keysWords = keysWords;
	}
	
	public boolean verificaPalavrasReservadas(String string){
		return (getKeysWords().contains(string));
	}
	
	
	
	
}
