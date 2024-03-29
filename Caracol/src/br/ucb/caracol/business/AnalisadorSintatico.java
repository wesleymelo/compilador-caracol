
package br.ucb.caracol.business;

import java.util.List;
import br.ucb.caracol.dados.Caracol;
import br.ucb.caracol.dados.Identificadores;
import br.ucb.caracol.dados.Numero;
import br.ucb.caracol.exceptions.CompilatorException;
import br.ucb.caracol.servicos.Servicos;
import br.ucb.caracol.view.View;

public class AnalisadorSintatico {
	private String codigo;
	private List<String> tokens;
	private int indexToken;
	private Boolean flag;
	private Boolean hasErro;
	
	public void verificaCodigo(String text, List<String> tokens) {
		setTokens(tokens);
		setCodigo(text);
		setIndexToken(0);
		flag = false;
		hasErro = false;
		formato_inicial();
	}

	private void formato_inicial() {
		if(getTokens().size()==0){
			View.showFeedBack("Linha: 01 - Nenhum c�digo encontrado");
			hasErro = true;
			throw new CompilatorException("Linha: 01 - Nenhum c�digo encontrado");
		}
		if(getTokens().get(getIndexToken()).equals("la_funcion") || getTokens().get(getIndexToken()).equals("el_program")){
			if(getTokens().get(getIndexToken()).equals("la_funcion")){
				dec_func();
			}	
			reconhecer("el_program");
			reconhecer("IDEN");
			reconhecer(";");
			corpo();
			reconhecer("el_final");
		}else{
			View.showFeedBack("Linha: 01 - Come�o de programa esperado");
			hasErro = true;
			throw new CompilatorException("Linha: 01 - Come�o de programa esperado");
		}
		if(!hasErro){
			View.showFeedBack("Programa fonte analisado. Nenhum erro encontrado.");
		}

	}

	private void corpo(){
		if(getTokens().get(getIndexToken()).equals("comienza")){
			reconhecer("comienza");
			while(getTokens().get(getIndexToken()).equals("int") || getTokens().get(getIndexToken()).equals("vetor")){
				dec_var();
			}
			cmd();
			while(getTokens().get(getIndexToken()).equals(";")){
				reconhecer(";");
				cmd();
			}			
			reconhecer("el_final");
		}
		else{
			View.showFeedBack("ERRO Linha: "+obterNumeroLinhaErro()+" - Esperado in�cio de corpo de programa"+"\n");
			hasErro = true;
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Esperado in�cio de corpo de programa");
		}

	}

	private void cmd() {
		boolean isCmd = false;
		if(Identificadores.verificaId(getTokens().get(getIndexToken()))){
			isCmd = true;
			reconhecer("IDEN");
			if(getTokens().get(getIndexToken()).equals("[") || getTokens().get(getIndexToken()).equals(":=")){
				if(getTokens().get(getIndexToken()).equals("[")){
					reconhecer("[");
					expr();
					reconhecer("]");
					reconhecer(":=");
					expr();
				}else{
					reconhecer(":=");
					expr();
				}
			}else if(getTokens().get(getIndexToken()).equals("(")){
					reconhecer("(");
					expr();
					while(getTokens().get(getIndexToken()).equals(",")){
						reconhecer(",");
						expr();
					}
					reconhecer(")");
			}
		}else if(getTokens().get(getIndexToken()).equals("si")){
			isCmd = true;
			reconhecer("si");
			expr();
			reconhecer("entonces");
			bloco();
			if(getTokens().get(getIndexToken()).equals("si_no")){
				reconhecer("si_no");
				bloco();
			}
		}else if(getTokens().get(getIndexToken()).equals("repeticion")){
			isCmd = true;
			reconhecer("repeticion");
			cmd();
			while(getTokens().get(getIndexToken()).equals(";")){
				reconhecer(";");
				cmd();
			}
			reconhecer("hasta");
			expr();
		}else if(getTokens().get(getIndexToken()).equals("exploracion")){
				isCmd = true;
				reconhecer("exploracion");
				reconhecer("(");
				reconhecer("IDEN");
				while(getTokens().get(getIndexToken()).equals(",")){
					reconhecer(",");
					reconhecer("IDEN");									
				}
				reconhecer(")");
		}else if(getTokens().get(getIndexToken()).equals("letra")){
			isCmd = true;
			reconhecer("letra");
			reconhecer("(");
			reconhecer("IDEN");						
			while(getTokens().get(getIndexToken()).equals(",")){
				reconhecer(",");
				reconhecer("IDEN");									
			}
			reconhecer(")");
		}

		if(!isCmd){
			View.showFeedBack("ERRO Linha: "+(obterNumeroLinhaErro()+1)+" - Comando esperado"+"\n");
			hasErro = true;
			throw new CompilatorException("ERRO Linha: "+(obterNumeroLinhaErro()+1)+" - Comando esperado");
		}	
	}

