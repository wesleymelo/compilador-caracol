
package br.ucb.caracol.business;

import br.ucb.caracol.dados.Alfabeto;
import br.ucb.caracol.dados.Caracol;
import br.ucb.caracol.dados.Identificadores;
import br.ucb.caracol.dados.Numero;
import br.ucb.caracol.exceptions.CompilatorException;
import br.ucb.caracol.servicos.Servicos;

public class CopyAnalisadorSintatico {
	private String codigo;
	private String[] tokens;
	private int numeroToken;

	public void verificaCodigo(String text) {
		System.out.println("AQUI OHHHHH");
		setCodigo(text);
		formato_inicial(text);
	}
	
	private void formato_inicial(String text) {
		setTokens(Servicos.obterToken(text));
		setNumeroToken(0);
		if(getTokens().length==0)
			throw new CompilatorException("Linha: 01 - Nenhum código encontrado");
		
		System.out.println("Token: "+getTokens()[getNumeroToken()]);
		
		if(getTokens()[getNumeroToken()].equals("la_funcion") || getTokens()[getNumeroToken()].equals("el_program")){
			if(getTokens()[getNumeroToken()].equals("la_funcion")){
				
				while(getTokens()[getNumeroToken()].equals("la_funcion")){
					dec_func();
				}
				
				
			}
			if(getTokens()[getNumeroToken()].equals("el_programa")){
				reconhecer("el_programa");
				reconhecer("IDEN");
				reconhecer(";");
				corpo();
				reconhecer("el_final");
			}
		}else{
			throw new CompilatorException("Linha: 01 - Nenhum código encontrado");
		}
		
	}
	
