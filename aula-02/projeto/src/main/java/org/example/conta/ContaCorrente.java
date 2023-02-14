package org.example.conta;

import javax.sound.sampled.FloatControl;

import org.example.pessoa.Pessoa;

public class ContaCorrente extends Conta {

    private Float limite;

    public ContaCorrente(Float saldo, Pessoa pessoa, Float limite) {
        super(saldo, pessoa);
        this.limite = limite;
    }

    public ContaCorrente(Conta conta, Float limite){
        super(conta.getSaldo(), conta.getPessoa());
        this.limite = limite;
    }
    @Override
    public void saque(Float valor){
        if (this.getSaldo() + this.limite < valor){
            System.out.println("Saque invalido");
        }
        else{
            saldo -= valor;
        }


    }



}
