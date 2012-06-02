package br.ucb.caracol.dados;

public class Identificadores {
	public static boolean verificaIdentificador(String identificador){
		if(identificador.charAt(0)>='0' && identificador.charAt(0)<='9' ){
			return false;
		}
		return true;
	}
	public static boolean verificaId(String token){
		Caracol li = new Caracol();
		KeysWords pr = new KeysWords();
		return !(li.getNumeros().verificaNumero(token) || li.getSimbolos().verificaSimbolos(token.charAt(0)) 
				|| pr.verificaPalavrasReservadas(token));
	}
}
