
package br.ucb.caracol.servicos;

import br.ucb.caracol.dados.Caracol;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacoes {
    
    public static boolean isArqValido(String arq){
        return (arq.endsWith(".col") || arq.endsWith(".COL"));
    }
    public static boolean isSimbolo(char simbolo){
        return (Caracol.getSimbolos().contains(simbolo+""));
    }
    public static boolean isLetra(char letra){
        return (Caracol.getLetras().contains(letra+""));
    }
    public static boolean isDigito(char digito){
        return (Caracol.getDigitos().contains(digito+""));
    }
    public static boolean iskeyWord(String keysWords){
        return Caracol.toList().contains(keysWords);
    }

    public static boolean isComentario(char charAtual, char charProxima,boolean isComentario){
        if((charAtual == '(') && (charProxima== '*')){
            isComentario = true;
        }
        if((charAtual == '*') && (charProxima== ')')){
            isComentario = false;
        }
       return isComentario;
    }

    public static boolean isValido(char digito){
        return isLetra(digito) || isDigito(digito) || isSimbolo(digito);
    }
    
    public static boolean isTerminarNaoSimbolo(char caracter){
        return caracter != '\n' && caracter != ' ' && caracter != '\t';
    }
    
    public static boolean isWordValid(String string){
        Pattern padrao = Pattern.compile("^(([A-Za-z]+)|([A-Za-z]+[//_]*[A-Za-z]+))+$");
        Matcher result = padrao.matcher(string);
        return result.matches();
    }
    
    
    public static boolean isNumberValid(String string){
        Pattern padrao = Pattern.compile("^([0-9]+)$");
        Matcher result = padrao.matcher(string);
        return result.matches();
    }
    
    public static boolean isCompostSymbol(String string){
        Pattern padrao = Pattern.compile("^(([<>:]=)|(<>))$");
        Matcher result = padrao.matcher(string);
        return result.matches();
    }
    
    public static boolean isTerminalSymbol(String string){
       
        Pattern padrao = Pattern.compile("^[\\:=();<>*/+-{}\n \t]$");
        Matcher result = padrao.matcher(string);
        System.out.println("Aqui terminalSymbol"+result.matches());
        return result.matches();
    }

    public static boolean isTerminalSymbol(char valor){

        return ((Caracol.getSimbolos()+"\n \t")).replace("_","").contains(valor+"");

    }
    
}
