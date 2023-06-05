package App;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public abstract class Assinante {
	private long cpf;
	private String nome;
	private long numero;
	protected int numChamadas;
	protected ArrayList<Chamada> chamadas = new ArrayList<>();

	public Assinante(long cpf, String nome, long numero) {
		this.cpf = cpf;
		this.nome = nome;
		this.numero = numero;
		this.numChamadas = 0;
	}

	public long getCpf() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		DecimalFormat formatadorCPF = new DecimalFormat("00000000000");
		String cpfFormatado = formatadorCPF.format(cpf);

		DecimalFormat formatadorNumero = new DecimalFormat("0000000000");
		String numeroFormatado = formatadorNumero.format(numero);

		return "Nome= " + nome + ", CPF= " + cpfFormatado.substring(0, 3) + "." +
				cpfFormatado.substring(3, 6) + "." + cpfFormatado.substring(6, 9) +
				"-" + cpfFormatado.substring(9) + ", Número= +55 (" + numeroFormatado.substring(0, 2) +
				") " + numeroFormatado.substring(2, 7) + "-" + numeroFormatado.substring(7);
	}
	public abstract void fazerChamada(GregorianCalendar data, int duracao);
	public abstract void imprimirFatura(int mes, int ano);
}
