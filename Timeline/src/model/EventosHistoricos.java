package model;

public class EventosHistoricos {
	private String descricao;
	private int anoCorreto;
	private String caminhoImagem;
	
	public EventosHistoricos(String descricao, int anoCorreto, String caminhoImagem) {
		this.descricao = descricao;
		this.anoCorreto = anoCorreto;
		this.caminhoImagem = caminhoImagem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getAnoCorreto() {
		return anoCorreto;
	}

	public void setAnoCorreto(int anoCorreto) {
		this.anoCorreto = anoCorreto;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

	public void setCaminhoImagem(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}
	

}

