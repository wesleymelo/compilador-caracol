package br.ucb.caracol.dados;


public class Caracol {
	
	private Simbolos simbolos;
	private Alfabeto alfabeto;
	private Numero numeros;
	private KeysWords keysWords;
	
	
	public Caracol() {
		setSimbolos(new Simbolos());
		setAlfabeto(new Alfabeto());
		setNumeros(new Numero()); 
		setKeysWords(new KeysWords());
	}
	
	public KeysWords getKeysWords() {
		return keysWords;
	}

	public void setKeysWords(KeysWords keysWords) {
		this.keysWords = keysWords;
	}

	public Simbolos getSimbolos() {
		return simbolos;
	}
	public void setSimbolos(Simbolos simbolos) {
		this.simbolos = simbolos;
	}
	public Alfabeto getAlfabeto() {
		return alfabeto;
	}
	public void setAlfabeto(Alfabeto alfabeto) {
		this.alfabeto = alfabeto;
	}
	public Numero getNumeros() {
		return numeros;
	}
	public void setNumeros(Numero numeros) {
		this.numeros = numeros;
	}
	
	

}
