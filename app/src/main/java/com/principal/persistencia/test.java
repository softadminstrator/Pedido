package com.principal.persistencia;

import com.principal.mundo.sysws.GetPedidoMesa;
import com.principal.mundo.sysws.Pedido;

public class test {

	public test() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GetPedidoMesa gpm=new GetPedidoMesa("192.168.0.6","");
		Pedido p=gpm.GetPedidoM("11");

	}

}
