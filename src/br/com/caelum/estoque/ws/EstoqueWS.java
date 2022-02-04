package br.com.caelum.estoque.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.ResponseWrapper;

import br.com.caelum.estoque.modelo.item.Filtro;
import br.com.caelum.estoque.modelo.item.Filtros;
import br.com.caelum.estoque.modelo.item.Item;
import br.com.caelum.estoque.modelo.item.ItemDao;
import br.com.caelum.estoque.modelo.item.ListaItens;
import br.com.caelum.estoque.modelo.usuario.AutorizacaoException;
import br.com.caelum.estoque.modelo.usuario.TokenDao;
import br.com.caelum.estoque.modelo.usuario.TokenUsuario;
import br.com.caelum.estoque.modelo.usuario.Usuario;

@WebService
public class EstoqueWS {
	
	private ItemDao dao = new ItemDao();
	
	@WebMethod(operationName = "todosOsItens")
	@WebResult(name = "itens")
	public ListaItens getitens(@WebParam(name = "filtros")Filtros filtros){
		
		System.out.println("Chamando getItens()");
		
		List<Filtro> lista = filtros.getLista();
		List<Item> itensResultado = dao.todosItens(lista);
		return new ListaItens(itensResultado);
	}
	
	
	/**ResponseWrapper */
//	@WebMethod(operationName="todosOsItens")
//	@ResponseWrapper(localName="itens")
//	@WebResult(name="item")
//	public List<Item> getItens() { 
//
//	    System.out.println("Chamando getItens()");
//	    return dao.todosItens();
//
//	}
	
	/**RequestWrapper*/
//	@WebMethod(operationName="todosOsItens")
//	@ResponseWrapper(localName="itens")
//	@WebResult(name="item")
//	@RequestWrapper(localName="listaItens")
//	public ListaItens getItens(@WebParam(name="filtros") Filtros filtros){
//
//		List<Filtro> lista = filtros.getLista();
//		List<Item> result = dao.todosItens(lista);
//
//		return new ListaItens(result);
//	}
	
	@WebMethod(operationName = "CadastrarItem")
	@WebResult(name = "item")
	public Item cadastrarItem(
			@WebParam(name = "tokenUsuario", header = true) TokenUsuario token, 
			@WebParam(name = "item")Item item) 
					throws AutorizacaoException {
		
		System.out.println("cadastrando item " + item + ", " + token);
		boolean valido = new TokenDao().ehValido(token);
		
		if(!valido) {
			throw new AutorizacaoException("Autorizacao falhou");
		}
		
		this.dao.cadastrar(item);
		return item;
		
	}

}
