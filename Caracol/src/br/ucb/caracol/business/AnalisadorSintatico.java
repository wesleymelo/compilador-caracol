
package br.ucb.caracol.business;

import java.util.List;
import br.ucb.caracol.dados.Alfabeto;
import br.ucb.caracol.dados.Caracol;
import br.ucb.caracol.dados.Identificadores;
import br.ucb.caracol.dados.Numero;
import br.ucb.caracol.exceptions.CompilatorException;
import br.ucb.caracol.servicos.Servicos;

public class AnalisadorSintatico {
	private String codigo;
	private List<String> tokens;
	private int indexToken;

	public void verificaCodigo(String text, List<String> tokens) {
		setTokens(tokens);
		setCodigo(text);
		setIndexToken(0);
		formato_inicial(text);
	}
	
	private void formato_inicial(String text) {
		if(getTokens().size()==0)
			throw new CompilatorException("Linha: 01 - Nenhum c�digo encontrado");
		
		
		if(getTokens().get(getIndexToken()).equals("la_funcion") || getTokens().get(getIndexToken()).equals("el_program")){
			if(getTokens().get(getIndexToken()).equals("la_funcion")){
				while(getTokens().get(getIndexToken()).equals("la_funcion")){
					dec_func();
				}
				
				
			}
			
			System.out.println("Token Atual: "+getTokens().get(getIndexToken()));
			if(getTokens().get(getIndexToken()).equals("el_program")){
				reconhecer("el_program");
				reconhecer("IDEN");
				reconhecer(";");
				corpo();
				reconhecer("el_final");
			}
		}else{
			throw new CompilatorException("Linha: 01 - Nenhum c�digo encontrado");
		}
		
	}
	
