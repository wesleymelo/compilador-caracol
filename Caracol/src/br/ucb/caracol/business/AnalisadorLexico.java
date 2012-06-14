
package br.ucb.caracol.business;


import java.util.ArrayList;
import br.ucb.caracol.dados.KeysWords;
import br.ucb.caracol.enuns.EnumSimboloComposto;
import br.ucb.caracol.exceptions.CompilatorException;
import br.ucb.caracol.servicos.Validacoes;
import br.ucb.caracol.view.View;

public class AnalisadorLexico {
	public static void analisadorLexico(String codigo) {


		int tam = codigo.length();
		boolean isComentario = false, flag;

		ArrayList<String> temp = new ArrayList<String>();

		for (int i = 0; i < tam; i++) { 
			if(Validacoes.isTerminarNaoSimbolo(codigo.charAt(i))){
				if(i+1 < tam){
					flag = isComentario;
					isComentario = Validacoes.isComentario(codigo.charAt(i),codigo.charAt(i+1), isComentario);
					if(flag && !isComentario)
						i+=2;
				}
				if(!isComentario && i < codigo.length()){
					if(!Validacoes.isValido(codigo.charAt(i))){
						if(Validacoes.isTerminarNaoSimbolo(codigo.charAt(i))){
							View.showFeedBack("ERRO 01: SÍMBOLO DESCONHECIDO: [ "+codigo.charAt(i)+" ]\n");
							throw new CompilatorException("ERRO 01: SÍMBOLO DESCONHECIDO: [ "+codigo.charAt(i)+" ]\n");
						}
					}else{
						if(Validacoes.isLetra(codigo.charAt(i)))
							i=verificaLetra(codigo,i,temp);
						else if(Validacoes.isDigito(codigo.charAt(i)))
							i=verificaDigito(codigo, i, temp);
						if(Validacoes.isSimbolo(codigo.charAt(i)))
							i=verificaSimbolo(codigo, i, temp);
					}
				}
			}
		}
		if(isComentario){
			View.showFeedBack("ERRO 02: FIM DE COMENTÁRIO ESPERADO\n");
			throw new CompilatorException("ERRO 02: FIM DE COMENTÁRIO ESPERADO\n");
		}
		AnalisadorSintatico sintatico = new AnalisadorSintatico();
		sintatico.verificaCodigo(codigo, temp);
		
	}


	public static int verificaDigito(String codigo, int i, ArrayList<String> temp){
		StringBuilder valor= new StringBuilder();
		while((i <= codigo.length()-1) && (Validacoes.isDigito(codigo.charAt(i)))){
			if(Validacoes.isTerminarNaoSimbolo(codigo.charAt(i))){
				if(!Validacoes.isTerminalSymbol(codigo.charAt(i))){
					valor.append(codigo.charAt(i));
					if(i <= codigo.length()-1)
						i++;
				}
			}
		}
		boolean isNumberValid = Validacoes.isNumberValid(valor.toString()); 
		if(isNumberValid){
			temp.add(valor.toString());
			return verificaSequencia(valor, codigo, i, "Lexema numérico: [ ");
		}
		//View.showFeedBack("Palavra não Identificada: [ "+valor.toString()+" ]\n");
		return i;  
	}

	public static int verificaLetra(String codigo, int i, ArrayList<String> temp){

		StringBuilder valor= new StringBuilder();
		while((i <= codigo.length()-1) && (Validacoes.isLetra(codigo.charAt(i)) || (codigo.charAt(i) == '_'))){
			if(Validacoes.isTerminarNaoSimbolo(codigo.charAt(i))){
				if(!Validacoes.isTerminalSymbol(codigo.charAt(i))){
					valor.append(codigo.charAt(i));
					if(i != codigo.length())
						i++;
				}
			}

		}

		boolean isWordValid = Validacoes.isWordValid(valor.toString()); 

		if(isWordValid){

			if(verificaKeyWord(valor.toString())){
				temp.add(valor.toString());
				return verificaSequencia(valor, codigo, i, "Palavra Reservada: [ ");
			}
			temp.add(valor.toString());
			return verificaSequencia(valor, codigo, i, "Lexema não numérico: [ ");
		}
		//View.showFeedBack("Palavra não Identificada: [ "+valor.toString()+" ]\n");
		return i;    
	}

	public static int verificaSimbolo( String codigo, int i, ArrayList<String> temp){
		StringBuilder valor = new StringBuilder();
		//boolean flag = false;
		if(i <= codigo.length()-1){
			valor.append(codigo.charAt(i));
			if(i != codigo.length()){
				i++;
				if(i < codigo.length()){
					if(Validacoes.isSimbolo(codigo.charAt(i))){
						if(verificaSimboloComposto(codigo.charAt(i-1),codigo.charAt(i))){
							valor.append(codigo.charAt(i));
							i++;
							temp.add(valor.toString());
							return verificaSequencia(valor, codigo, i,"Símbolo composto: [ ");
						}
					}
				}
				temp.add(valor.toString());
				return verificaSequencia(valor, codigo, i,"Símbolo Simples: [ ");
			}
		}
		temp.add(valor.toString());
		return verificaSequencia(valor, codigo, i,"Símbolo Simples: [ ");

	}


	private static boolean verificaSimboloComposto(char charAt, char charAt2) {
		StringBuilder composto = new StringBuilder();
		composto.append(charAt);
		composto.append(charAt2);
		return EnumSimboloComposto.findSimboloComp(composto.toString());
		
	}


	public static int verificaSequencia(StringBuilder valor, String codigo, int i, String msgNormal){
		/*if((i >= codigo.length()- 1) || Validacoes.isTerminalSymbol(codigo.charAt(i)+""))
			View.showFeedBack(msgNormal+valor.toString()+" ]\n");*/
		if(i < codigo.length()-1){
			i++;
			valor.append(codigo.charAt(i));
			return i-2 ;
		}
		return i-1;
	}

	public static boolean verificaKeyWord(String valor){

		KeysWords keys = new KeysWords();
		return keys.verificaPalavrasReservadas(valor);

	}
}
