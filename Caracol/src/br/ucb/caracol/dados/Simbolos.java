package br.ucb.caracol.dados;

import java.util.ArrayList;

public class Simbolos {
	
	private ArrayList<Character> simbolos;

	public Simbolos(ArrayList<Character> simbolos) {
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
		
		
	}

	public ArrayList<Character> getSimbolos() {
		return simbolos;
	}

	public void setSimbolos(ArrayList<Character> simbolos) {
		this.simbolos = simbolos;
	}
	

}
