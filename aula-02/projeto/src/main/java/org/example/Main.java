package org.example;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import org.example.conta.Conta;
import org.example.conta.ContaCorrente;
import org.example.conta.ContaService;
import org.example.pessoa.Pessoa;

public class Main {
    public static void main(String[] args) {
        ContaService contaService = new ContaService();
        List<Conta> contas = new ArrayList<>();
        String operacao = "";
        Scanner scanner = new Scanner(System.in);
        while (!operacao.equals("4")){
            System.out.println("1 - Criar \n2 - Listar \n3 - Buscar \n4 - Sair");
            operacao = scanner.next();
            if (operacao.equals("1")){
                System.out.println("Digite o nome:");
                String nome = scanner.next();
                String cpf = scanner.next();
                Float saldo = scanner.nextFloat();
                Float limite = scanner.nextFloat();
                
                contaService.cadastrar(nome, cpf, saldo, limite);

            }
            if (operacao.equals("2")){
                for(Conta c : contaService.listar()){
                    System.out.println(c);
                }
    
            }
            if (operacao.equals("3")){
                String cpf = scanner.next();
                Conta conta = contaService.buscar(cpf);

                for (Conta c : contaService.listar()){
                    if(c.getPessoa().getCpf().equals(cpf)){
                        conta = c;
                    }
                    if (conta != null){
                        System.out.println(conta);
                    }
                    else{
                        System.out.println("Conta não encontrada");
                    }
                }
            }
        }
    }
}