	private void corpo() {
		if(getTokens().get(getIndexToken()).equals("comienza")){
			reconhecer("comienza");
			while(getTokens().get(getIndexToken()).equals("int") || getTokens().get(getIndexToken()).equals("vetor")){
				dec_var();
			}
			System.out.println("AQUI CO\\o/XO");
			do{
				if(getTokens().get(getIndexToken()).equals(";"))
					reconhecer(";");
				cmd();
			}while(getTokens().get(getIndexToken()).equals(";"));
			reconhecer("el_final");
		}
		else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Esperado in�cio de fun��o");
		}
			
		
	}

	private void cmd() {
		if(getTokens().get(getIndexToken()).equals("IDEN")){
			reconhecer("IDEN");
			if(getTokens().get(getIndexToken()).equals("[") || getTokens().get(getIndexToken()).equals(":=")){
				
				if(getTokens().get(getIndexToken()).equals("[")){
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
				if(getTokens().get(getIndexToken()).equals("(")){
					reconhecer("(");
					
					do{
						if(getTokens().get(getIndexToken()).equals(","))
							reconhecer(",");
						expr();
					}while(getTokens().get(getIndexToken()).equals(","));
					reconhecer("(");
				}
				else{
					if(getTokens().get(getIndexToken()).equals("si")){
						reconhecer("si");
						expr();
						reconhecer("entonces");
						bloco();
						
						if(getTokens().get(getIndexToken()).equals("si_no")){
							reconhecer("entonces");
							bloco();
						}
					}
					else{
						if(getTokens().get(getIndexToken()).equals("si")){
							reconhecer("repeticion");
							do{
								if(getTokens().get(getIndexToken()).equals(";"))
									reconhecer(";");
								cmd();
							}while(getTokens().get(getIndexToken()).equals(";"));
							reconhecer("hasta");
							expr();
						}
						else{
							if(getTokens().get(getIndexToken()).equals("exploracion")){
								reconhecer("exploracion");
								reconhecer("(");
								
								do{
									if(getTokens().get(getIndexToken()).equals(","))
										reconhecer(",");
									reconhecer("IDEN");									
								}while(getTokens().get(getIndexToken()).equals(","));
								
								reconhecer(")");
							}
							else{
								if(new Alfabeto().getLetras().contains(getTokens().get(getIndexToken()).charAt(0))){
									reconhecer(getTokens().get(getIndexToken()));
									reconhecer("(");
									do{
										if(getTokens().get(getIndexToken()).equals(","))
											reconhecer(",");
										reconhecer("IDEN");									
									}while(getTokens().get(getIndexToken()).equals(","));
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
		if(getTokens().get(getIndexToken()).equals("+")){
			reconhecer("{");
			do{
				if(getTokens().get(getIndexToken()).equals(";"))
					reconhecer(";");
				cmd();									
			}while(getTokens().get(getIndexToken()).equals(";"));
			reconhecer("}");
		}
		else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Bloco esperado");
		}
	}	

	private void expr() {
		if(getTokens().get(getIndexToken()).equals("+") || getTokens().get(getIndexToken()).equals("-") || 
			getTokens().get(getIndexToken()).equals("(") || getTokens().get(getIndexToken()).equals("no") || getTokens().get(getIndexToken()).equals("IDEN") || 
			  new Numero().getNumeros().contains(getTokens().get(getIndexToken())))
		{
			siexpr();
			if(getTokens().get(getIndexToken()).equals("=") || getTokens().get(getIndexToken()).equals("<") || getTokens().get(getIndexToken()).equals(">") || 
					getTokens().get(getIndexToken()).equals("<") || getTokens().get(getIndexToken()).equals("<>") || getTokens().get(getIndexToken()).equals("<=") ||
						getTokens().get(getIndexToken()).equals(">=")){
			
				reconhecer(getTokens().get(getIndexToken()));
				siexpr();
			}
		}
		else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Esperada uma express�o");
		}
		
	}
	
	private void siexpr() {
		if(getTokens().get(getIndexToken()).equals("+") || getTokens().get(getIndexToken()).equals("-") || getTokens().get(getIndexToken()).equals("(") || 
					getTokens().get(getIndexToken()).equals("no") || getTokens().get(getIndexToken()).equals("IDEN") ||
						 new Numero().getNumeros().contains(getTokens().get(getIndexToken()))){
			
			if(getTokens().get(getIndexToken()).equals("+") || getTokens().get(getIndexToken()).equals("-")){
				
				if(getTokens().get(getIndexToken()).equals("+")){
					reconhecer("+");
				}else if(getTokens().get(getIndexToken()).equals("-")){
					reconhecer("-");
				}
			}
			do{
				if(getTokens().get(getIndexToken()).equals("+")){
					reconhecer("+");
				}else if(getTokens().get(getIndexToken()).equals("-")){
					reconhecer("-");
				}else if(getTokens().get(getIndexToken()).equals("o")){
					reconhecer("o");
				}
				termo();
			}while(getTokens().get(getIndexToken()).equals("+") || getTokens().get(getIndexToken()).equals("-") || getTokens().get(getIndexToken()).equals("o"));
		}else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Operador ou identificador esperado");
		}
	}

	private void termo() {
		if(getTokens().get(getIndexToken()).equals("+") || getTokens().get(getIndexToken()).equals("-") || getTokens().get(getIndexToken()).equals("(") || 
				getTokens().get(getIndexToken()).equals("no") || getTokens().get(getIndexToken()).equals("IDEN") ||
				 new Numero().getNumeros().contains(getTokens().get(getIndexToken()))){
			fator();
			while (getTokens().get(getIndexToken()).equals("*") || getTokens().get(getIndexToken()).equals("/") || getTokens().get(getIndexToken()).equals("y")) {
				reconhecer(getTokens().get(getIndexToken()));
				fator();
			}
		}
		
	}
	
	

	private void fator() {
		if(getTokens().get(getIndexToken()).equals("(") || getTokens().get(getIndexToken()).equals("+") || getTokens().get(getIndexToken()).equals("-") ||
				getTokens().get(getIndexToken()).equals("no") || new Caracol().getNumeros().verificaNumero(getTokens().get(getIndexToken())) || 
				getTokens().get(getIndexToken()).equals("IDEN")){
					
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
				}else if(getTokens().get(getIndexToken()).equals("IDEN")){
					reconhecer("IDEN");
				}
		}else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Fator");
		}
		
		
	}

	private void constante() {
		if(getTokens().get(getIndexToken()).equals("+") || getTokens().get(getIndexToken()).equals("-") || new Caracol().getNumeros().verificaNumero(getTokens().get(getIndexToken()))){
			if(getTokens().get(getIndexToken()).equals("+")){
				reconhecer("+");
			}else if(getTokens().get(getIndexToken()).equals("-"))
				reconhecer("-");
			reconhecer(getTokens().get(getIndexToken()));
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
				}while(getTokens().get(getIndexToken()).equals(",") );
				reconhecer(";");
			}
		}
		else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Declara��o de um tipo esperada");
		}
		
	}

	private void tipo() {
		if(getTokens().get(getIndexToken()).equals("int") || getTokens().get(getIndexToken()).equals("vetor")){
			if(getTokens().get(getIndexToken()).equals("int")){
				reconhecer("int");
			}else if(getTokens().get(getIndexToken()).equals("vetor")){
				reconhecer("vetor");
				if(new Caracol().getNumeros().verificaNumero(getTokens().get(getIndexToken()))){
					reconhecer(getTokens().get(getIndexToken()));
				}
				reconhecer("]");
					
			}
		}
	}

	private void dec_func() {
		if((getTokens().get(getIndexToken())).equals("la_funcion")){
			while((getTokens().get(getIndexToken())).equals("la_funcion")){
				reconhecer("la_funcion");
				reconhecer("IDEN");
				reconhecer("(");
				while(getTokens().get(getIndexToken()).equals("int") || getTokens().get(getIndexToken()).equals("vetor")){
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
		if(getTokens().get(getIndexToken()).equals("int") || getTokens().get(getIndexToken()).equals("vector")){
			do{
				tipo();
				reconhecer(":");
				reconhecer("IDEN");
				while(getTokens().get(getIndexToken()).equals(",")){
					reconhecer(",");
					reconhecer("IDEN");
				}
				if(getTokens().get(getIndexToken()).equals(","))
					reconhecer(":");
				
			}while(getTokens().get(getIndexToken()).equals("int") || getTokens().get(getIndexToken()).equals("vector"));
		}
		else{
			throw new CompilatorException("ERRO Linha: "+obterNumeroLinhaErro()+" - Identificador esperado");
		}
		
		
	}

	public void reconhecer(String comand){
		Numero numero = new Numero();
		if(comand.equals("el_final")){
			if((getIndexToken() != getTokens().size()))
				throw new CompilatorException("ERRO Linha : "+obterNumeroLinhaErro()+" N�o � esperado mais nenhum comandos depois de "+getTokens().get(getIndexToken()-1));
		}else if(comand.equals("numero")){
			if((getIndexToken()==getTokens().size()) || !numero.verificaNumero(getTokens().get(getIndexToken())))
				throw new CompilatorException("ERRO Linha : "+obterNumeroLinhaErro()+" Esperado um "+comand);
		}else if(comand.equals("IDEN")){
			if((getIndexToken()==getTokens().size()) || !Identificadores.verificaId(getTokens().get(getIndexToken()))){
				throw new CompilatorException("ERRO Linha : "+obterNumeroLinhaErro()+" Esperado um "+comand);
			}
		}else if((getIndexToken()==getTokens().size()) || !comand.equals(getTokens().get(getIndexToken()))){
				throw new CompilatorException("ERRO Linha : "+obterNumeroLinhaErro()+" Esperado um(a) "+comand);
		}
		setIndexToken(getIndexToken()+1);
	}

	

	
	public int obterNumeroLinhaErro(){
		int linhaErro = 1;
		int qtdeIguais=0;
		int qtdeEncontrada = 0;
		//String aux  = SeparaCodigo.retiraComentario(getCodigo());
		String []codigo = Servicos.separaTexto(getCodigo());
		
		//conta a qtde de iguais a essa palavra errada
		for(int i=0; i<getIndexToken() && i <getTokens().size()-1 ;i++){
			if(getTokens().get(getIndexToken()).equals(getTokens().get(i)))
				qtdeIguais++;
		}
		
		//verifica em q linha foi o erro
		for(int j=0; j<codigo.length-1; j++){
			if(codigo[j].charAt(0)==13)
				linhaErro++;
			if(getIndexToken()==getTokens().size())
				break;
				if(codigo[j].equals(getTokens().get(getIndexToken()))){
					qtdeEncontrada++;
					if(qtdeEncontrada==qtdeIguais+1 && codigo[j].equals(getTokens().get(getIndexToken())))
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