	private void bloco() {
		if(getTokens().get(getIndexToken()).equals("{")){
			reconhecer("{");
			cmd();
			while(getTokens().get(getIndexToken()).equals(";")){
				reconhecer(";");
				cmd();
			}
			reconhecer("}");
		}
		else{
			View.showFeedBack("ERRO Linha: "+obterNumeroLinhaErro()+" - Bloco esperado"+"\n");
			hasErro = true;
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Bloco esperado");
		}
	}	

	private void expr() {
		if(getTokens().get(getIndexToken()).equals("+") || getTokens().get(getIndexToken()).equals("-") || 
				getTokens().get(getIndexToken()).equals("(") || getTokens().get(getIndexToken()).equals("no") || Identificadores.verificaId(getTokens().get(getIndexToken())) || 
				new Caracol().getNumeros().verificaNumero(getTokens().get(getIndexToken()))){
			siexpr();
			if(getTokens().get(getIndexToken()).equals("=") || getTokens().get(getIndexToken()).equals("<") || getTokens().get(getIndexToken()).equals(">") || 
			    getTokens().get(getIndexToken()).equals("<>") || getTokens().get(getIndexToken()).equals("<=") || getTokens().get(getIndexToken()).equals(">=")){
				if(getTokens().get(getIndexToken()).equals("="))
					reconhecer("=");
				else if(getTokens().get(getIndexToken()).equals("<"))
					reconhecer("<");
				else if(getTokens().get(getIndexToken()).equals(">"))
					reconhecer(">");
				else if(getTokens().get(getIndexToken()).equals("<>"))
					reconhecer("<>");
				else if(getTokens().get(getIndexToken()).equals("<="))
					reconhecer("<=");
				else if(getTokens().get(getIndexToken()).equals(">="))
					reconhecer(">=");
				siexpr();
			}
			

		}
		else{
			View.showFeedBack("ERRO Linha: "+(obterNumeroLinhaErro()-1)+" - Esperada uma express�o"+"\n");
			hasErro = true;
			throw new CompilatorException("ERRO Linha: "+(obterNumeroLinhaErro()-2)+" - Esperada uma express�o");
		}

	}

