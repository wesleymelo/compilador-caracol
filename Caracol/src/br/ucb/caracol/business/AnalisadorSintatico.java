
package br.ucb.caracol.business;

import br.ucb.caracol.dados.Alfabeto;
import br.ucb.caracol.dados.Caracol;
import br.ucb.caracol.dados.Identificadores;
import br.ucb.caracol.dados.Numero;
import br.ucb.caracol.exceptions.CompilatorException;
import br.ucb.caracol.servicos.Servicos;

public class AnalisadorSintatico {
	private String codigo;
	private String[] tokens;
	private int numerotoken;
	
	public void verificaCodigo(String text) {
		setCodigo(text);
		formato_inicial(text);
	}
	
	private void formato_inicial(String text) {
		setTokens(Servicos.obterToken(text));
		setNumerotoken(0);
		if(getTokens().length==0)
			throw new CompilatorException("Linha: 01 - Nenhum código encontrado");
		
		if(getTokens()[getNumerotoken()].equals("la_funcion")){
			
			while(getTokens()[getNumerotoken()].equals("la_funcion")){
				dec_func();
			}
			
			
		}
		if(getTokens()[getNumerotoken()].equals("el_programa")){
			reconhecer("el_programa");
			reconhecer("IDEN");
			reconhecer(";");
			corpo();
			reconhecer("el_final");
		}	
		else{
			throw new CompilatorException("Linha: 01 - Nenhum código encontrado");
		}
		
	}
	
