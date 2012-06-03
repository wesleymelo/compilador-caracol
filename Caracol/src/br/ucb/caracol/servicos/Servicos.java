
package br.ucb.caracol.servicos;
    
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import br.ucb.caracol.dados.Alfabeto;
import br.ucb.caracol.dados.Numero;
import br.ucb.caracol.view.JanelaPrincipal;

    public class Servicos {
        public static String carregaArquivo(String arquivo) throws  IOException{

            File file = new File(arquivo);
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder saida = new StringBuilder();
            String linha;
            while((linha = br.readLine()) != null)
                saida.append(linha);
            br.close();
            return saida.toString();

        }

        public static void salvaArquivo(String string) throws  IOException{
              BufferedWriter out = new BufferedWriter(new FileWriter(JanelaPrincipal.getPath()));
              out.write(string);
              out.close();
        }
        
        
        public static String [] obterToken(String codigo){
    		String str = retiraComentario(codigo);
    		str = retiraEnter(str);
    		int numeroEspeciais=0;
    		String [] cod = separaTexto(str); 
    		for (String string : cod) {
    			if(string.equals(" ") || string.equals("\n") || string.equals("\t") || string.isEmpty())
    				numeroEspeciais++;
    		}
    		String [] linhas = new String[cod.length-numeroEspeciais];
    		for (int i=0,j=0;i<cod.length;i++) {
    			if( (!cod[i].equals(" ") && (!cod[i].equals("\n")) && !(cod[i].equals("\t"))) && !(cod[i].isEmpty())){
    				linhas[j]=cod[i];
    				j++;
    			}
    		}
    		return linhas;
    	}
        
        public static String retiraEnter(String str){
    		StringBuffer sb = new StringBuffer();
    		for (int i=0;i<str.length();i++) {
    			if((str.charAt(i) != 13) && (str.charAt(i) != '\t') && (str.charAt(i)!=0))
    				sb.append(str.charAt(i));
    			else
    				sb.append(' ');				
    		}
    		return sb.toString();
    	}
        
        public static String retiraComentario(String codigo){
    		boolean flag=false;
    		StringBuffer corrigido = new StringBuffer();
    		for (int i = 0; i < codigo.length(); i++) {
    			if(!flag && (codigo.charAt(i)!='{')){
    				corrigido.append(codigo.charAt(i));
    			}
    			else if(codigo.charAt(i)=='{')
    				flag=true;
    			else if(codigo.charAt(i)=='}')
    				flag=false;
    		}
    		return corrigido.toString();
    	}
        
        public static String[] separaTexto(String texto){
    		ArrayList<String> string = new ArrayList<String>();
    		Alfabeto alfabeto = new Alfabeto();
    		Numero numero = new Numero();
    		int i=0;
    		String aux;
    		while(i<texto.length()){
    			aux="";
    			if(alfabeto.verificaAlfabeto(texto.charAt(i))){
    				while(i<texto.length() && alfabeto.verificaAlfabeto(texto.charAt(i)) ){
    					aux=aux.concat(""+texto.charAt(i));
    					i++;
    				}
    			}else if(numero.verificaNumero(texto.charAt(i))){
    				while(i<texto.length() && numero.verificaNumero(texto.charAt(i))){
    					aux=aux.concat(""+texto.charAt(i));
    					i++;
    				}
    			}else{
    				aux=""+texto.charAt(i);
    				i++;
    			}
    			string.add(aux);			
    		}
    		return converteArrayToString(string);
    	}
        
        public static String[] converteArrayToString(ArrayList<String> str){
    		String []aux = new String[str.size()];
    		for (int i = 0; i < aux.length; i++) {
    			aux[i]=new String();
    			aux[i] = str.get(i);
    		}
    		return aux;
    	}
    }
