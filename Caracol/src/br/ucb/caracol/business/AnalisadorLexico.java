
package br.ucb.caracol.business;

import br.ucb.caracol.dados.Caracol;
import br.ucb.caracol.exceptions.CompilatorException;
import br.ucb.caracol.servicos.Validacoes;
import br.ucb.caracol.view.View;

public class AnalisadorLexico {
    public static void analisadorLexico(String codigo) {

            int tam = codigo.length();
            boolean isComentario = false, flag;
            boolean isCharValido = true;
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
                             isCharValido = false;
                         }else{
                            if(Validacoes.isLetra(codigo.charAt(i)))
                                i=verificaLetra(codigo,i);
                            else if(Validacoes.isDigito(codigo.charAt(i)))
                                i=verificaDigito(codigo, i);
                            if(Validacoes.isSimbolo(codigo.charAt(i)))
                                i=verificaSimbolo(codigo, i);
                         }
                     }
                }
            }

            if(isComentario){
                View.showFeedBack("ERRO 02: FIM DE COMENTÁRIO ESPERADO\n");
                throw new CompilatorException("ERRO 02: FIM DE COMENTÁRIO ESPERADO\n");
            }
        }
    
    
    public static int verificaDigito(String codigo, int i){
            StringBuilder valor= new StringBuilder();
            valor.append(codigo.charAt(i));
            while(((Validacoes.isDigito(codigo.charAt(i))) || (Validacoes.isLetra(codigo.charAt(i)))) && (i < codigo.length()-1)){
                i++;
                if(Validacoes.isTerminarNaoSimbolo(codigo.charAt(i))){
                    if(!Validacoes.isTerminalSymbol(codigo.charAt(i)))
                       
                       valor.append(codigo.charAt(i));
                }
            }
            boolean isNumberValid = Validacoes.isNumberValid(valor.toString()); 
            if(isNumberValid){
                return verificaSequencia(valor, codigo, i, "Lexema numérico: [ ");
            }
            View.showFeedBack("Palavra não Identificada: [ "+valor.toString()+" ]\n");
            return i;  
        }

        public static int verificaLetra( String codigo, int i){

            StringBuilder valor= new StringBuilder();
            valor.append(codigo.charAt(i));
            while((Validacoes.isLetra(codigo.charAt(i)) || (codigo.charAt(i) == '_') || (Validacoes.isDigito(codigo.charAt(i)))) && (i < codigo.length()-1)){
                i++;
                if(Validacoes.isTerminarNaoSimbolo(codigo.charAt(i))){
                    if(!Validacoes.isTerminalSymbol(codigo.charAt(i)))
                        valor.append(codigo.charAt(i));

                }
            }
            
            boolean isWordValid = Validacoes.isWordValid(valor.toString()); 
            
            if(isWordValid){
                if(verificaKeyWord(valor.toString()))
                    return verificaSequencia(valor, codigo, i, "Palavra Reservada: [ ");
                return verificaSequencia(valor, codigo, i, "Lexema não numérico: [ ");
            }
            View.showFeedBack("Palavra não Identificada: [ "+valor.toString()+" ]\n");
            return i;    
         }

         public static int verificaSimbolo( String codigo, int i){
            StringBuilder valor= new StringBuilder();
            boolean flag = false;
            valor.append(codigo.charAt(i));
            if(i < codigo.length()-1){
                i++;
                if(Validacoes.isSimbolo(codigo.charAt(i))){
                    valor.append(codigo.charAt(i));
                    flag = true;
                    if(Validacoes.isCompostSymbol(valor.toString())){
                        return verificaSequencia(valor, codigo, i,"Símbolo Composto: [ ");
                    }
                }
                if(flag)
                    valor.deleteCharAt(1);
                return verificaSequencia(valor, codigo, i-1,"Símbolo Simples: [ ");
                
            }
            return verificaSequencia(valor, codigo, i,"Símbolo Simples: [ ");
           
        }


        public static int verificaSequencia(StringBuilder valor, String codigo, int i, String msgNormal){
           if(Validacoes.isTerminalSymbol(codigo.charAt(i)+"") || i == codigo.length() - 1)
               View.showFeedBack(msgNormal+valor.toString()+" ]\n");
           else{ 
                valor.append(codigo.charAt(i));
                while(!Validacoes.isTerminalSymbol(codigo.charAt(i)+"")){
                    if(i < codigo.length()){
                        i++;
                        valor.append(codigo.charAt(i));
                    }
                }
                View.showFeedBack("Palavra não Identificada: [ "+valor.toString()+" ]\n");
           }
           return i;
        }

        public static boolean verificaKeyWord(String valor){

            String []keys = Caracol.getKeysWords();

            for (int i = 0; i < keys.length; i++) {
                if(keys[i].equals(valor))
                    return true;
            }
            return false;

        }

}