	private void corpo() {
		if(getTokens()[getNumerotoken()].equals("comienza")){
			reconhecer("comienza");
			while(getTokens()[getNumerotoken()].equals("int") || getTokens()[getNumerotoken()].equals("vetor")){
				dec_var();
			}
			do{
				if(getTokens()[getNumerotoken()].equals(";"))
					reconhecer(";");
				cmd();
			}while(getTokens()[getNumerotoken()].equals(";"));
			reconhecer("el_final");
		}
		else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Esperado início de programa");
		}
			
		
	}

	private void cmd() {
		if(getTokens()[getNumerotoken()].equals("IDEN")){
			reconhecer("IDEN");
			if(getTokens()[getNumerotoken()].equals("[") || getTokens()[getNumerotoken()].equals(":=")){
				
				if(getTokens()[getNumerotoken()].equals("[")){
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
				if(getTokens()[getNumerotoken()].equals("(")){
					reconhecer("(");
					
					do{
						if(getTokens()[getNumerotoken()].equals(","))
							reconhecer(",");
						expr();
					}while(getTokens()[getNumerotoken()].equals(","));
					reconhecer("(");
				}
				else{
					if(getTokens()[getNumerotoken()].equals("si")){
						reconhecer("si");
						expr();
						reconhecer("entonces");
						bloco();
						
						if(getTokens()[getNumerotoken()].equals("si_no")){
							reconhecer("entonces");
							bloco();
						}
					}
					else{
						if(getTokens()[getNumerotoken()].equals("si")){
							reconhecer("repeticion");
							do{
								if(getTokens()[getNumerotoken()].equals(";"))
									reconhecer(";");
								cmd();
							}while(getTokens()[getNumerotoken()].equals(";"));
							reconhecer("hasta");
							expr();
						}
						else{
							if(getTokens()[getNumerotoken()].equals("exploracion")){
								reconhecer("exploracion");
								reconhecer("(");
								
								do{
									if(getTokens()[getNumerotoken()].equals(","))
										reconhecer(",");
									reconhecer("IDEN");									
								}while(getTokens()[getNumerotoken()].equals(","));
								
								reconhecer(")");
							}
							else{
								if(new Alfabeto().getLetras().contains(getTokens()[getNumerotoken()].charAt(0))){
									reconhecer(getTokens()[getNumerotoken()]);
									reconhecer("(");
									do{
										if(getTokens()[getNumerotoken()].equals(","))
											reconhecer(",");
										reconhecer("IDEN");									
									}while(getTokens()[getNumerotoken()].equals(","));
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
		if(getTokens()[getNumerotoken()].equals("+")){
			reconhecer("{");
			do{
				if(getTokens()[getNumerotoken()].equals(";"))
					reconhecer(";");
				cmd();									
			}while(getTokens()[getNumerotoken()].equals(";"));
			reconhecer("}");
		}
		else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Bloco esperado");
		}
	}	

	private void expr() {
		if(getTokens()[getNumerotoken()].equals("+") || getTokens()[getNumerotoken()].equals("-") || 
			getTokens()[getNumerotoken()].equals("(") || getTokens()[getNumerotoken()].equals("no") || getTokens()[getNumerotoken()].equals("IDEN") || 
			  new Numero().getNumeros().contains(getTokens()[getNumerotoken()]))
		{
			siexpr();
			if(getTokens()[getNumerotoken()].equals("=") || getTokens()[getNumerotoken()].equals("<") || getTokens()[getNumerotoken()].equals(">") || 
					getTokens()[getNumerotoken()].equals("<") || getTokens()[getNumerotoken()].equals("<>") || getTokens()[getNumerotoken()].equals("<=") ||
						getTokens()[getNumerotoken()].equals(">=")){
			
				reconhecer(getTokens()[getNumerotoken()]);
				siexpr();
			}
		}
		else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Esperada uma expressão");
		}
		
	}
	
	private void siexpr() {
		if(getTokens()[getNumerotoken()].equals("+") || getTokens()[getNumerotoken()].equals("-") || getTokens()[getNumerotoken()].equals("(") || 
					getTokens()[getNumerotoken()].equals("no") || getTokens()[getNumerotoken()].equals("IDEN") ||
						 new Numero().getNumeros().contains(getTokens()[getNumerotoken()])){
			
			if(getTokens()[getNumerotoken()].equals("+") || getTokens()[getNumerotoken()].equals("-")){
				
				if(getTokens()[getNumerotoken()].equals("+")){
					reconhecer("+");
				}else if(getTokens()[getNumerotoken()].equals("-")){
					reconhecer("-");
				}
			}
			do{
				if(getTokens()[getNumerotoken()].equals("+")){
					reconhecer("+");
				}else if(getTokens()[getNumerotoken()].equals("-")){
					reconhecer("-");
				}else if(getTokens()[getNumerotoken()].equals("o")){
					reconhecer("o");
				}
				termo();
			}while(getTokens()[getNumerotoken()].equals("+") || getTokens()[getNumerotoken()].equals("-") || getTokens()[getNumerotoken()].equals("o"));
		}else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Operador ou identificador esperado");
		}
	}

	private void termo() {
		if(getTokens()[getNumerotoken()].equals("+") || getTokens()[getNumerotoken()].equals("-") || getTokens()[getNumerotoken()].equals("(") || 
				getTokens()[getNumerotoken()].equals("no") || getTokens()[getNumerotoken()].equals("IDEN") ||
				 new Numero().getNumeros().contains(getTokens()[getNumerotoken()])){
			fator();
			while (getTokens()[getNumerotoken()].equals("*") || getTokens()[getNumerotoken()].equals("/") || getTokens()[getNumerotoken()].equals("y")) {
				reconhecer(getTokens()[getNumerotoken()]);
				fator();
			}
		}
		
	}
	
	

	private void fator() {
		if(getTokens()[getNumerotoken()].equals("(") || getTokens()[getNumerotoken()].equals("+") || getTokens()[getNumerotoken()].equals("-") ||
				getTokens()[getNumerotoken()].equals("no") || new Caracol().getNumeros().verificaNumero(getTokens()[getNumerotoken()]) || 
				getTokens()[getNumerotoken()].equals("IDEN")){
					
				if(getTokens()[getNumerotoken()].equals("(")){
					reconhecer("(");
					expr();
					reconhecer(")");
				}else if(getTokens()[getNumerotoken()].equals("+")){
					reconhecer("+");
					expr();
				}else if(getTokens()[getNumerotoken()].equals("-")){
					reconhecer("-");
					expr();
				}else if(getTokens()[getNumerotoken()].equals("no")){
					reconhecer("no");
					fator();
				}else if(new Caracol().getNumeros().verificaNumero(getTokens()[getNumerotoken()])){
					constante();
				}else if(getTokens()[getNumerotoken()].equals("IDEN")){
					reconhecer("IDEN");
				}
		}else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Fator");
		}
		
		
	}

	private void constante() {
		if(getTokens()[getNumerotoken()].equals("+") || getTokens()[getNumerotoken()].equals("-") || new Caracol().getNumeros().verificaNumero(getTokens()[getNumerotoken()])){
			if(getTokens()[getNumerotoken()].equals("+")){
				reconhecer("+");
			}else if(getTokens()[getNumerotoken()].equals("-"))
				reconhecer("-");
			reconhecer(getTokens()[getNumerotoken()]);
		}
		
	}

	private void dec_var() {
		if(getTokens()[getNumerotoken()].equals("int") || getTokens()[getNumerotoken()].equals("vetor")){
			while(getTokens()[getNumerotoken()].equals("int") || getTokens()[getNumerotoken()].equals("vetor")){
				tipo();
				reconhecer(":");
				do{
					if(getTokens()[getNumerotoken()].equals(","))
						reconhecer(",");
					reconhecer("IDEN");
				}while(getTokens()[getNumerotoken()].equals(",") );
				reconhecer(";");
			}
		}
		else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Declaração de um tipo esperada");
		}
		
	}

	private void tipo() {
		if(getTokens()[getNumerotoken()].equals("int") || getTokens()[getNumerotoken()].equals("vetor")){
			if(getTokens()[getNumerotoken()].equals("int")){
				reconhecer("int");
			}else if(getTokens()[getNumerotoken()].equals("vetor")){
				reconhecer("vetor");
				if(new Caracol().getNumeros().verificaNumero(getTokens()[getNumerotoken()])){
					reconhecer(getTokens()[getNumerotoken()]);
				}
				reconhecer("]");
					
			}
		}
	}

	private void dec_func() {
		if((getTokens()[getNumerotoken()]).equals("la_funcion")){
			while((getTokens()[getNumerotoken()]).equals("la_funcion")){
				reconhecer("la_funcion");
				reconhecer("IDEN");
				reconhecer("(");
				while(getTokens()[getNumerotoken()].equals("int") || getTokens()[getNumerotoken()].equals("vetor")){
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
		if(getTokens()[getNumerotoken()].equals("int") || getTokens()[getNumerotoken()].equals("vector")){
			do{
				tipo();
				reconhecer(":");
				reconhecer("IDEN");
				while(getTokens()[getNumerotoken()].equals(",")){
					reconhecer(",");
					reconhecer("IDEN");
				}
				if(getTokens()[getNumerotoken()].equals(","))
					reconhecer(":");
				
			}while(getTokens()[getNumerotoken()].equals("int") || getTokens()[getNumerotoken()].equals("vector"));
		}
		else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Identificador esperado");
		}
		
		
	}

	public void reconhecer(String comand){
		Numero numero = new Numero();
		if(comand.equals("el_final")){
			if((getNumerotoken() != getTokens().length))
				throw new CompilatorException("ERRO Linha : "+obterNumeroLinhaErro()+" Não é esperado mais nenhum comandos depois de "+getTokens()[getNumerotoken()-1]);
		}else if(comand.equals("numero")){
			if((getNumerotoken()==getTokens().length) || !numero.verificaNumero(tokens[getNumerotoken()]))
				throw new CompilatorException("ERRO Linha : "+obterNumeroLinhaErro()+" Esperado um "+comand);
		}else if(comand.equals("IDEN")){
			if((getNumerotoken()==getTokens().length) || !Identificadores.verificaId(tokens[getNumerotoken()])){
				throw new CompilatorException("ERRO Linha : "+obterNumeroLinhaErro()+" Esperado um "+comand);
			}
		}else if((getNumerotoken()==getTokens().length) || !comand.equals(tokens[getNumerotoken()])){
				throw new CompilatorException("ERRO Linha : "+obterNumeroLinhaErro()+" Esperado um(a) "+comand);
		}
		setNumerotoken(getNumerotoken()+1);
	}

	

	
	public int obterNumeroLinhaErro(){
		int linhaErro = 1;
		int qtdeIguais=0;
		int qtdeEncontrada = 0;
		//String aux  = SeparaCodigo.retiraComentario(getCodigo());
		String []codigo = Servicos.separaTexto(getCodigo());
		
		//conta a qtde de iguais a essa palavra errada
		for(int i=0; i<getNumerotoken() && i <getTokens().length-1 ;i++){
			if(getTokens()[getNumerotoken()].equals(getTokens()[i]))
				qtdeIguais++;
		}
		
		//verifica em q linha foi o erro
		for(int j=0; j<codigo.length-1; j++){
			if(codigo[j].charAt(0)==13)
				linhaErro++;
			if(getNumerotoken()==getTokens().length)
				break;
				if(codigo[j].equals(getTokens()[getNumerotoken()])){
					qtdeEncontrada++;
					if(qtdeEncontrada==qtdeIguais+1 && codigo[j].equals(getTokens()[getNumerotoken()]))
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

	public int getNumerotoken() {
		return numerotoken;
	}

	public void setNumerotoken(int numerotoken) {
		this.numerotoken = numerotoken;
	}

	
	
}
