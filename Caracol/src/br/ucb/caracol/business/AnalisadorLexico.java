
package br.ucb.caracol.business;


import java.util.ArrayList;
import br.ucb.caracol.dados.KeysWords;
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
							//throw new CompilatorException("ERRO 01: SÁMBOLO DESCONHECIDO: [ "+codigo.charAt(i)+" ]\n");
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
		AnalisadorSintatico sintatico = new AnalisadorSintatico();
		sintatico.verificaCodigo(codigo, temp);

		if(isComentario){
			View.showFeedBack("ERRO 02: FIM DE COMENTÁRIO ESPERADO\n");
			throw new CompilatorException("ERRO 02: FIM DE COMENTÁRIO ESPERADO\n");
		}
	}


	public static int verificaDigito(String codigo, int i, ArrayList<String> temp){
		StringBuilder valor= new StringBuilder();
		while((i <= codigo.length()-1) && (Validacoes.isDigito(codigo.charAt(i)))){
			if(Validacoes.isTerminarNaoSimbolo(codigo.charAt(i))){
				if(!Validacoes.isTerminalSymbol(codigo.charAt(i))){
					valor.append(codigo.charAt(i));
					if(i <= codigo.length()-1)
						i++;
					//System.out.println("i: "+i+" i-1: "+(codigo.length()-1)+"codigo: "+codigo.charAt(i));
				}
			}
		}
		boolean isNumberValid = Validacoes.isNumberValid(valor.toString()); 
		if(isNumberValid){
			return verificaSequencia(valor, codigo, i, "Lexema numérico: [ ",temp);
		}
		View.showFeedBack("Palavra não Identificada: [ "+valor.toString()+" ]\n");
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
			
			if(verificaKeyWord(valor.toString()))
				return verificaSequencia(valor, codigo, i, "Palavra Reservada: [ ",temp);
			return verificaSequencia(valor, codigo, i, "Lexema não numérico: [ ",temp);
		}
		View.showFeedBack("Palavra não Identificada: [ "+valor.toString()+" ]\n");
		return i;    
	}

	public static int verificaSimbolo( String codigo, int i, ArrayList<String> temp){
		StringBuilder valor = new StringBuilder();
		//boolean flag = false;
		if(i <= codigo.length()-1){
			valor.append(codigo.charAt(i));
			if(i != codigo.length()){
				i++;
				/*if(i < codigo.length()-1){
					if(Validacoes.isSimbolo(codigo.charAt(i))){
						valor.append(codigo.charAt(i));
						flag = true;
						if(Validacoes.isCompostSymbol(valor.toString())){
							return verificaSequencia(valor, codigo, i,"Símbolo Composto: [ ");
						}
					}
				}
				if(flag)
					valor.deleteCharAt(1);*/
				return verificaSequencia(valor, codigo, i,"Símbolo Simples: [ ",temp);
			}
		}
		return verificaSequencia(valor, codigo, i,"Símbolo Simples: [ ",temp);

	}


	public static int verificaSequencia(StringBuilder valor, String codigo, int i, String msgNormal, ArrayList<String> temp){
		temp.add(valor.toString());
		if((i >= codigo.length()- 1) || Validacoes.isTerminalSymbol(codigo.charAt(i)+""))
			View.showFeedBack(msgNormal+valor.toString()+" ]\n");
		if(i < codigo.length()-1){
			i++;
			valor.append(codigo.charAt(i));
			return i-2 ;
		}
		return i-1;
		/*valor.append(codigo.charAt(i));
			while(!Validacoes.isTerminalSymbol(codigo.charAt(i)+"")){
				if(i < codigo.length()){
					i++;
					valor.append(codigo.charAt(i));
				}
			}
			View.showFeedBack("Palavra não Identificada: [ "+valor.toString()+" ]\n");
		}
		return i;*/
	}

	public static boolean verificaKeyWord(String valor){

		KeysWords keys = new KeysWords();
		return keys.verificaPalavrasReservadas(valor);

	}

	/*
	 * 
	 * public static void verificaCodigo(String string) throws CompilacaoException{
		boolean flag=false;
		for(int i=0;i<string.length();i++){
			if((string.charAt(i) != '\n') && (string.charAt(i) != '\t')){
				if((string.charAt(i) != '{') && (!flag)){
					if(!verificaChar(string.charAt(i)))
						throw new CompilacaoException("SIMBOLO INVALIDO");
				}else
					flag=true;
				if((string.charAt(i)=='}') && (flag==true))
					flag=false;
				else if(string.charAt(i) == '}'){
					throw new CompilacaoException("Chave nao encontrada");
				}
			}
		}
		if(flag == true){
			throw new CompilacaoException("Fim de comentario esperado");
		}
	}	
	public static boolean verificaChar(char caracter){
		Linguagem lingua = new Linguagem();
		if(!(lingua.getSimbolos().verificaSimbolos(caracter) ||	
				lingua.getAlfabeto().verificaAlfabeto(caracter) ||
				lingua.getNumeros().verificaNumeros(caracter) || caracter==13)){

			Print.msgErro("SIMBOLO INVALIDO","ERRO");
			return false;
		}
		return true;
	}
	 * 
	 * 
	 */








}
