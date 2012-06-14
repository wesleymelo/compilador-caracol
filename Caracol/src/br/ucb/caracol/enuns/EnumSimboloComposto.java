package br.ucb.caracol.enuns;

public enum EnumSimboloComposto {
	
	DIF("<>"), REC(":="), MA_IG(">="), ME_IG("<=");
	private String simbolo;
	
	
	public static boolean findSimboloComp(String simbolo){
		
		for (EnumSimboloComposto e : values()) {
			if (e.getSimbolo().equals(simbolo)) {
				return true;
			}
		}
		return false;
	}
	
	
	private EnumSimboloComposto(String simbolo) {
		setSimbolo(simbolo);
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	
	

}
