package br.ucb.caracol.dados;

import java.util.ArrayList;

public class Simbolos {
	
	private ArrayList<Character> simbolos;

	public Simbolos() {
		setSimbolos(new ArrayList<Character>());
		
		getSimbolos().add(':');
		getSimbolos().add('=');
		getSimbolos().add('(');
		getSimbolos().add(')');
		getSimbolos().add(',');
		getSimbolos().add('.');
		getSimbolos().add(';');
		getSimbolos().add('[');
		getSimbolos().add(']');
		getSimbolos().add('<');
		getSimbolos().add('>');
		getSimbolos().add('*');
		getSimbolos().add('/');
		getSimbolos().add('+');
		getSimbolos().add('-');
		getSimbolos().add('_');
		getSimbolos().add('{');
		getSimbolos().add('}');
		//getSimbolos().add(' ');
		
	}
	
	public boolean verificaSimbolos(char simbolo){
		return (getSimbolos().contains(simbolo));
	}
	
	public boolean verificaSimbolos(char simbolo, String carac){	
		return ((getSimbolos().contains(simbolo)) || (simbolo+"").equals("\n") || (simbolo+"").equals("\t")) &&  !(simbolo+"").equals(carac);
	}
	
	public boolean verificaVazio(char vazio){
		return (vazio == ' ');
	}
	
	public ArrayList<Character> getSimbolos() {
		return simbolos;
	}

	public void setSimbolos(ArrayList<Character> simbolos) {
		this.simbolos = simbolos;
	}
	
}