	private void corpo() {
		if(getTokens()[getNumeroToken()].equals("comienza")){
			reconhecer("comienza");
			while(getTokens()[getNumeroToken()].equals("int") || getTokens()[getNumeroToken()].equals("vetor")){
				dec_var();
			}
			do{
				if(getTokens()[getNumeroToken()].equals(";"))
					reconhecer(";");
				cmd();
			}while(getTokens()[getNumeroToken()].equals(";"));
			reconhecer("el_final");
		}
		else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Esperado início de programa");
		}
			
		
	}

	private void cmd() {
		if(getTokens()[getNumeroToken()].equals("IDEN")){
			reconhecer("IDEN");
			if(getTokens()[getNumeroToken()].equals("[") || getTokens()[getNumeroToken()].equals(":=")){
				
				if(getTokens()[getNumeroToken()].equals("[")){
					reconhecer("[");
					expr();
					reconhecer("]");
				}
				else{
					reconhecer(":=");
					expr();
				}
			}
			else{
				if(getTokens()[getNumeroToken()].equals("(")){
					reconhecer("(");
					
					do{
						if(getTokens()[getNumeroToken()].equals(","))
							reconhecer(",");
						expr();
					}while(getTokens()[getNumeroToken()].equals(","));
					reconhecer("(");
				}
				else{
					if(getTokens()[getNumeroToken()].equals("si")){
						reconhecer("si");
						expr();
						reconhecer("entonces");
						bloco();
						
						if(getTokens()[getNumeroToken()].equals("si_no")){
							reconhecer("entonces");
							bloco();
						}
					}
					else{
						if(getTokens()[getNumeroToken()].equals("si")){
							reconhecer("repeticion");
							do{
								if(getTokens()[getNumeroToken()].equals(";"))
									reconhecer(";");
								cmd();
							}while(getTokens()[getNumeroToken()].equals(";"));
							reconhecer("hasta");
							expr();
						}
						else{
							if(getTokens()[getNumeroToken()].equals("exploracion")){
								reconhecer("exploracion");
								reconhecer("(");
								
								do{
									if(getTokens()[getNumeroToken()].equals(","))
										reconhecer(",");
									reconhecer("IDEN");									
								}while(getTokens()[getNumeroToken()].equals(","));
								
								reconhecer(")");
							}
							else{
								if(new Alfabeto().getLetras().contains(getTokens()[getNumeroToken()].charAt(0))){
									reconhecer(getTokens()[getNumeroToken()]);
									reconhecer("(");
									do{
										if(getTokens()[getNumeroToken()].equals(","))
											reconhecer(",");
										reconhecer("IDEN");									
									}while(getTokens()[getNumeroToken()].equals(","));
									reconhecer(")");
								}
								else{
									throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Comando esperado");
								}
							}
						}
					}
				}
			}
		}
	}

	private void bloco() {
		if(getTokens()[getNumeroToken()].equals("+")){
			reconhecer("{");
			do{
				if(getTokens()[getNumeroToken()].equals(";"))
					reconhecer(";");
				cmd();									
			}while(getTokens()[getNumeroToken()].equals(";"));
			reconhecer("}");
		}
		else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Bloco esperado");
		}
	}	

	private void expr() {
		if(getTokens()[getNumeroToken()].equals("+") || getTokens()[getNumeroToken()].equals("-") || 
			getTokens()[getNumeroToken()].equals("(") || getTokens()[getNumeroToken()].equals("no") || getTokens()[getNumeroToken()].equals("IDEN") || 
			  new Numero().getNumeros().contains(getTokens()[getNumeroToken()]))
		{
			siexpr();
			if(getTokens()[getNumeroToken()].equals("=") || getTokens()[getNumeroToken()].equals("<") || getTokens()[getNumeroToken()].equals(">") || 
					getTokens()[getNumeroToken()].equals("<") || getTokens()[getNumeroToken()].equals("<>") || getTokens()[getNumeroToken()].equals("<=") ||
						getTokens()[getNumeroToken()].equals(">=")){
			
				reconhecer(getTokens()[getNumeroToken()]);
				siexpr();
			}
		}
		else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Esperada uma expressão");
		}
		
	}
	
	private void siexpr() {
		if(getTokens()[getNumeroToken()].equals("+") || getTokens()[getNumeroToken()].equals("-") || getTokens()[getNumeroToken()].equals("(") || 
					getTokens()[getNumeroToken()].equals("no") || getTokens()[getNumeroToken()].equals("IDEN") ||
						 new Numero().getNumeros().contains(getTokens()[getNumeroToken()])){
			
			if(getTokens()[getNumeroToken()].equals("+") || getTokens()[getNumeroToken()].equals("-")){
				
				if(getTokens()[getNumeroToken()].equals("+")){
					reconhecer("+");
				}else if(getTokens()[getNumeroToken()].equals("-")){
					reconhecer("-");
				}
			}
			do{
				if(getTokens()[getNumeroToken()].equals("+")){
					reconhecer("+");
				}else if(getTokens()[getNumeroToken()].equals("-")){
					reconhecer("-");
				}else if(getTokens()[getNumeroToken()].equals("o")){
					reconhecer("o");
				}
				termo();
			}while(getTokens()[getNumeroToken()].equals("+") || getTokens()[getNumeroToken()].equals("-") || getTokens()[getNumeroToken()].equals("o"));
		}else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Operador ou identificador esperado");
		}
	}

	private void termo() {
		if(getTokens()[getNumeroToken()].equals("+") || getTokens()[getNumeroToken()].equals("-") || getTokens()[getNumeroToken()].equals("(") || 
				getTokens()[getNumeroToken()].equals("no") || getTokens()[getNumeroToken()].equals("IDEN") ||
				 new Numero().getNumeros().contains(getTokens()[getNumeroToken()])){
			fator();
			while (getTokens()[getNumeroToken()].equals("*") || getTokens()[getNumeroToken()].equals("/") || getTokens()[getNumeroToken()].equals("y")) {
				reconhecer(getTokens()[getNumeroToken()]);
				fator();
			}
		}
		
	}
	
	

	private void fator() {
		if(getTokens()[getNumeroToken()].equals("(") || getTokens()[getNumeroToken()].equals("+") || getTokens()[getNumeroToken()].equals("-") ||
				getTokens()[getNumeroToken()].equals("no") || new Caracol().getNumeros().verificaNumero(getTokens()[getNumeroToken()]) || 
				getTokens()[getNumeroToken()].equals("IDEN")){
					
				if(getTokens()[getNumeroToken()].equals("(")){
					reconhecer("(");
					expr();
					reconhecer(")");
				}else if(getTokens()[getNumeroToken()].equals("+")){
					reconhecer("+");
					expr();
				}else if(getTokens()[getNumeroToken()].equals("-")){
					reconhecer("-");
					expr();
				}else if(getTokens()[getNumeroToken()].equals("no")){
					reconhecer("no");
					fator();
				}else if(new Caracol().getNumeros().verificaNumero(getTokens()[getNumeroToken()])){
					constante();
				}else if(getTokens()[getNumeroToken()].equals("IDEN")){
					reconhecer("IDEN");
				}
		}else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Fator");
		}
		
		
	}

	private void constante() {
		if(getTokens()[getNumeroToken()].equals("+") || getTokens()[getNumeroToken()].equals("-") || new Caracol().getNumeros().verificaNumero(getTokens()[getNumeroToken()])){
			if(getTokens()[getNumeroToken()].equals("+")){
				reconhecer("+");
			}else if(getTokens()[getNumeroToken()].equals("-"))
				reconhecer("-");
			reconhecer(getTokens()[getNumeroToken()]);
		}
		
	}

	private void dec_var() {
		if(getTokens()[getNumeroToken()].equals("int") || getTokens()[getNumeroToken()].equals("vetor")){
			while(getTokens()[getNumeroToken()].equals("int") || getTokens()[getNumeroToken()].equals("vetor")){
				tipo();
				reconhecer(":");
				do{
					if(getTokens()[getNumeroToken()].equals(","))
						reconhecer(",");
					reconhecer("IDEN");
				}while(getTokens()[getNumeroToken()].equals(",") );
				reconhecer(";");
			}
		}
		else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Declaração de um tipo esperada");
		}
		
	}

	private void tipo() {
		if(getTokens()[getNumeroToken()].equals("int") || getTokens()[getNumeroToken()].equals("vetor")){
			if(getTokens()[getNumeroToken()].equals("int")){
				reconhecer("int");
			}else if(getTokens()[getNumeroToken()].equals("vetor")){
				reconhecer("vetor");
				if(new Caracol().getNumeros().verificaNumero(getTokens()[getNumeroToken()])){
					reconhecer(getTokens()[getNumeroToken()]);
				}
				reconhecer("]");
					
			}
		}
	}

	private void dec_func() {
		if((getTokens()[getNumeroToken()]).equals("la_funcion")){
			while((getTokens()[getNumeroToken()]).equals("la_funcion")){
				reconhecer("la_funcion");
				reconhecer("IDEN");
				reconhecer("(");
				while(getTokens()[getNumeroToken()].equals("int") || getTokens()[getNumeroToken()].equals("vetor")){
					l_par();
				}
				reconhecer(")");
				reconhecer(":");
				tipo();
				reconhecer(";");
				corpo();
			}
		}
		
	}

	private void l_par() {
		if(getTokens()[getNumeroToken()].equals("int") || getTokens()[getNumeroToken()].equals("vector")){
			do{
				tipo();
				reconhecer(":");
				reconhecer("IDEN");
				while(getTokens()[getNumeroToken()].equals(",")){
					reconhecer(",");
					reconhecer("IDEN");
				}
				if(getTokens()[getNumeroToken()].equals(","))
					reconhecer(":");
				
			}while(getTokens()[getNumeroToken()].equals("int") || getTokens()[getNumeroToken()].equals("vector"));
		}
		else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Identificador esperado");
		}
		
		
	}

	public void reconhecer(String comand){
		Numero numero = new Numero();
		if(comand.equals("el_final")){
			if((getNumeroToken() != getTokens().length))
				throw new CompilatorException("ERRO Linha : "+obterNumeroLinhaErro()+" Não é esperado mais nenhum comandos depois de "+getTokens()[getNumeroToken()-1]);
		}else if(comand.equals("numero")){
			if((getNumeroToken()==getTokens().length) || !numero.verificaNumero(tokens[getNumeroToken()]))
				throw new CompilatorException("ERRO Linha : "+obterNumeroLinhaErro()+" Esperado um "+comand);
		}else if(comand.equals("IDEN")){
			if((getNumeroToken()==getTokens().length) || !Identificadores.verificaId(tokens[getNumeroToken()])){
				throw new CompilatorException("ERRO Linha : "+obterNumeroLinhaErro()+" Esperado um "+comand);
			}
		}else if((getNumeroToken()==getTokens().length) || !comand.equals(tokens[getNumeroToken()])){
				throw new CompilatorException("ERRO Linha : "+obterNumeroLinhaErro()+" Esperado um(a) "+comand);
		}
		setNumeroToken(getNumeroToken()+1);
	}

	

	
	public int obterNumeroLinhaErro(){
		int linhaErro = 1;
		int qtdeIguais=0;
		int qtdeEncontrada = 0;
		//String aux  = SeparaCodigo.retiraComentario(getCodigo());
		String []codigo = Servicos.separaTexto(getCodigo());
		
		//conta a qtde de iguais a essa palavra errada
		for(int i=0; i<getNumeroToken() && i <getTokens().length-1 ;i++){
			if(getTokens()[getNumeroToken()].equals(getTokens()[i]))
				qtdeIguais++;
		}
		
		//verifica em q linha foi o erro
		for(int j=0; j<codigo.length-1; j++){
			if(codigo[j].charAt(0)==13)
				linhaErro++;
			if(getNumeroToken()==getTokens().length)
				break;
				if(codigo[j].equals(getTokens()[getNumeroToken()])){
					qtdeEncontrada++;
					if(qtdeEncontrada==qtdeIguais+1 && codigo[j].equals(getTokens()[getNumeroToken()]))
						break;
				}
		}
		return linhaErro;
	}
	
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String[] getTokens() {
		return tokens;
	}

	public void setTokens(String[] tokens) {
		this.tokens = tokens;
	}

	public int getNumeroToken() {
		return numeroToken;
	}

	public void setNumeroToken(int numeroToken) {
		this.numeroToken = numeroToken;
	}	
	
	
}
