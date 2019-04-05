package Slytherin.controle;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import Slytherin.DAO.BancoDados;
import Slytherin.DAO.LivroDAO;
import Slytherin.entidade.ItemPedido;
import Slytherin.entidade.Livro;
import Slytherin.entidade.Carrinho;
import Slytherin.excecao.BancoException;
import Slytherin.excecao.NegocioException;

/**
 * Esta classe gerencia o carrinho, podendo ser adicionar e retirado itens do carrinho
 * @author Lukas e Rayssa
 */
public class ControleCarrinho {
    
    
    /**
     * Exibe todos os itens do carrinho
     * @return Todos os itens do carrinho
     */
    public ArrayList<ItemPedido> exibirCarrinho(){
        return Carrinho.getItens();
    }
    
    
    /**
     * Adiciona um item ao carrinho de compras
     * @param isbn String que armazena o código do livro
     * @param quantidade Quantidade de itens no carrinho
     */
    public void adicionarItem(String isbn, String quantidade) {
        //Verifica se já existe algum item no carrinho; se já, altera e sai
        for(ItemPedido ix : Carrinho.getItens()) {
            if(ix.getLivro().getIsbn().equals(isbn)) {
                alterarItem(isbn,String.valueOf((Integer.parseInt(quantidade)+1)));
                return;
            }
        }
        
        //Prossegue o processo normal
        BancoDados bd = new BancoDados();
        LivroDAO lDao = new LivroDAO(bd);
        
        try {
            ItemPedido itemP = new ItemPedido();
            Livro livro = new Livro();
            livro.setIsbn(isbn);
            livro = lDao.buscar(livro);
            itemP.definirItemPedido(livro, Integer.parseInt(quantidade));
            Carrinho.adicionarItem(itemP);
            JOptionPane.showMessageDialog(null,"Item adicionado com sucesso.");
        } catch (NegocioException | BancoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    /**
     * Altera o item do carrinho de compras
     * @param isbn String com o código correspondente ao livro
     * @param quantidade Quantidade de itens no carrinho
     */
    public void alterarItem(String isbn, String quantidade) {
        ItemPedido escolhido = null;
        for(ItemPedido ix : Carrinho.getItens()) {
            if(ix.getLivro().getIsbn().equals(isbn)) {
                escolhido = ix;
                break;
            }
        }
        if(escolhido != null) {
            try {
                escolhido.definirItemPedido(escolhido.getLivro(), Integer.parseInt(quantidade));
                Carrinho.alterarItem(escolhido);
                JOptionPane.showMessageDialog(null,"Item alterado com sucesso.");
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
    
    /**
     * Retira um item do carrinho de compras
     * @param isbn String com o código correspondente ao livro
     */
    public void retirarItem(String isbn) {
        ItemPedido escolhido = null;
        for(ItemPedido ix : Carrinho.getItens()) {
            if(ix.getLivro().getIsbn().equals(isbn)) {
                escolhido = ix;
                break;
            }
        }
        if(escolhido != null) {
            Carrinho.retirarItem(escolhido);
            JOptionPane.showMessageDialog(null,"Item removido com sucesso.");
        }
    }
    
    /**
     * Limpa o carrinho de compras
     */
    public void limparCarrinho(){
        Carrinho.limparCarrinho();
    }
}