	private void siexpr() {
		if(getTokens().get(getIndexToken()).equals("+") || getTokens().get(getIndexToken()).equals("-") || getTokens().get(getIndexToken()).equals("(") || 
				getTokens().get(getIndexToken()).equals("no") || Identificadores.verificaId(getTokens().get(getIndexToken())) ||
				new Caracol().getNumeros().verificaNumero(getTokens().get(getIndexToken()))){
			if(getTokens().get(getIndexToken()).equals("+") || getTokens().get(getIndexToken()).equals("-")){		
				if(getTokens().get(getIndexToken()).equals("+")){
					reconhecer("+");
				}else if(getTokens().get(getIndexToken()).equals("-")){
					reconhecer("-");
				}
			}
			System.out.println("Termo in : "+getTokens().get(getIndexToken()));
			termo();
			System.out.println("Termo out: "+getTokens().get(getIndexToken()));
			while(getTokens().get(getIndexToken()).equals("+") || getTokens().get(getIndexToken()).equals("-") || getTokens().get(getIndexToken()).equals("o")){
				if(getTokens().get(getIndexToken()).equals("+")){
					reconhecer("+");
				}else if(getTokens().get(getIndexToken()).equals("-")){
					reconhecer("-");
				}else if(getTokens().get(getIndexToken()).equals("o")){
					reconhecer("o");
				}
				termo();
			}
		}else{
			View.showFeedBack("ERRO Linha: "+obterNumeroLinhaErro()+" - Operador ou identificador esperado"+"\n");
			hasErro = true;
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Operador ou identificador esperado");
		}
	}

	private void termo() {
		if(getTokens().get(getIndexToken()).equals("+") || getTokens().get(getIndexToken()).equals("-") || getTokens().get(getIndexToken()).equals("(") || 
				getTokens().get(getIndexToken()).equals("no") || Identificadores.verificaId(getTokens().get(getIndexToken())) ||
				new Caracol().getNumeros().verificaNumero(getTokens().get(getIndexToken()))){
			fator();
			while (getTokens().get(getIndexToken()).equals("*") || getTokens().get(getIndexToken()).equals("/") || getTokens().get(getIndexToken()).equals("y")) {
				if(getTokens().get(getIndexToken()).equals("*"))
					reconhecer("*");
				else if(getTokens().get(getIndexToken()).equals("/"))
					reconhecer("/");
				else if(getTokens().get(getIndexToken()).equals("y"))
					reconhecer("y");
				fator();
			}
		}else{
			View.showFeedBack("ERRO Linha: "+obterNumeroLinhaErro()+" - Termo esperado"+"\n");
			hasErro = true;
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Termo esperado");
		}

	}



	private void fator() {
		if(getTokens().get(getIndexToken()).equals("(") || getTokens().get(getIndexToken()).equals("+") || getTokens().get(getIndexToken()).equals("-") ||
				getTokens().get(getIndexToken()).equals("no") || new Caracol().getNumeros().verificaNumero(getTokens().get(getIndexToken())) || 
				Identificadores.verificaId(getTokens().get(getIndexToken()))){				
			if(getTokens().get(getIndexToken()).equals("(")){
				reconhecer("(");
				expr();
				reconhecer(")");
			}else if(getTokens().get(getIndexToken()).equals("+")){
				reconhecer("+");
				expr();
			}else if(getTokens().get(getIndexToken()).equals("-")){
				reconhecer("-");
				expr();
			}else if(getTokens().get(getIndexToken()).equals("no")){
				reconhecer("no");
				fator();
			}else if(new Caracol().getNumeros().verificaNumero(getTokens().get(getIndexToken()))){
				constante();
			}else if(Identificadores.verificaId(getTokens().get(getIndexToken()))){
				reconhecer("IDEN");
			}
		}else{
			View.showFeedBack("ERRO Linha: "+obterNumeroLinhaErro()+" - Fator esperado"+"\n");
			hasErro = true;
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Fator");
		}


	}

	private void constante() {
		if(getTokens().get(getIndexToken()).equals("+") || getTokens().get(getIndexToken()).equals("-") || new Caracol().getNumeros().verificaNumero(getTokens().get(getIndexToken()))){
			if(getTokens().get(getIndexToken()).equals("+")){
				reconhecer("+");
			}else if(getTokens().get(getIndexToken()).equals("-")){
				reconhecer("-");
			}
			if(new Caracol().getNumeros().verificaNumero(getTokens().get(getIndexToken()))){
				//reconhecer(getTokens().get(getIndexToken()));
				reconhecer("numero");
			}
		}else{
			View.showFeedBack("ERRO Linha: "+obterNumeroLinhaErro()+" - CONSTANTE esperada"+"\n");
			hasErro = true;
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - CONSTANTE esperada");
		}
	}

