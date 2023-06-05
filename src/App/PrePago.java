package App;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class PrePago extends Assinante {

	private int numRecargas;
	private float creditos;
	private ArrayList<Recarga> recargas = new ArrayList<>();

	public PrePago(long cpf, String nome, long numero) {
		super(cpf, nome, numero);
		this.numRecargas = 0;
	}

	public void fazerChamada(GregorianCalendar data, int duracao) {

		// caso todos espaços do array estejam preechidos(sem credito) ou credito for
		// menor ou igual a zero, printa que não pode fazer a ligação
		if (creditos < duracao * 1.45f) {
			System.out.println("Não possui créditos suficientes para completar esta ligação.");
		} else {

			chamadas.add(new Chamada(data, duracao)); // usuário
			this.numChamadas += 1; // numChamadas aumenta +1
			this.creditos -= duracao * 1.45f; // aqui ele atualiza o valor de crédito cobrando de // acordo com a
												// duração da chamada(fiz o casting para
												// float porque o valor provido é double porém o
												// requerido é float
		}
	}

	public void recarregar(GregorianCalendar data, float valor) {

		recargas.add(new Recarga(data, valor));
		numRecargas++; // atualiza o valor de numRecarga
		creditos += valor; // atualiza também o valor de créditos
	}

	public void imprimirFatura(int mes, int ano) {
		float totalChamadas = 0; // variável para armazenar o total das chamadas do mês
		float valorChamada;
		System.out.println("Fatura do(s) assinante(s) para o mês ");
		System.out.println(this.toString());

		for (Chamada chamada : chamadas) {
			// verifica se há alguma chamada realizada no mês desejado
			if (chamada.getData().get(GregorianCalendar.MONTH) == mes
					&& chamada.getData().get(GregorianCalendar.YEAR) == ano) {

				System.out.print(chamada.toString());// imprime data e duração de cada chamada
				valorChamada = (float) chamada.getDuracao() * 1.45f;
				System.out.println(" Custo: R$ " + String.format("%.2f", valorChamada));// imprime valor de cada chamada
				totalChamadas += (float) chamada.getDuracao() * 1.45f; // adiciona o valor da chamada ao total
			}
		}

		float totalRecargas = 0; // variável para armazenar o total de recargas do mês

		// laço for que percorre o array de recargas do assinante e calcula o total de
		// recargas realizadas no mês
		for (Recarga recarga : recargas) {
			if (recarga.getData().get(GregorianCalendar.MONTH) == mes
					&& recarga.getData().get(GregorianCalendar.YEAR) == ano) {
				System.out.println(recarga.toString());
				totalRecargas += recarga.getValor(); // adiciona o valor da recarga ao totala ao total
			}
		}

		// imprime a fatura do assinante
		System.out.println("Total em chamadas: R$ " + String.format("%.2f", totalChamadas));
		System.out.println("Total em recargas: R$ " + String.format("%.2f", totalRecargas));
		System.out.println("Créditos: R$ " + String.format("%.2f", creditos));

	}
}
