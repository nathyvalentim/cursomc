package com.livapp.cursomc.domain.enums;

public enum EstadoPagamento {
	
	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quiatdo"),
	CANCELADO(3, "Cancelado");
	
	private int cod;
	private String descricao;
	
	//Construtor
	private EstadoPagamento (int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static EstadoPagamento toEnum(Integer cod){
		if (cod == null) {
			return null;
		}
		
		//Compara todos objetos 'x' nos valores possíveis do TipoCliente
		for(EstadoPagamento x : EstadoPagamento.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("ID Inválido:" + cod);
	
	
	}	

}