	private void dec_var() {
		if(getTokens().get(getIndexToken()).equals("int") || getTokens().get(getIndexToken()).equals("vetor")){
			while(getTokens().get(getIndexToken()).equals("int") || getTokens().get(getIndexToken()).equals("vetor")){
				tipo();
				reconhecer(":");
				do{
					if(getTokens().get(getIndexToken()).equals(","))
						reconhecer(",");
					reconhecer("IDEN");
				}while(getTokens().get(getIndexToken()).equals(","));
				reconhecer(";");
			}
		}
		else{
			View.showFeedBack("ERRO Linha: "+obterNumeroLinhaErro()+" - Declara��o de um tipo esperada"+"\n");
			hasErro = true;
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Declara��o de um tipo esperada");
		}
	}

	private void tipo() {
		if(getTokens().get(getIndexToken()).equals("int") || getTokens().get(getIndexToken()).equals("vetor")){
			if(getTokens().get(getIndexToken()).equals("int")){
				reconhecer("int");
			}else if(getTokens().get(getIndexToken()).equals("vetor")){
				reconhecer("vetor");
				reconhecer("[");
				if(new Caracol().getNumeros().verificaNumero(getTokens().get(getIndexToken()))){
					reconhecer(getTokens().get(getIndexToken()));
				}
				reconhecer("]");
			}
		}else{
			View.showFeedBack("ERRO Linha: "+obterNumeroLinhaErro()+" - Tipo esperado"+"\n");
			hasErro = true;
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Tipo esperado");
		}

	}

	private void dec_func() {
		if((getTokens().get(getIndexToken())).equals("la_funcion")){
			do{
				reconhecer("la_funcion");
				reconhecer("IDEN");
				reconhecer("(");
				if(getTokens().get(getIndexToken()).equals("int") || getTokens().get(getIndexToken()).equals("vetor")){
                    l_par();
				}
				reconhecer(")");
				reconhecer(":");
				tipo();
				reconhecer(";");
				corpo();
			}while((getTokens().get(getIndexToken())).equals("la_funcion"));
		}else{
			View.showFeedBack("ERRO Linha: "+obterNumeroLinhaErro()+" - Declara��o da fun��o esperada"+"\n");
			hasErro = true;
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Declara��o da fun��o esperada");
		}


	}

	private void l_par() {
		if(getTokens().get(getIndexToken()).equals("int") || getTokens().get(getIndexToken()).equals("vetor")){
			do{
				tipo();
				reconhecer(":");
				reconhecer("IDEN");
				while(getTokens().get(getIndexToken()).equals(",")){
					reconhecer(",");
					reconhecer("IDEN");
				}
				if(getTokens().get(getIndexToken()).equals(";"))
					reconhecer(";");
			}while(getTokens().get(getIndexToken()).equals("int") || getTokens().get(getIndexToken()).equals("vetor"));
		}
		else{
			View.showFeedBack("ERRO Linha: "+obterNumeroLinhaErro()+" - Identificador esperado"+"\n");
			hasErro = true;
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Identificador esperado");
		}
	}

