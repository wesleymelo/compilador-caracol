package br.ucb.caracol.dados;

import java.util.ArrayList;
public class Alfabeto {
    
    private ArrayList<Character> letras;
    
    
    private void setLetras(ArrayList<Character> letras) {
		this.letras = letras;
	}	
 
    public ArrayList<Character> getLetras() {
		return letras;
	}

	public Alfabeto() {
        setLetras(new ArrayList<Character>());
        getLetras().add('a');
        getLetras().add('b');
        getLetras().add('c');
        getLetras().add('d');
        getLetras().add('e');
        getLetras().add('f');
        getLetras().add('g');
        getLetras().add('h');
        getLetras().add('i');
        getLetras().add('j');
        getLetras().add('k');
        getLetras().add('l');
        getLetras().add('m');
        getLetras().add('n');
        getLetras().add('o');
        getLetras().add('p');
        getLetras().add('q');
        getLetras().add('r');
        getLetras().add('s');
        getLetras().add('t');
        getLetras().add('u');
        getLetras().add('v');
        getLetras().add('x');
        getLetras().add('w');
        getLetras().add('y');
        getLetras().add('z');
        getLetras().add('A');
        getLetras().add('B');
        getLetras().add('C');
        getLetras().add('D');
        getLetras().add('E');
        getLetras().add('F');
        getLetras().add('G');
        getLetras().add('H');
        getLetras().add('I');
        getLetras().add('J');
        getLetras().add('K');
        getLetras().add('L');
        getLetras().add('M');
        getLetras().add('N');
        getLetras().add('O');
        getLetras().add('P');
        getLetras().add('Q');
        getLetras().add('R');
        getLetras().add('S');
        getLetras().add('T');
        getLetras().add('U');
        getLetras().add('V');
        getLetras().add('X');
        getLetras().add('W');
        getLetras().add('Y');
        getLetras().add('Z');
    }
    
}
