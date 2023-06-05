package App;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Telefonia {

    private int numPrePagos;
    private List<PrePago> prePagos;
    private List<PosPago> posPagos;
    private int numPosPagos;
    private ArrayList<Assinante> assinantes;

    private static Scanner input = new Scanner(System.in);

    public Telefonia() {
        numPrePagos = 0;
        prePagos = new ArrayList<>();
        posPagos = new ArrayList<>();
        numPosPagos = 0;
        assinantes = new ArrayList<>();
    }

    public boolean validarCPF(String cpf) {
        // Remova qualquer formatação do CPF
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifique se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifique se todos os dígitos são iguais (CPF inválido)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Verifique os dígitos verificadores
        int soma = 0;
        int resto;

        for (int i = 0; i < 9; i++) {
            soma += Integer.parseInt(cpf.substring(i, i + 1)) * (10 - i);
        }

        resto = 11 - (soma % 11);
        if (resto == 10 || resto == 11) {
            resto = 0;
        }

        if (resto != Integer.parseInt(cpf.substring(9, 10))) {
            return false;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Integer.parseInt(cpf.substring(i, i + 1)) * (11 - i);
        }

        resto = 11 - (soma % 11);
        if (resto == 10 || resto == 11) {
            resto = 0;
        }

        if (resto != Integer.parseInt(cpf.substring(10, 11))) {
            return false;
        }

        return true;
    }

    public void cadastrarAssinante() {
        System.out.println("Qual tipo de assinatura você deseja:");
        System.out.println("1 - Pós-pago\n2 - Pré-pago\n");

        int plano = input.nextInt();

        if (plano == 1) {
            System.out.println("Digite o CPF: ");
            String inputCpf = input.next();

            if (!validarCPF(inputCpf)) {
                System.out.println("CPF inválido!");
                return;
            }

            long cpf = Long.parseLong(inputCpf);

            System.out.println("Digite seu Nome: ");
            input.nextLine(); // consumir o \n do nextLong anterior
            String inputNome = input.nextLine();

            System.out.println("Digite o numero desejado: ");
            Long inputNumero = input.nextLong();

            System.out.println("Digite o valor do plano: ");
            float inputAssinatura = input.nextFloat();

            Assinante assinante = localizarAssinante(cpf);
            if (assinante != null && assinante instanceof PosPago) {
                System.out.println("CPF já cadastrado como pós-pago!");
            } else {
                this.posPagos.add(new PosPago(cpf, inputNome, inputNumero, inputAssinatura));
                numPosPagos += 1;
                assinantes.add(new PosPago(cpf, inputNome, inputNumero, inputAssinatura));
            }
        }

        if (plano == 2) {
            System.out.println("Digite o CPF: ");
            String inputCpf = input.next();

            if (!validarCPF(inputCpf)) {
                System.out.println("CPF inválido!");
                return;
            }

            long cpf = Long.parseLong(inputCpf);

            System.out.println("Digite seu Nome: ");
            input.nextLine(); // consumir o \n do nextLong anterior
            String inputNome = input.nextLine();

            System.out.println("Digite o numero desejado: ");
            Long inputNumero = input.nextLong();

            Assinante assinante = localizarAssinante(cpf);
            if (assinante != null && assinante instanceof PrePago) {
                System.out.println("CPF já cadastrado como pré-pago!");
            } else {
                this.prePagos.add(new PrePago(cpf, inputNome, inputNumero));
                numPrePagos += 1;
                assinantes.add(new PrePago(cpf, inputNome, inputNumero)); // Adiciona o assinante à lista "assinantes"
            }
        }
    }

    public void listarAssinantes() {

        System.out.println("Assinantes:");
        System.out.println("====================================== PÓS-PAGOS ======================================");
        for (Assinante assinante : assinantes) {
            if (assinante instanceof PosPago) {
                System.out.println(assinante.toString());
            }
        }
        System.out.println("====================================== PRÉ-PAGOS ======================================");
        for (Assinante assinante : assinantes) {
            if (assinante instanceof PrePago) {
                System.out.println(assinante.toString());
            }
        }
    }

    public void fazerChamada() {
        System.out.println("Qual é o tipo do assinante?\n1 - Pós-pago\n2 - Pré-pago\n");
        int plano = input.nextInt();
        System.out.println("Digite o CPF: ");
        long inputCpf = input.nextLong();

        Assinante assinante = localizarAssinante(inputCpf);

        if (assinante != null) {
            if (plano == 1 && assinante instanceof PosPago) {
                PosPago posPago = (PosPago) assinante;
                System.out.println("Qual a duração da chamada?");
                int inputDuracao = input.nextInt();
                System.out.println("Insira a data da chamada no formato dd/mm/aaaa");
                input.nextLine();
                String inputData = input.nextLine();
                SimpleDateFormat frmt = new SimpleDateFormat("dd/MM/yyyy");
                Date data = null;
                try {
                    data = (Date) frmt.parse(inputData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                GregorianCalendar dataChamada = new GregorianCalendar();
                dataChamada.setTime(data);
                posPago.fazerChamada(dataChamada, inputDuracao);
                System.out.println("Chamada realizada!");
            } else if (plano == 2 && assinante instanceof PrePago) {
                PrePago prePago = (PrePago) assinante;
                System.out.println("Qual a duração da chamada?");
                int inputDuracao = input.nextInt();
                System.out.println("Insira a data da chamada no formato dd/mm/aaaa");
                input.nextLine();
                String inputData = input.nextLine();
                SimpleDateFormat frmt = new SimpleDateFormat("dd/MM/yyyy");
                Date data = null;
                try {
                    data = (Date) frmt.parse(inputData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                GregorianCalendar dataChamada = new GregorianCalendar();
                dataChamada.setTime(data);
                prePago.fazerChamada(dataChamada, inputDuracao);
                System.out.println("Chamada realizada!");
            } else {
                System.out.println("Plano inválido.");
            }
        } else {
            System.out.println("Assinante não encontrado.");
        }
    }

    public void fazerRecarga(PrePago prePago) {

        System.out.println("Qual o valor da recarga? ");
        float valorRecarga = input.nextFloat();
        System.out.println("Insira a data da recarga no formato dd/mm/aaaa");
        input.nextLine();
        String inputData = input.nextLine();
        SimpleDateFormat frmt = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        try {
            data = (Date) frmt.parse(inputData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        GregorianCalendar dataRecarga = new GregorianCalendar();
        dataRecarga.setTime(data);
        prePago.recarregar(dataRecarga, valorRecarga);
        System.out.println("Recarga realizado!");
    }

    private Assinante localizarAssinante(long cpf) {
        for (Assinante assinante : prePagos) {
            if (assinante.getCpf() == cpf) {
                return assinante;
            }
        }

        for (Assinante assinante : posPagos) {
            if (assinante.getCpf() == cpf) {
                return assinante;
            }
        }

        return null;
    }

    public void imprimirFaturas() {

        System.out.println("Digite o mês da fatura");
        int inputMes = input.nextInt() - 1;// o mês no GregorianCalendar conta a partir de 0

        System.out.println("Digite o ano da fatura");
        int inputAno = input.nextInt();

        System.out.println("============================ PRÉ-PAGOS ============================");
        for (Assinante assinante : assinantes) {

            if (assinante instanceof PrePago) {

                assinante.imprimirFatura(inputMes, inputAno);
            }
        }

        System.out.println("============================ PÓS-PAGOS ============================");
        for (Assinante assinante : assinantes) {

            if (assinante instanceof PosPago) {

                assinante.imprimirFatura(inputMes, inputAno);
            }
        }

    }

    public static void main(String[] args) {
        Telefonia telefonia = new Telefonia();

        int opcao;

        do {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Cadastrar Assinante");
            System.out.println("2 - Listar Assinantes");
            System.out.println("3 - Fazer Chamada");
            System.out.println("4 - Fazer Recarga");
            System.out.println("5 - Imprimir Fatura");
            System.out.println("0 - Sair");
            opcao = input.nextInt();

            switch (opcao) {
                case 1:
                    telefonia.cadastrarAssinante();
                    break;
                case 2:
                    telefonia.listarAssinantes();
                    break;
                case 3:
                    telefonia.fazerChamada();
                    break;
                case 4:

                    System.out.println("Informe o CPF do assinante: ");
                    long inputCpf = input.nextLong();

                    Assinante assinanteFound = telefonia.localizarAssinante(inputCpf);

                    if (assinanteFound != null) {
                        if (assinanteFound instanceof PrePago) {

                            PrePago assinantePre = (PrePago) assinanteFound;
                            telefonia.fazerRecarga(assinantePre);

                        } else {

                            System.out.println("O CPF não é de um assinante pré-pago!");

                        }
                    } else {

                        System.out.println("Assinante não encontrado");
                    }
                    break;
                case 5:
                    telefonia.imprimirFaturas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);

        input.close();

    }
}