	public void reconhecer(String comand){
		Numero numero = new Numero();
		System.out.println("Coman: "+ comand);
		if(comand.equals("el_final")){
			if(getIndexToken()+1 != getTokens().size()){
				if(!verificaElfinal()){
					hasErro = true;
				}else if(flag && getTokens().get(getIndexToken()).equals("el_final") && !verificaElfinal()){
					hasErro = true;
					View.showFeedBack("ERRO Linha : "+(obterNumeroLinhaErro())+" N�o � esperado mais nenhum comandos depois de "+getTokens().get(getIndexToken())+"\n");
				}else 
					flag = true;
				//throw new CompilatorException("ERRO Linha : "+obterNumeroLinhaErro()+" N�o � esperado mais nenhum comandos depois de "+getTokens().get(getIndexToken()-1));
			}
			else if(!flag){
					if(!verificaElfinal()){
						flag = true;
						hasErro = true;
					}
			}
				
		}else if(comand.equals("numero")){
			if((getIndexToken()==getTokens().size()) || !numero.verificaNumero(getTokens().get(getIndexToken()))){
				hasErro = true;
				View.showFeedBack("ERRO Linha : "+obterNumeroLinhaErro()+" Esperado um "+comand+"\n");
				//	throw new CompilatorException("ERRO Linha : "+obterNumeroLinhaErro()+" Esperado um "+comand);
			}
		}else if(comand.equals("IDEN")){
			if((getIndexToken()==getTokens().size()) || !Identificadores.verificaId(getTokens().get(getIndexToken()))){
				hasErro = true;
				View.showFeedBack("ERRO Linha : "+obterNumeroLinhaErro()+" Esperado um "+comand+"\n");
				//		throw new CompilatorException("ERRO Linha : "+obterNumeroLinhaErro()+" Esperado um "+comand);
			}
		}else if((getIndexToken()==getTokens().size()) || !comand.equals(getTokens().get(getIndexToken()))){
			hasErro = true;
			View.showFeedBack("ERRO Linha : "+(obterNumeroLinhaErro()+1)+" Esperado um(a) "+comand+"\n");
			//	throw new CompilatorException("ERRO Linha : "+obterNumeroLinhaErro()+" Esperado um(a) "+comand);
		}
		
		if(getIndexToken()+1 != getTokens().size())
			setIndexToken(getIndexToken()+1);
		else if(!flag){
			if(!verificaElfinal()){
				hasErro = true;
				flag = true;
			}
		}
			
	}

	private boolean verificaElfinal() {
		int i = 0, j = 0;
		
		if((tokens.contains("la_funcion") && tokens.contains("comienza")) || (tokens.contains("el_program") && tokens.contains("comienza"))){
			for (String t : tokens) {	
				if(t.equals("comienza") || t.equals("el_program") || t.equals("la_funcion")){
					i++;
				}
			}
			
			for (String t : tokens) {
				if(t.equals("el_final")){
					j++;
				}
			}
			
			if((i - j) < 0){
				View.showFeedBack("ERRO - N�o esperava encontrar "+(i - j)*(-1)+" el_final \n");
				return false;
			}
			else if((i - j) > 0){
				View.showFeedBack("ERRO - Esperava encontrar "+(i - j)+" el_final \n");
				return false;
			}
			return true;
		}
		return false;
		
	}

	public int obterNumeroLinhaErro(){
		int linhaErro = 1;
		int qtdeIguais=0;
		int qtdeEncontrada = 0;
		//String aux  = SeparaCodigo.retiraComentario(getCodigo());
		String []codigo = Servicos.separaTexto(getCodigo());

		//conta a qtde de iguais a essa palavra errada
		for(int i=0; i < getIndexToken() && i < getTokens().size()-1 ;i++){
			if(getTokens().get(getIndexToken()).equals(getTokens().get(i)))
				qtdeIguais++;
		}
		
		//verifica em q linha foi o erro
		for(int j=0; j < codigo.length; j++){
			if(codigo[j].charAt(0)== '\n')
				linhaErro++;
			if(getIndexToken()==getTokens().size())
				break;
			if(codigo[j].equals(getTokens().get(getIndexToken()))){
				qtdeEncontrada++;
				if(qtdeEncontrada==qtdeIguais+1 && codigo[j].equals(getTokens().get(getIndexToken())))
					break;
			}
		}
		return linhaErro-1;
	}


	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<String> getTokens() {
		return tokens;
	}

	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}

	public int getIndexToken() {
		return indexToken;
	}

	public void setIndexToken(int indexToken) {
		this.indexToken = indexToken;
	}
}
