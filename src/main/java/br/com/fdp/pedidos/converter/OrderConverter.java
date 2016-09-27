package br.com.fdp.pedidos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fdp.pedidos.model.Order;
import br.com.fdp.pedidos.repository.OrderRepository;




@Component
public class OrderConverter implements Converter{
	
	@Autowired
	private OrderRepository orderRepository;

	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value.isEmpty())
			return null;
		try {
			Order order = orderRepository.findOne(new Long(value));
			return order;
		} catch (Exception e) {
			return null;
		}
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value instanceof Order) {
			Order order = (Order) value;
			return order.getId().toString();
		} else {
			return null;
		}
	}

}
