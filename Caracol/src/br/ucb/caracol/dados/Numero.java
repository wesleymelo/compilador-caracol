package br.ucb.caracol.dados;

import java.util.ArrayList;

public class Numero {
	
	private ArrayList<Character> numeros;
	
	public Numero() {
		setNumeros(new ArrayList<Character>());
		getNumeros().add('0');
		getNumeros().add('1');
		getNumeros().add('2');
		getNumeros().add('3');
		getNumeros().add('4');
		getNumeros().add('5');
		getNumeros().add('6');
		getNumeros().add('7');
		getNumeros().add('8');
		getNumeros().add('9');
	}
	public boolean verificaNumeros(String numero){
		if(numero.isEmpty())
			return false;
		for (char character : numero.toCharArray()) {
			if(!getNumeros().contains(character))
				return false;
		}
		return true;
	}
	public boolean verificaNumeros(char numero){
		return (getNumeros().contains(numero));
	}
	
	public ArrayList<Character> getNumeros() {
		return numeros;
	}

	public void setNumeros(ArrayList<Character> numeros) {
		this.numeros = numeros;
	}
	

}
